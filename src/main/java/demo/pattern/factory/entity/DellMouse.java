package demo.pattern.factory.entity;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 */
public class DellMouse implements Mouse{
    @Override
    public void sayHi() {
        System.out.println("Hi，我是戴尔鼠标");
    }
}
