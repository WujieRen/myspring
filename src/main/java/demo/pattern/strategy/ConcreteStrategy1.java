package demo.pattern.strategy;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description
 */
public class ConcreteStrategy1 implements IStrategy {
    @Override
    public void algorithmMethod() {
        System.out.println("策略1~~~");
    }
}
