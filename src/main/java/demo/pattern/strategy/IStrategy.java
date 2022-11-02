package demo.pattern.strategy;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description 策略接口
 */
public interface IStrategy {
    //定义的抽象算法方法 来约束具体的算法实现方法
    public void algorithmMethod();
}
