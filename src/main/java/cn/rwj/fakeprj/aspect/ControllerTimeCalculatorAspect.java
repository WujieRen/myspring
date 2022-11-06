package cn.rwj.fakeprj.aspect;

import lombok.extern.slf4j.Slf4j;
import org.myspring.aop.native_.annotation.Order;
import org.myspring.aop.native_.aspect.DefaultAspect;
import org.myspring.aop.aspectj.annotation.AspectJ;

import java.lang.reflect.Method;

/**
 * @Author rwj
 * @Date 2022/11/6
 * @Description
 */
@Slf4j
//@Aspect(Controller.class)
@AspectJ(pointcut = "execution(* cn.rwj.fakeprj.controller.frontend..*.*(..))")
@Order(1)
public class ControllerTimeCalculatorAspect extends DefaultAspect {
    private long timestampCache;
    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("开始计时，执行的类是[{}],执行的方法是[{}]，参数是[{}]",
                targetClass.getName(),method.getName(),args
        );
        timestampCache = System.currentTimeMillis();
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        long endTime = System.currentTimeMillis();
        long costTime = endTime - timestampCache;
        log.info("结束计时，执行的类是[{}], 执行的方法是[{}]，参数是[{}]，返回值是[{}]时间为[{}]ms",
                targetClass.getName(),method.getName(),args, returnValue, costTime);
        return returnValue;
    }
}
