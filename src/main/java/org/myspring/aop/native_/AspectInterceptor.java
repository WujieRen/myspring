package org.myspring.aop.native_;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.myspring.aop.native_.aspect.AspectInfo;
import org.myspring.util.ValidationUtil;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

/**
 * @Author rwj
 * @Date 2022/11/6
 * @Description 横切逻辑 以及 被代理对象 方法的按序执行
 *                  CGLib动态代理的Interceptor，可以类比为jdk动态代理的 InvovationHandler，即对横切逻辑的管理。
 */
public class AspectInterceptor implements MethodInterceptor {

    private Class<?> targetClass;      //目标类，被代理对象
    private List<AspectInfo> aspectInfoList;    //切面类及其顺序

    // 往哪个类织入哪些横切类，以及织入顺序编号
    public AspectInterceptor(Class<?> targetClass, List<AspectInfo> aspectInfoList) {
        this.targetClass = targetClass;
        this.aspectInfoList = sortAspectInfoList(aspectInfoList);
    }

    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        aspectInfoList.sort(Comparator.comparingInt(AspectInfo::getOrder));
        return aspectInfoList;
    }

    /**
     * 实现横切逻辑以及被代理对象的按序执行
     * @param targetObject "this", the enhanced object
     *              被代理对象
     * @param method intercepted Method
     *                被代理对象的目标方法
     * @param args argument array; primitive types are wrapped
     *              被代理对象的目标方法的参数数组
     * @param methodProxy used to invoke super (non-intercepted method); may be called
     *              CGLib生成的一个用来代替Method的动态代理Method对象
     * as many times as needed
     * @return  被代理对象方法执行后返回的值
     * @throws Throwable
     */
    @Override
    public Object intercept(Object targetObject, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        /** 没有切面类的话，不能影响原方法的执行 */
        if(ValidationUtil.isEmpty(aspectInfoList)) {
            returnValue = methodProxy.invokeSuper(targetObject, args);
            return returnValue;
        }
        try {
            // 1. 按Order升序执行切面类的 before()
            invokeBeforeAdvices(method, args);
            // 2. 执行被代理类的目标方法
            returnValue = methodProxy.invokeSuper(targetObject, args);
            // 3. 如果被代理类方法正常执行，按Order顺序降序执行切面类的 AfterReturning()
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        } catch (Throwable e) {
            // 4. 如果被代理类方法抛出异常，按Order顺序降序执行切面类的 AfterThrowing()
            invokeAfterThrowingAdvides(method, args, e);
        }

        return returnValue;
    }

    private void invokeAfterThrowingAdvides(Method method, Object[] args, Throwable e) throws Throwable {
        for(int i = aspectInfoList.size() - 1; i >= 0; i--) {
            aspectInfoList.get(i).getAspect().afterThrowing(targetClass, method, args, e);
        }
    }

    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object result = null;
        for(int i = aspectInfoList.size() - 1; i >= 0; i--) {
            result = aspectInfoList.get(i).getAspect().afterReturning(targetClass, method, args, returnValue);
        }
        return result;
    }

    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for(AspectInfo aspectInfo : aspectInfoList) {
            aspectInfo.getAspect().before(targetClass, method, args);
        }
    }
}
