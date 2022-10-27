package demo.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author rwj
 * @Date 2022/10/27
 * @Description
 * 获取成员变量并调用：
 *      1.批量获取
 *          1).Field[] getFields():获取所有的"公有字段"，包含继承字段
 *          2).Field[] getDeclaredFields():获取所有字段，包括：私有、受保护、默认、公有。不包括继承的字段
 *      2.获取单个的：
 *          1).public Field getField(String fieldName):获取名称为 fieldName，具有public修饰的某个字段，包含继承字段
 *          2).public Field getDeclaredField(String fieldName):获取指定名称为  fieldName，包含 private 修饰的某个字段，不包含继承字段
 *
 * 设置字段的值：
 *      Field --> public void set(Object obj,Object value):
 *          参数说明：
 *              1. obj：要设置的字段所在的对象；
 *              2. value：要为字段设置的值；
 */
public class FieldCollector {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        //获取Class对象
        Class reflectTargetClass = Class.forName("demo.reflect.ReflectTarget");

        //1.获取所有公有的字段
        System.out.println("************获取所有公有的字段********************");
        Field[] fieldArray = reflectTargetClass.getFields();
        for (Field f : fieldArray){
            System.out.println(f);
        }

        //2.获取所有的字段
        System.out.println("************获取所有的字段(包括私有、受保护、默认的)********************");
        fieldArray = reflectTargetClass.getDeclaredFields();
        for (Field f : fieldArray){
            System.out.println(f);
        }

        //3.获取单个特定公有的field
        System.out.println("*************获取公有字段并调用***********************************");
        Field f = reflectTargetClass.getField("name");
        System.out.println("公有的field name : " + f);

        ReflectTarget reflectTarget = (ReflectTarget)reflectTargetClass.getConstructor().newInstance();
        //4.给获取到的field赋值
        f.set(reflectTarget, "待反射一号");
        //5.验证对应的值name
        System.out.println("验证name : " + reflectTarget.name);


        //6.获取单个私有的Field
        System.out.println("**************获取私有字段targetInfo并调用********************************");
        f = reflectTargetClass.getDeclaredField("targetInfo");
        System.out.println(f);
        f.setAccessible(true);
        f.set(reflectTarget, "13810592345");
        System.out.println("验证信息" + reflectTarget);
    }
}
