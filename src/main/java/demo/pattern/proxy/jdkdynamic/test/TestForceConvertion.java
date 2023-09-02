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
    }

}
