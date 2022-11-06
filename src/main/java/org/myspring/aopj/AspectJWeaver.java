package org.myspring.aopj;

import org.myspring.aop.ProxyCreator;
import org.myspring.aop.annotation.Order;
import org.myspring.aop.aspect.DefaultAspect;
import org.myspring.aopj.annotation.AspectJ;
import org.myspring.aopj.aspect.AspectJInfo;
import org.myspring.core.BeanContainer;
import org.myspring.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author rwj
 * @Date 2022/11/6
 * @Description
 *  1. import dependency
 *  2. 将横切逻辑织入到被代理对象，并生成代理对象的实例
 *      1. 获取所有切面类
 *      2. 构造List<AspectJInfo>
 *      3. 遍历所有容器Bean对应的Key（这里必须遍历所有实例，因为 @AspectJ注解是通过 pointcut 表达式来确定织入目标 —— 而不是像Aspect一样通过注解类来确定织入目标，所以无法获取指定范围的Bean）
 *          1. 粗筛符合条件的类 —— 类级别粗筛（AspectWeaver只支持类级别粒度的织入）
 *          2. 方法织入时精确匹配被代理方法是否符合 pointcut expression  —— 方法级别的织入（这个要在Interceptor中处理）
 *          3. 创建织入了切面逻辑的动态代理对象
 *  3. 将动态代理实例添加到容器里，取代未被代理的实例
 */
public class AspectJWeaver {

    private BeanContainer beanContainer;

    public AspectJWeaver(BeanContainer beanContainer) {
        this.beanContainer = beanContainer;
    }

    public void doAOP() {
        // 1. 获取所有切面类
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(AspectJ.class);
        if(ValidationUtil.isEmpty(aspectSet)){return;}
        // 2.构造List<AspectJInfo>
        List<AspectJInfo> aspectJInfoList = packAspectJInfoList(aspectSet);
        if(ValidationUtil.isEmpty(aspectJInfoList)) return;
        // 3.遍历所有容器实例
        Set<Class<?>> classSet = beanContainer.getClasses();
        for (Class<?> targetClass : classSet) {
            // 4.粗筛符合条件的Class，即符合 @Aspect(point ="expression") 表达式的 Class
            List<AspectJInfo> roughMatchedAspectList = collectRoughMatchedAspectJListForSpecificClass(aspectJInfoList, targetClass);
            // 5.织入对应的Aspect逻辑
            wrapIfNecessary(roughMatchedAspectList, targetClass);
        }
    }

    private void wrapIfNecessary(List<AspectJInfo> roughMatchedAspectList, Class<?> targetClass) {
        if(ValidationUtil.isEmpty(roughMatchedAspectList)) {
            return;
        }
        AspectJInterceptor aspectJInterceptor = new AspectJInterceptor(targetClass, roughMatchedAspectList);
        Object proxyBean = ProxyCreator.createProxy(targetClass, aspectJInterceptor);
        beanContainer.addBean(targetClass, proxyBean);
    }

    private List<AspectJInfo> collectRoughMatchedAspectJListForSpecificClass(List<AspectJInfo> aspectJInfoList, Class<?> targetClass) {
        List<AspectJInfo> roughMatchedAspectList = new ArrayList<>();
        for(AspectJInfo aspectJInfo : aspectJInfoList) {
            if(aspectJInfo.getPointcutLocator().roughMatches(targetClass)) {
                roughMatchedAspectList.add(aspectJInfo);
            }
        }
        return roughMatchedAspectList;
    }

    private List<AspectJInfo> packAspectJInfoList(Set<Class<?>> aspectSet) {
        List<AspectJInfo> aspectJInfoList = new ArrayList<>();
        for (Class<?> clazz : aspectSet) {
            if(verifyAspectJ(clazz)) {
                Order orderTag = clazz.getAnnotation(Order.class);
                AspectJ aspectJTag = clazz.getAnnotation(AspectJ.class);
                DefaultAspect aspectBean = (DefaultAspect) beanContainer.getBean(clazz);
                PointcutLocator pointcutLocator = new PointcutLocator(aspectJTag.pointcut());
                AspectJInfo aspectJInfo = new AspectJInfo(orderTag.value(), aspectBean, pointcutLocator);
                aspectJInfoList.add(aspectJInfo);
            } else {
                //不遵守规范则直接抛出异常
                throw new RuntimeException("@Aspect and @Order must be added to the Aspect class, and Aspect class must extend from DefaultAspect");
            }
        }
        return aspectJInfoList;
    }

    /**
     *  Aspect 的 value 是 Class<?> ，所以原先这里还需要验证 @aspect.value() != Aspect.clazz
     *  AspectJ 是通过 AspectJ 的 pointcut expression （String） 来解析，所以这里不要验证其 pointcut/value
     * @param clazz 要验证的 Class<?>
     * @return true or false
     */
    private boolean verifyAspectJ(Class<?> clazz) {
        return clazz.isAnnotationPresent(Order.class) &&
                clazz.isAnnotationPresent(AspectJ.class) &&
                DefaultAspect.class.isAssignableFrom(clazz);
    }

}
