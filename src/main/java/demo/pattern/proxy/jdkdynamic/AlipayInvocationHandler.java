package demo.pattern.proxy.jdkdynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description InvocationHandler可以类比为统一管理横切逻辑的Aspect，真正的代理类/代理对象是由 java.lang.Proxy 来生成
 */
public class AlipayInvocationHandler implements InvocationHandler {

    private Object targetObject;

    public AlipayInvocationHandler(Object targetObject) {
        this.targetObject = targetObject;
    }

    /**
     *
     * @param proxy 代理对象。即Proxy.newInstance()生成的对象
     *
     * @param method 被代理对象的方法
     *
     * @param args 参数
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        // 被代理的类实例、参数
        Object object = method.invoke(targetObject, args);
        after();
        return object;
    }

    private void before() {
        System.out.println("取款-----");
    }

    private void after() {
        System.out.println("支付-----");
    }

}
