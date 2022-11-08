package org.myspring.mvc.annotation;

import org.myspring.mvc.type.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author rwj
 * @Date 2022/11/8
 * @Description 用在类或方法上。用于标记Controller的方法的请求路径与请求方式
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * 请求路径
     * @return
     */
    String value() default "";

    /**
     * 请求方式
     * @return
     */
    RequestMethod method() default RequestMethod.GET;

}
