package demo.annotation;

/**
 * @Author rwj
 * @Date 2022/10/28
 * @Description
 */
public class TypeParameterDemo<@TypeParameterAnnotation T> {

    public <@TypeParameterAnnotation U> T foo(T t){
        return null;
    }

}
