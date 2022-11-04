package demo.pattern.proxy.static_;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description
 */
public class Main {
    public static void main(String[] args) {
        AlipayToC alipayToC = new AlipayToC(new ToCpaymentImpl());
        alipayToC.pay();
        System.out.println("\n");
        AlipayToB alipayToB = new AlipayToB(new ToBPaymentImpl());
        alipayToB.pay();
    }
}
