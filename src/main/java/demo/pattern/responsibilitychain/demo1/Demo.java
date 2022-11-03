package demo.pattern.responsibilitychain.demo1;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description
 */
public class Demo {
    public static void main(String[] args) {
        FirstChainHandler first = new FirstChainHandler();
        SecondChainHandler second = new SecondChainHandler();
        ThirdChainHandler third = new ThirdChainHandler();
        first.setNextHandler(second);
        second.setNextHandler(third);

        first.checkAction(new SourceOrderItemModel());
    }
}
