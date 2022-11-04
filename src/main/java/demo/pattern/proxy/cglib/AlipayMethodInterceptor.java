package demo.pattern.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author rwj
 * @Date 2022/11/5
 * @Description cglib实现动态代理
 * <p>
 * Code Generation Library —— 代码生成库
 * 1. 不要求被代理类实现接口
 * 2. 内部主要封装了ASM Java字节码操控框架
 * 3. 动态生成子类以覆盖非final方法，绑定钩子回调自定义拦截器
 * <p>
 * 这个可以类比jdk动态代理的 InvovationHandler，即对横切逻辑的管理。
 * <p>
 * 真正生成代理对象（被代理对象子类）需要Enhancer.create()
 */
public class AlipayMethodInterceptor implements MethodInterceptor {

    /**
     * @param o           "this", the enhanced object
     *                    动态代理对象
     * @param method      intercepted Method
     *                    被代理的方法实例
     * @param args        argument array; primitive types are wrapped
     *                    被代理方法需要的参数数组
     * @param methodProxy used to invoke super (non-intercepted method); may be called
     *                    cglib生成的一个用来代替Method的动态代理Method对象
     *                    as many times as needed
     * @return 方法执行后的返回值
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        beforePay();
        Object object = methodProxy.invokeSuper(o, args);
        afterPay();
        return object;
    }

    private void beforePay() {
        System.out.println("取款-----");
    }

    private void afterPay() {
        System.out.println("支付-----");
    }
}
