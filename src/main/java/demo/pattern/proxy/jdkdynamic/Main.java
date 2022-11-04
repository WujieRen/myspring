package demo.pattern.proxy.jdkdynamic;

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
        Payment payment = new ToCpaymentImpl();
        InvocationHandler tocHandler = new AlipayInvocationHandler(payment);
        Payment toCpayment = JdkDynamicProxyUtil.newProxyInstance(payment, tocHandler);
        toCpayment.pay();

        System.out.println("\n");

        Payment payment1 = new ToBPaymentImpl();
        InvocationHandler toBHandler = new AlipayInvocationHandler(payment1);
        Payment toBPayment = JdkDynamicProxyUtil.newProxyInstance(payment1, toBHandler);
        toBPayment.pay();
    }
}
