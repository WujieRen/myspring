package demo.pattern.proxy.static_;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description
 */
public class AlipayToC implements Payment {
    private ToCpaymentImpl aliToCPayment;

    public AlipayToC(ToCpaymentImpl aliToCPayment) {
        this.aliToCPayment = aliToCPayment;
    }

    @Override
    public void pay() {
        before();
        aliToCPayment.pay();
        after();
    }

    private void before() {
        System.out.println("取款-----");
    }

    private void after() {
        System.out.println("支付-----");
    }
}
