package demo.pattern.factory.abstractf;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description 抽象工厂模式：提供一个创建一系列相关或相互依赖对象的接口；抽象工厂模式侧重的是一个产品族（工厂方法模式则侧重于同一产品等级）。
 *  优点：
 *      1. 解决了工厂方法模式只能创建一个产品的弊端
 *  缺点：
 *      1. 新增产品族仍需要拖家带口，且已有工厂需要新增新产品接口，依旧违反开闭原则。
 *
 *      此时，工厂模式相关的牌已打完，仍没有解决该缺点，Spring是如何解决该问题的呢？ ————  结合 工厂模式  和  反射机制 实现的IOC容器值得借鉴。
 */
public class AbstractFactoryDemo {
    public static void main(String[] args) {
        //可以看到，抽象工厂模式解决了工场只能创建一种产品的弊端
        ComputerFactory computerFactory = new DellComputerFactory();
        computerFactory.createMouse().sayHi();
        computerFactory.createKeyboard().sayHello();
        //但是抽象工厂模式并没有解决增加增加新产品时需要修改已有工厂的问题
        //比如新增小米电脑产品族，既需要修改所有工厂，违反开闭原则
        ComputerFactory computerFactory1 = new XiaomiComputerFactory();
        computerFactory1.createMouse().sayHi();
        computerFactory1.createKeyboard().sayHello();
    }
}
