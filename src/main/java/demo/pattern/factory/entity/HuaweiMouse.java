package demo.pattern.factory.entity;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 */
public class HuaweiMouse implements Mouse{

    @Override
    public void sayHi() {
        System.out.println("HI，我是华为鼠标");
    }
}
