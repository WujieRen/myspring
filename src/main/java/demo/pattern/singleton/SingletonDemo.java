package demo.pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author rwj
 * @Date 2022/10/28
 * @Description 单例模式：确保一个类只有一个实例，并对外提供统一的访问方式
 * <p>
 * 单纯的饿汉模式和懒汉模式都不够安全，可以被反射 轻松绕过
 */
public class SingletonDemo {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        System.out.println("--------- 饿汉模式  ---------");
        System.out.println(StarvingSingleton.getInstance());
        System.out.println(StarvingSingleton.getInstance());

        System.out.println("--------- 懒汉模式  ---------");
        System.out.println(LazyDoubleCheckSingleton.getInstance());
        System.out.println(LazyDoubleCheckSingleton.getInstance());

        System.out.println("--------- 反射绕过，可以发现创建的对象和以上的都不一样  ---------");
        Class eClazz = StarvingSingleton.class;
        Constructor eCon = eClazz.getDeclaredConstructor();
        eCon.setAccessible(true);
        System.out.println(eCon.newInstance());

        Class lClazz = LazyDoubleCheckSingleton.class;
        Constructor lCon = lClazz.getDeclaredConstructor();
        lCon.setAccessible(true);
        System.out.println(lCon.newInstance());

        System.out.println("--------- 装备了枚举的饿汉单例模式，发现经过反射获取到的实例和根据实例方法获取到的是同一个对象 ---------");
        Class clazz = EnumStarvingSingleton.class;
        Constructor enumCon = clazz.getDeclaredConstructor();
        enumCon.setAccessible(true);
        EnumStarvingSingleton enumSingleTon = (EnumStarvingSingleton) enumCon.newInstance();
        System.out.println(enumSingleTon.getInstance());
        System.out.println(EnumStarvingSingleton.getInstance());

    }
}
