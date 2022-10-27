package demo.annotation;

/**
 * @Author rwj
 * @Date 2022/10/28
 * @Description
 */
@CourseInfoAnnotation(courseName = "类——课程名称", courseTag = "类——学习", courseProfile = "类——课程简介blablabla...")
public class Course {


    @PersonInfoAnnotation(name = "成员变量——任武杰", language = {"Jasva", "Python", "Scala", "Shell", "SQL"})
    private String author;

    @CourseInfoAnnotation(courseName = "方法——课程名称", courseTag = "方法——学习", courseProfile = "方法——课程简介blablabla...")
    public void getCourseInfo() {

    }

}
