package org.myspring.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author rwj
 * @Date 2022/11/10
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerMethod {

    /**
     * 被 @Controller 标记的类
     */
    private Class<?> controllerClass;

    /**
     * 该类下的某个方法
     */
    private Method invokeMethod;

    /**
     * 该方法的<参数， 参数类型>
     */
    private Map<String, Class<?>> methodParameterMap;

}
