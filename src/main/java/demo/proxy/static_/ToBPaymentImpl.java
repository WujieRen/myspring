package demo.proxy.static_;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description
 */
public class ToBPaymentImpl implements Payment {
    @Override
    public void pay() {
        System.out.println("B端支付");
    }
}
