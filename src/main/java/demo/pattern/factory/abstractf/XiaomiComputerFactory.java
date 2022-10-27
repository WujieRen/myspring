package demo.pattern.factory.abstractf;

import demo.pattern.factory.entity.Keyboard;
import demo.pattern.factory.entity.Mouse;
import demo.pattern.factory.entity.XiaomiKeyboard;
import demo.pattern.factory.entity.XiaomiMouse;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 */
public class XiaomiComputerFactory implements ComputerFactory{
    @Override
    public Mouse createMouse() {
        return new XiaomiMouse();
    }

    @Override
    public Keyboard createKeyboard() {
        return new XiaomiKeyboard();
    }
}
