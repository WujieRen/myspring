package demo.pattern.proxy.static_;

import demo.pattern.proxy.jdkdynamic.TestPayment;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description
 */
public class ToCpaymentImpl extends TestPayment/*implements Payment*/ {

//    @Override
    public void pay() {
        System.out.println("C端支付");
    }

}
