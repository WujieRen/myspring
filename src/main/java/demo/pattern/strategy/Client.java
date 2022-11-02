package demo.pattern.strategy;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description 策略模式：
 *
 *      参考：https://www.cnblogs.com/lewis0077/p/5133812.html
 *
 *      优点：
 *          扩展性良好，新增策略时只需新增类即可
 *      缺点：
 *          使用者需要知道每个策略对应的使用场景
 *
 */
public class Client {
    public static void main(String[] args) {
        //1. 创建具体的策略实现
        IStrategy strategy = new ConcreteStrategy1();
        //2.在创建策略上下文的同时，将具体的策略实现对象注入到策略上下文当中
        StrategyCointext cointext = new StrategyCointext(strategy);
        //3.调用上下文对象的方法来完成对具体策略实现的回调
        cointext.action();
    }
}
