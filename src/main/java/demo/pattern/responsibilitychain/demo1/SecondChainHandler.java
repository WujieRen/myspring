package demo.pattern.responsibilitychain.demo1;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description
 */
public class SecondChainHandler extends AbstractChainHandler{

    private AbstractChainHandler nextHandler;

    @Override
    public Boolean checkAction(SourceOrderItemModel sourceOrderItemModel) {
        System.out.println("第二步校验");
        return nextHandler.checkAction(sourceOrderItemModel);
    }

    public void setNextHandler(AbstractChainHandler handler){
        this.nextHandler = handler;
    }
}
