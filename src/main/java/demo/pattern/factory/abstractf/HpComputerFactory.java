package demo.pattern.factory.abstractf;

import demo.pattern.factory.entity.HpKeyboard;
import demo.pattern.factory.entity.HpMouse;
import demo.pattern.factory.entity.Keyboard;
import demo.pattern.factory.entity.Mouse;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 */
public class HpComputerFactory implements ComputerFactory{
    @Override
    public Mouse createMouse() {
        return new HpMouse();
    }

    @Override
    public Keyboard createKeyboard() {
        return new HpKeyboard();
    }
}
