package demo.annotation;

/**
 * @Author rwj
 * @Date 2022/10/28
 * @Description 单纯运行这个发现注解其实没上什么用，但也是因此，注解加上或者去掉并不会影响程序的运行
 */
public class AnnotationDemo {
    public static void main(String[] args) {
        Course course = new Course();
        course.getCourseInfo();
    }
}
