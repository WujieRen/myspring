package demo.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author rwj
 * @Date 2022/10/28
 * @Description
 */
public class AnnotationParser {

    //解析类的注解
    public static void parseTypeAnnotation() throws ClassNotFoundException {
        System.out.println("-----------  类注解  -----------");
        Class clazz = Class.forName("demo.annotation.Course");
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            CourseInfoAnnotation courseInfoAnnotation = (CourseInfoAnnotation) annotation;
            System.out.println("课程名称：" + courseInfoAnnotation.courseName() + "\n"
                    + "课程介绍：" + courseInfoAnnotation.courseProfile() + "\n"
                    + "课程标签：" + courseInfoAnnotation.courseTag() + "\n"
                    + "课程标签：" + courseInfoAnnotation.courseIndex() + "\n"
            );
        }
    }


    //解析成员变量上的标签
    public static void parseFieldAnnotation() throws ClassNotFoundException {
        System.out.println("-----------  成员变量注解  -----------");
        Class clazz = Class.forName("demo.annotation.Course");
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            //判断成员变量中是否有指定注解类型的注解
            boolean hasAnnotation = f.isAnnotationPresent(PersonInfoAnnotation.class);
            if (hasAnnotation) {
                PersonInfoAnnotation personInfoAnnotation = f.getAnnotation(PersonInfoAnnotation.class);
                System.out.println("名字：" + personInfoAnnotation.name() + "\n" +
                        "年龄：" + personInfoAnnotation.age() + "\n" +
                        "性别：" + personInfoAnnotation.gender() + "\n");
                for (String language : personInfoAnnotation.language()) {
                    System.out.println("开发语言：" + language);
                }
            }
        }
    }

    //解析方法注解
    public static void parseMethodAnnotation() throws ClassNotFoundException {
        System.out.println("-----------  方法注解  -----------");
        Class clazz = Class.forName("demo.annotation.Course");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            boolean annotationPresent = method.isAnnotationPresent(CourseInfoAnnotation.class);
            if (annotationPresent) {
                CourseInfoAnnotation annotation = method.getAnnotation(CourseInfoAnnotation.class);
                System.out.println("课程名称：" + annotation.courseName() + "\n"
                        + "课程介绍：" + annotation.courseProfile() + "\n"
                        + "课程标签：" + annotation.courseTag() + "\n"
                        + "课程标签：" + annotation.courseIndex() + "\n");
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        parseTypeAnnotation();
        parseFieldAnnotation();
        parseMethodAnnotation();
    }
}
