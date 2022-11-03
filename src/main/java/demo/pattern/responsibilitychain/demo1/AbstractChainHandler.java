package demo.pattern.responsibilitychain.demo1;

import java.util.Objects;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description
 */
public abstract class AbstractChainHandler {
    /**
     *下一个处理器
     */
    protected AbstractChainHandler nextAbstractChainHandler;

    /**
     * 校验结果
     * @return
     */
    public abstract Boolean checkAction(SourceOrderItemModel sourceOrderItemModel);


    public void setAbstractChainHandler(AbstractChainHandler nextAbstractChainHandler) {
        this.nextAbstractChainHandler = nextAbstractChainHandler;
    }

    protected Boolean checkNextHandler(SourceOrderItemModel sourceOrderItemModel){
        //校验是否含有下一级处理器
        if (Objects.nonNull(nextAbstractChainHandler)){
            //调用下一级处理器
            return nextAbstractChainHandler.checkNextHandler(sourceOrderItemModel);
        }else {
            //没有下一级处理器，直接返回false
            return false;
        }
    }

}
