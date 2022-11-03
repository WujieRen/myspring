package demo.pattern.responsibilitychain.demo1;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description
 */
public class ThirdChainHandler extends AbstractChainHandler {

    @Override
    public Boolean checkAction(SourceOrderItemModel sourceOrderItemModel) {
        System.out.println("第三部校验");
        return false;
    }

}
