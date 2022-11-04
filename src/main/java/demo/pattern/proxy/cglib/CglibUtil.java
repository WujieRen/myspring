package demo.pattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description
 */
public class CglibUtil {
    public static <T> T createProxy(T targetObject, MethodInterceptor interceptor) {
        return (T) Enhancer.create(targetObject.getClass(), interceptor);
    }
}