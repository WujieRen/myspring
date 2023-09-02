package demo.pattern.proxy.jdkdynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description 动态代理
 *      由两部分构成
 *          1. 生成代理类的 java.lang.Proxy
 *          2. 统一管理横切逻辑的 InvocationHandler
 */
public class JdkDynamicProxyUtil {
    public static <T> T newProxyInstance(T objectProxied, InvocationHandler handler) {
        ClassLoader classLoader = objectProxied.getClass().getClassLoader();
        Class<?>[] interfaces = objectProxied.getClass().getInterfaces();
        // 代理类的类加载器、代理类实现的接口列表、该代理类的invvocatoin handler
        // 该方法返回的是最终生成的代理类实例
        Object o = Proxy.newProxyInstance(classLoader, interfaces, handler);
        Method[] declaredMethods = o.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
        }
        return (T) o;
    }
}
