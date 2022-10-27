package demo.reflect;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 */
public class ReflectTarget {

    public static void main(String[] args) throws ClassNotFoundException {
        //第一种方式获取Class对象
        ReflectTarget reflectTarget = new ReflectTarget();
        Class reflectTargetClass1 = reflectTarget.getClass();
        System.out.println("1st : " + reflectTargetClass1.getName());
        //第二种方式获取Class对象
        Class reflectTargetClass2 = ReflectTarget.class;
        System.out.println("2nd: " + reflectTargetClass2.getName());
        //判断第一种方式获取的class对象和第二种方式获取的是否是同一个
        System.out.println(reflectTargetClass1 == reflectTargetClass2);
        //第三种方式来获取Class对象
        Class reflectTargetClass3 = Class.forName("demo.reflect.ReflectTarget");
        System.out.println("3rd: " + reflectTargetClass3.getName());
        System.out.println(reflectTargetClass2 == reflectTargetClass3);
    }

}
