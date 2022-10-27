package demo.pattern.factory.abstractf;

import demo.pattern.factory.entity.Keyboard;
import demo.pattern.factory.entity.Mouse;
import demo.pattern.factory.method.MouseFactory;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 */
public interface ComputerFactory {
    Mouse createMouse();
    Keyboard createKeyboard();
}
