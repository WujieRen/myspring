package demo.pattern.factory.entity;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 */
public class HpKeyboard implements Keyboard{
    @Override
    public void sayHello() {
        System.out.println("Hello，我是惠普键盘");
    }
}
