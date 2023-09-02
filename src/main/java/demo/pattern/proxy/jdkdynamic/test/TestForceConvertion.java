package demo.pattern.proxy.jdkdynamic.test;

/**
 * @author rwj
 * @date 2023/9/2
 */
public class TestForceConvertion {

    /**
     * java.lang.ClassCastException: class demo.pattern.proxy.jdkdynamic.test.A cannot be cast to class demo.pattern.proxy.jdkdynamic.test.B (demo.pattern.proxy.jdkdynamic.test.A and demo.pattern.proxy.jdkdynamic.test.B are in unnamed module of loader 'app')
     * @param args
     */
    public static void main(String[] args) {
        A a = new A();
        B b = (B) a;
        System.out.println(b);

        // 按这里来看，之前动态代理测试那里不应该报错啊，因为这里父类对象引用是可以指向子类对象的
        A a2 = new B();
        B b2 = (B) a2;
        System.out.println(b2);

        //其实本质原因在于：向上转型会丢失子类特有的方法
        /**
         * 1、父类引用可以指向子类对象，子类引用不能指向父类对象；
         * 2、把子类对象直接赋给父类引用叫做向上转型，向上转型不用强制转型，如Father f1=new Son()；
         * 3、把指向子类对象的父类引用赋给子类引用叫做向下转型，要强制转型，如 Son s1 = (Son)f1。
         * 4、向上转型会丢失子类特有的方法，但是子类 overriding 父类的方法，子类方法有效。
         */
        One t = new Two(); //向上转型，即父类引用指向子类对象，此时子类对象的类型为父类的类型
        t.foo();
//        t.dosth();        //编译错误
        t = (Two)t;
//        t.dosth();        //编译错误
        ((Two) t).dosth();//编译成功
    }

}


class One {
    public void foo() {
        System.out.println("One");
    }
}
class Two extends One {
    public void foo() {
        System.out.println("Two");
    }
    public void dosth(){
        System.out.println("Two again");
    }
}