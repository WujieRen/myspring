package demo.pattern.factory.method;

import demo.pattern.factory.entity.HuaweiMouse;
import demo.pattern.factory.entity.Mouse;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 */
public class HuaweiMouseFactory implements MouseFactory{
    @Override
    public Mouse createMouse() {
        return new HuaweiMouse();
    }
}
