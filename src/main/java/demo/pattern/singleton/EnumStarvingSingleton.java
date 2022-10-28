package demo.pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author rwj
 * @Date 2022/10/29
 * @Description 基于枚举的饿汉模式 单例模式
 * 这种模式可以防御反射的攻击
 * 也可以防止序列化和反序列化的攻击
 */
public class EnumStarvingSingleton {

    // 1. 私有构造函数
    private EnumStarvingSingleton() {
    }

    // 2. 公有静态获取实例方法
    public static EnumStarvingSingleton getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    /**
     * 3. 内部枚举单例
     *  参考: https://blog.csdn.net/C_AJing/article/details/105857968
     */

    private enum ContainerHolder {  //枚举的本质是 final class Which extends Enum
        HOLDER;

        /**
         * 枚举元素是被声明成public static final的成员变量
         * 可以通过类名直接调用，并且在初始化枚举时 在static静态代码块中被初始化
         */
        private EnumStarvingSingleton instance;

        // 初始化枚举时初始化外部类实例
        ContainerHolder() { //每初始化一个枚举的元素（不是属性，是 Holder这种）都会调用一次枚举的构造方法和构造代码块{}
            instance = new EnumStarvingSingleton();
        }
    }

    /**
     * 尝试使用反射攻击，法相都无法通过反射获取实例
     * @param args
     */
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class clazz = ContainerHolder.class;
//        Constructor con = clazz.getDeclaredConstructor();   //java.lang.NoSuchMethodException: demo.pattern.singleton.EnumStarvingSingleton$ContainerHolder.<init>()
        Constructor con = clazz.getDeclaredConstructor(String.class, int.class);   //java.lang.IllegalArgumentException: Cannot reflectively create enum objects
        con.setAccessible(true);
        System.out.println(con.newInstance());
        System.out.println(EnumStarvingSingleton.getInstance());
    }
}
