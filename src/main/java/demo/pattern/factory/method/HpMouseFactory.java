package demo.pattern.factory.method;

import demo.pattern.factory.entity.HpMouse;
import demo.pattern.factory.entity.Mouse;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 */
public class HpMouseFactory implements MouseFactory{
    @Override
    public Mouse createMouse() {
        return new HpMouse();
    }
}
