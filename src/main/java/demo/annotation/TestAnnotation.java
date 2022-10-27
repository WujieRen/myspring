package demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author rwj
 * @Date 2022/10/28
 * @Description 通过反编译，可以知道 RetentionPolicy 各种策略生命周期
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface TestAnnotation {
}
