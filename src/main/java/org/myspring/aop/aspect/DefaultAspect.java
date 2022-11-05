package org.myspring.aop.aspect;

import java.lang.reflect.Method;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description 横切逻辑骨架，所有被@Aspect标记的切面类必须要实现的类
 */
public abstract class DefaultAspect {


    /**
     * 被代理方法执行前置Advice，事前拦截
     *
     * @param targetClass 被代理类
     * @param method      被代理对象要执行的方法
     * @param args        被代理对象的目标方法对应的的参数
     */
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {

    }


    /**
     * 被代理方法执行后置Advice，事后拦截
     *
     * @param targetClass 被代理的目标类
     * @param method      被代理的目标方法
     * @param args        被代理的目标方法对应的参数列表
     * @param returnValue 被代理的目标方法执行后的返回值
     * @return
     */
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        return returnValue;
    }


    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {

    }

}
