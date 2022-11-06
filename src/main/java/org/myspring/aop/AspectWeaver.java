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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author rwj
 * @Date 2022/11/6
 * @Description
 *      1. 将横切逻辑织入到被代理对象，以生成动态代理对象
 *          1. 创建被代理对象的动态代理实例
 *              1. 获取所有被 @Aspect 标记的切面类
 *              2. 将切面类按照不同的织入目标进行切分
 *              3. 按照不同织入目标按序织入Aspect的逻辑
 *                  1. 获取被代理类的集合
 *                  遍历
 *                      1. 创建被代理类的代理对象实例
 *      2. 织入
 *          将动态代理实例添加到容器里，取代未被代理的实例
 *
 *
 * 这种方式比第一种方式要好很多，第一种方式是对容器中所有Bean进行遍历同时判断每个Bean是否需要织入逻辑
 * 而且通过 Controller.. 等注解去容器中获取被这些注解标记的Bean，大大的减少了对容器中Bean扫描的数量
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
        // 2.将切面类按照不同的织入目标进行切分
        Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap = new ConcurrentHashMap<>();
        for(Class<?> aspectClass : aspectSet) {
            if(verifyAspect(aspectClass)) {
                categorizedAspect(categorizedMap, aspectClass);
            } else {
                throw new RuntimeException("@Aspect and @Order must be added to the Aspect class, and Aspect class must extend from DefaultAspect");
            }
        }
        // 3.按照不同织入目标按序织入Aspect的逻辑
        if(ValidationUtil.isEmpty(categorizedMap)) {
            return;     //没有任何需要织入的切面类，直接返回
        }
        for(Class<? extends Annotation> category : categorizedMap.keySet()) {
            weaveByCategory(category, categorizedMap.get(category));
        }
    }

    /** 这种方式比第一种方式要好很多，第一种方式是对容器中所有Bean进行遍历同时判断每个Bean是否需要织入逻辑 */
    /** 而且通过 Controller.. 等注解去容器中获取被这些注解标记的Bean，大大的减少了对容器中Bean扫描的数量 */
    private void weaveByCategory(Class<? extends Annotation> category, List<AspectInfo> aspectInfoList) {
        // 1. 获取被代理类的集合
        Set<Class<?>> classSet = beanContainer.getClassesByAnnotation(category);
        // 2. 遍历被代理类
        for(Class targetClass : classSet) {
            // 创建实例
            AspectInterceptor aspectInterceptor = new AspectInterceptor(targetClass, aspectInfoList);
            Object proxyBean = ProxyCreator.createProxy(targetClass, aspectInterceptor);
            // 3. 将动态代理对象实例添加到容器中，取代未被代理的实例
            beanContainer.addBean(targetClass, proxyBean);
        }
    }

    private void categorizedAspect(Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap, Class<?> aspectClass) {
        Order orderTag = aspectClass.getAnnotation(Order.class);
        Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
        DefaultAspect aspectObject = (DefaultAspect) beanContainer.getBean(aspectClass);
        AspectInfo aspectInfo = new AspectInfo(orderTag.value(), aspectObject);
        if(ValidationUtil.isEmpty(categorizedMap.get(aspectTag.value()))) {
            List<AspectInfo> aspectInfoList = new ArrayList<>();
            aspectInfoList.add(aspectInfo);
            categorizedMap.put(aspectTag.value(), aspectInfoList);
        } else {
            List<AspectInfo> aspectInfoList = categorizedMap.get(aspectTag.value());
            aspectInfoList.add(aspectInfo);
        }
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
