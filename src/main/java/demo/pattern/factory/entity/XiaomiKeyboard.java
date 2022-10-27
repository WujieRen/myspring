package demo.pattern.factory.entity;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 */
public class XiaomiKeyboard implements Keyboard{
    @Override
    public void sayHello() {
        System.out.println("Hello，我是小米键盘");
    }
}
