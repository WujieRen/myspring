package demo.pattern.proxy.cglib;

import demo.pattern.proxy.jdkdynamic.AlipayInvocationHandler;
import demo.pattern.proxy.jdkdynamic.JdkDynamicProxyUtil;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description Spring AOP 底层同时支持 JDK动态代理 和 CGLib动态代理。
 *                  默认策略： Bean实现了接口就就用JDK动态代理，否则用CGLib动态代理
 */
public class Main {
    public static void main(String[] args) {
        CommenPayment commenPayment = new CommenPayment();

          /** jdk动态代理报错，因为jdk动态代理要求被代理对象必须实现接口 */
//        CommenPayment aliPayment = JdkDynamicProxyUtil.newProxyInstance(commenPayment, new AlipayInvocationHandler(commenPayment));
//        aliPayment.pay();

        /** CGLib 不要求被代理类实现接口 */
        CommenPayment commenPaymentProxy = CglibUtil.createProxy(commenPayment, new AlipayMethodInterceptor());
        commenPaymentProxy.pay();

    }
}
