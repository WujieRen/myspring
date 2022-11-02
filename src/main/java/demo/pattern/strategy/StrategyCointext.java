package demo.pattern.strategy;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description 策略上下文。封装客户端对策略的直接访问，隔离变化。
 */
public class StrategyCointext {

    //持有一个策略实现的引用
    private IStrategy strategy;

    //注入具体的构造类
    public StrategyCointext(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void action() {
        //调用策略的具体方式
        strategy.algorithmMethod();
    }
}
