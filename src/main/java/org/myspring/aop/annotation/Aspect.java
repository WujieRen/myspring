package org.myspring.aop.annotation;

import java.lang.annotation.*;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description 被@Aspect标记的，就是切面类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 指定Aspect应用于哪种资源上（Controller.class、Service.class）
     * @return
     */
    Class<? extends Annotation> value();
}
