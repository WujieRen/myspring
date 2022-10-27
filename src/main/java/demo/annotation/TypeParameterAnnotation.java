package demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_PARAMETER) //用在泛型形参上
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeParameterAnnotation {
}
