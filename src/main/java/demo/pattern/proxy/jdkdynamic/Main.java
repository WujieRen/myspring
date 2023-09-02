package demo.pattern.proxy.jdkdynamic;

import demo.pattern.proxy.jdkdynamic.test.TestForceConvertion;
import demo.pattern.proxy.static_.Payment;
import demo.pattern.proxy.static_.ToBPaymentImpl;
import demo.pattern.proxy.static_.ToCpaymentImpl;

import java.lang.reflect.InvocationHandler;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description
 */
public class Main {
    public static void main(String[] args) {
        ToCpaymentImpl payment = new ToCpaymentImpl();
        InvocationHandler tocHandler = new AlipayInvocationHandler(payment);
//        ToCpaymentImpl toCpayment = JdkDynamicProxyUtil.newProxyInstance(payment, tocHandler);    //报错：class com.sun.proxy.$Proxy0 cannot be cast to class demo.pattern.proxy.static_.ToCpaymentImpl
        /**
         * 其实一個普通得类也可以生成代理对象，但是代理对岸相当于和被代理对象必须有一个共同的父类 —— 即，代理对象 是和 被代理对象 是同级的，必须有一个父类/接口去接收这个被代理对象
         * 比如用 Object 接收即可，但是生成的代理对象其实是 Object 的子类，所以无法访问被代理对象中的 方法
         */
//        Object toCpayment = JdkDynamicProxyUtil.newProxyInstance(payment, tocHandler);
//        toCpayment.pay();     生成的代理对象无法访问到被代理对象的方法
        /**
         * 即使给一个共同的父类，让目標方法可以被代理对象访问，也会因为生成的代理对象无法转换为 被代理对象的父类（不同类） 而报错  ClassCastException，案例见： {@link TestForceConvertion}
         *
         * 其实返回的是一个丢失了部分子类信息的 Object 对象，然后 Object 强转为 TestPayment 时因为是向下转型，这时类型已经不兼容，所以就会报错 ClassCaseException
         */
        TestPayment toCpayment = JdkDynamicProxyUtil.newProxyInstance(payment, tocHandler);
        toCpayment.pay();

//        System.out.println(toCpayment.getClass().getSimpleName());
//        toCpayment.pay();

//        System.out.println("\n");

        /*Payment payment1 = new ToBPaymentImpl();
        InvocationHandler toBHandler = new AlipayInvocationHandler(payment1);
        Payment toBPayment = JdkDynamicProxyUtil.newProxyInstance(payment1, toBHandler);
        toBPayment.pay();*/
    }
}
