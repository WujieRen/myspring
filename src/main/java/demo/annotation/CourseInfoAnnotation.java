package demo.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解实现
 *  1. 注解和注解的属性只能用 public 或 缺省（默认级别，包可见） 修饰
 *  2. 注解属性支持的类型：
 *      1。 所有基本类型
 *      2. String类型
 *      3. Class类型
 *      4. Enum类型
 *      5. Annotation类型
 *      6. 以上所有类型数组
 *  3. 注解属性只能以类似定义方法的形式来实现，可以有默认值，且只能用 public 或 缺省 修饰
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseInfoAnnotation {

    //课程名称
    public String courseName();

    //课程标签
    public  String courseTag();

    //课程简介
    public String courseProfile();

    //课程序号
    public int courseIndex() default 303;

}
