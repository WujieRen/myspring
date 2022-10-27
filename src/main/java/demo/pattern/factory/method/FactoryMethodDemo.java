package demo.pattern.factory.method;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description 工厂方法模式：定义一个用于创建对象的接口，让子类决定实例化哪一个 ———— 对类的实例化延迟到子类实现。
 *                  工厂方法模式是对简单工厂模式的进一步抽象和扩展，在保留了工厂的封装优点的同时，让扩展和继承变得更简单，增加了多态的体现。
 * 优点：
 *      1. 遵循开闭原则
 *      2. 对客户隐藏创建细节
 *      3. 遵循单一原则
 * 缺点：
 *      1. 添加子类（工厂）的时候“拖家带口，实体类和工厂类成对增加
 *      2. 只支持同一类产品的创建
 *
 *              随着业务增长，将会有越来越多的 Controller 来满足业务的需求，对于工厂方法模式而言，还需要创建同等数量的工厂方法类，代码同样还是比较臃肿。
 *              其次，工厂方法模式只支持同一类产品创建，随着业务越来越复杂，Controller 会处理越来越多不同类别的请求。
 *
 *              因此，只靠工厂方法模式仍不能满足我的业务需求，且改动成本较大，后续同样不利于扩展，并没有从根本上解决问题。
 *              所以，需要进一步寻找更加可行的方案。
 *              由于框架涉及到对类的管理，因此要使用和工厂相关的模式才能解决问题，那么还有和工厂相关的设计模式能够弥补以上至少一个缺点吗？ ————  抽象工厂模式
 */
public class FactoryMethodDemo {
    public static void main(String[] args) {
        MouseFactory mouseFactory = new HpMouseFactory();
        mouseFactory.createMouse().sayHi();
        MouseFactory mouseFactory1 = new DellMouseFactory();
        mouseFactory1.createMouse().sayHi();
        // 新增一个华为鼠标，需要新增华为鼠标工厂 ——  类成对增加
        MouseFactory mouseFactory2 = new HuaweiMouseFactory();
        mouseFactory2.createMouse().sayHi();
    }
}
