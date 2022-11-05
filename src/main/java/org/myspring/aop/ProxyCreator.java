package org.myspring.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @Author rwj
 * @Date 2022/11/6
 * @Description 创建代理对象，对应CGLib里的创建代理对象
 */
public class ProxyCreator {

    /**
     * 创建动态代理对象并返回
     * @param targetClass   被代理目标类Class
     * @param interceptor   拦截器
     * @return
     */
    public static Object createProxy(Class<?> targetClass, MethodInterceptor interceptor) {
        return Enhancer.create(targetClass, interceptor);
    }
}
