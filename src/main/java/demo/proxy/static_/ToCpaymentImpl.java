package demo.proxy.static_;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description
 */
public class ToCpaymentImpl implements Payment {

    @Override
    public void pay() {
        System.out.println("C端支付");
    }

}
