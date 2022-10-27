package demo.pattern.factory.entity;

import java.security.Key;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 */
public class DellKeyboard implements Keyboard {
    @Override
    public void sayHello() {
        System.out.println("Hello，我是戴尔键盘");
    }
}
