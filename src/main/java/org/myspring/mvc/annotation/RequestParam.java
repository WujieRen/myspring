package org.myspring.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author rwj
 * @Date 2022/11/8
 * @Description 用在方法上，方法的参数名称
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    /**
     * 参数名
     * @return
     */
    String value() default "";
}
