package demo.pattern.factory.simple;

import demo.pattern.factory.entity.DellMouse;
import demo.pattern.factory.entity.HpMouse;
import demo.pattern.factory.entity.Mouse;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description 工厂模式
 *  适用场景：
 *      1. 需要创建的对象很少
 *      2. 客户不关心对象的创建过程/对客户隐藏创建的过程
 *  优点：
 *      1. 可以对创建的对象进行“加工”，对客户端隐藏相关细节
 *   缺点：
 *      1. 因创建逻辑复杂  或  创建对象过多       造成代码臃肿
 *      2. 新增，删除子类时，会影响到工厂类，违反开闭原则（一个软件实体，应该对扩展开放，对修改关闭）
 */
public class MouseFactory {

    public static Mouse createMouse(int type) {
        switch (type) {
            case 0:
                return new DellMouse();
            case 1:
                return new HpMouse();
            default:
                return new DellMouse();
        }
    }


    public static void main(String[] args) {
        Mouse mouse = MouseFactory.createMouse(0);
        mouse.sayHi();
        Mouse mouse1 = MouseFactory.createMouse(1);
        mouse1.sayHi();
    }
}
