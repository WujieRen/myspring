package demo.pattern.proxy.static_;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description
 */
public class AlipayToB implements Payment{

    private ToBPaymentImpl toBPayment;

    public AlipayToB(ToBPaymentImpl toBPayment) {
        this.toBPayment = toBPayment;
    }

    @Override
    public void pay() {
        before();
        toBPayment.pay();
        after();
    }

    private void before() {
        System.out.println("取款-----");
    }

    private void after() {
        System.out.println("支付-----");
    }

}
