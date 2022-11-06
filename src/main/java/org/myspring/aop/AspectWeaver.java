package org.myspring.aop;

import org.myspring.aop.annotation.Aspect;
import org.myspring.aop.annotation.Order;
import org.myspring.aop.aspect.AspectInfo;
import org.myspring.aop.aspect.DefaultAspect;
import org.myspring.core.BeanContainer;
import org.myspring.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author rwj
 * @Date 2022/11/6
 * @Description
 *      1. 将横切逻辑织入到被代理对象，以生成动态代理对象
 *          1. 创建被代理对象的动态代理实例
 *              1. 获取所有被 @Aspect 标记的切面类
 *              2. 遍历容器中所有 Class（即所有Bean对应的Key），找到该 Class 需要被植入的 切面类集合
 *              3. 按照不同的织入目标（即容器中Class标记的代理Bean）分别按序织入Aspect逻辑
 *      2. 织入
 *          将动态代理实例添加到容器里，取代未被代理的实例
 */
public class AspectWeaver {

    private BeanContainer beanContainer;

    public AspectWeaver(BeanContainer beanContainer) {
        this.beanContainer = beanContainer;
    }

    public void doAOP() {
        // 1.获取所有被 @Aspect 标记的切面类
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
        if (ValidationUtil.isEmpty(aspectSet)) {
            return;
        }
        // 2.构造AspectInfo
        List<AspectInfo> aspectInfoList = packAspectInfoList(aspectSet);
        // 3.遍历容器中所有 Class
        Set<Class<?>> classSet = beanContainer.getClasses();
        /**
         * for循环里这部分代码可以优化
         *  1. 这里相当于对 classSet 遍历的同时 ， 对 aspectInfoList 进行了 嵌套遍历 时间复杂度 O(n^2)
         * 优化方式；
         *  通过对 aspectInfoList 单独进行遍历（不在对 classSet 遍历的for循环中遍历，而是单独提出来在与 classSet for循环 同一层）
         *  构造一个 Map<targetClass, List<AspectInfo>>，然后再在 对 classSet 遍历的for循环中通过 map.get(targetClass) 获取到对应的 List<AspectInfo>
         *  这样代码执行效率就提高了，为 O(n)
         */
        for (Class<?> targetClass : classSet) {
            if (targetClass.isAnnotationPresent(Aspect.class)) { //如果被 @Aspect 标记了就跳过，不能对切面类本身织入切面逻辑
                continue;
            }
            List<AspectInfo> roughMatchedAspectList  = collectRoughMatchedAspectListForSpecificClass(targetClass, aspectInfoList);
            AspectInterceptor aspectInterceptor = new AspectInterceptor(targetClass, roughMatchedAspectList);
            Object proxyBean = ProxyCreator.createProxy(targetClass, aspectInterceptor);
            beanContainer.addBean(targetClass, proxyBean);
        }
    }

    private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(Class<?> targetClass, List<AspectInfo> aspectInfoList) {
        List<AspectInfo> roughMatchedAspectList = new ArrayList<>();
        for(AspectInfo aspectInfo : aspectInfoList) {
            Class<? extends Annotation> value = aspectInfo.getAspect().getClass().getAnnotation(Aspect.class).value();
            if(targetClass.isAnnotationPresent(value)) {    // 如果目标类的Bean类型（Controller、Component、Service、Repository）和 @Aspect.value() 相等，说明需要被织入切面类逻辑
                roughMatchedAspectList.add(aspectInfo);
            }
        }
        return roughMatchedAspectList;
    }

    private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        for (Class<?> aspectClass : aspectSet) {
            if (verifyAspect(aspectClass)) {     // 如果通过验证，就构造为 AspectInfo，并添加到 aspectInfoList
                Order orderTag = aspectClass.getAnnotation(Order.class);
                DefaultAspect aspectBean = (DefaultAspect) beanContainer.getBean(aspectClass);
                AspectInfo aspectInfo = new AspectInfo(orderTag.value(), aspectBean);
                aspectInfoList.add(aspectInfo);
            } else {    // 没有通过验证直接报错
                throw new RuntimeException("@Aspect and @Order must be added to the Aspect class, and Aspect class must extend from DefaultAspect");
            }
        }
        return aspectInfoList;
    }

    /**
     * 验证
     *  1. 切面类必须是被 @Aspect 标记
     *  2. 切面类必须被 @Order 标记
     *  3. 切面类必须集成 DefaultAspect 抽象类
     *  4. @Aspect Class 的 value != Aspect.class
     * @param aspectClass 切面类Class对象
     * @return
     */
    private boolean verifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class) &&
                aspectClass.isAnnotationPresent(Order.class) &&
                DefaultAspect.class.isAssignableFrom(aspectClass) &&
                aspectClass.getAnnotation(Aspect.class).value() != Aspect.class;
    }

}
