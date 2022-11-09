package org.myspring.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.myspring.core.BeanContainer;
import org.myspring.mvc.RequestProcessorChain;
import org.myspring.mvc.annotation.RequestBody;
import org.myspring.mvc.annotation.RequestMapping;
import org.myspring.mvc.annotation.RequestParam;
import org.myspring.mvc.processor.RequestProcessor;
import org.myspring.mvc.render.impl.JsonResultRender;
import org.myspring.mvc.render.impl.ResourceNotFoundResultRender;
import org.myspring.mvc.render.impl.ViewResultRender;
import org.myspring.mvc.type.ControllerMethod;
import org.myspring.mvc.type.RequestPathInfo;
import org.myspring.util.ConverterUtil;
import org.myspring.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description Controller请求处理器
 *
 *  1.初始化：依靠容器的能力，建立起请求路径、请求方法与Controller方法实例的映射
 *      1.获取容器实例
 *      2.获取容器中所有由 @RequestMapping 标记的 Controller
 *      3.构建 请求路径、请求方法与 Controller 方法实例的映射
 *          1.遍历所有被 @RequestMapping 标记的 Class
 *              1.获取 Class 的 @RequestMapping 注解、 @RequestMapping 注解的 value(一级路径)
 *              2.获取该 Class 对应的所有方法对象数组 Methods[]，遍历所有被 @RequestMapping 标记的方法
 *                  1.获取方法对应的 @RequestMapping 注解，获取该注解对应的 value（二级路径）、method
 *                  2.解析方法被 @RequestParam 标记的参数，遍历
 *                      1.获取参数数组 Object[]，遍历
 *                      2.获取参数对应的参数名称和类型，构造Map<String(参数名称), Class<?> argType>
 *                      （这里只需要知道名称和类型就可以了，通过这两者在 process() 中
 *                      对 HttpServletRequest 传进来的参数根据参数名称获取传进来的值，并根据类型转换为相应的值）
 *                      3.构造Map<（httpRequestPath, httpRequestMethod）, （Class<?> targetClass, Method<?> targetMethod, Map<String(参数名称), Class<?> argType>）>
 *
 *  2.处理
 *      1.解析HttpServletRequest的请求方法,请求路径，获取对应的 ControllerMethod 实例
 *      2.解析请求参数，并传递给获取到的ControllerMethod实例去执行
 *          1.根据 HttpServletRequst ，构造出参数名和参数值的映射 Map<String, String>
 *          2.从已经构造好的参数名称和参数类型映射 Map 中获取对应的类型，并转换上一步获取到的值，构造参数 List
 *          3.执行方法
 *      3.根据处理的结果，选择设置对应的 render
 *
 */
@Slf4j
public class ControllerRequestProcessor implements RequestProcessor {

    private BeanContainer beanContainer;
    private Map<RequestPathInfo, ControllerMethod> pathControllerMethodMap = new ConcurrentHashMap<>();


    public ControllerRequestProcessor() {
        this.beanContainer = BeanContainer.getInstance();
        Set<Class<?>> requestMappingSet = beanContainer.getClassesByAnnotation(RequestMapping.class);
        initPathControllerMethodMap(requestMappingSet);
    }

    private void initPathControllerMethodMap(Set<Class<?>> requestMappingSet) {
        if(ValidationUtil.isEmpty(requestMappingSet)) {
            log.warn("There's no any Class marked with @RequestMapping");
            return;
        }
        //1.遍历所有被@RequestMapping标记的类，获取类上面该注解的属性值作为一级路径
        for (Class<?> requestMappingClass : requestMappingSet) {
            RequestMapping requestMapping = requestMappingClass.getAnnotation(RequestMapping.class);
            StringBuilder url = new StringBuilder(requestMapping.value());
            if(!url.toString().startsWith("/")) {
                url.insert(0, "/");
            }

            //2. 遍历Method
            Method[] declaredMethods = requestMappingClass.getDeclaredMethods();
            if(ValidationUtil.isEmpty(declaredMethods)) {
                continue;
            }
            for (Method declaredMethod : declaredMethods) {
                if(!declaredMethod.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }
                requestMapping = declaredMethod.getAnnotation(RequestMapping.class);
                String methodPath = requestMapping.value();
                if(!methodPath.startsWith("/")) {
                    url.append("/");
                }
                url.append(methodPath);

                //3.遍历参数
                Map<String, Class<?>> methodParams = new HashMap<>();
                Parameter[] parameters = declaredMethod.getParameters();
                if(!ValidationUtil.isEmpty(parameters)) {
                    for(Parameter param : parameters) {
                        RequestParam requestParam = param.getAnnotation(RequestParam.class);
                        if(requestParam == null) {
                            throw new RuntimeException("The parameter must have @RequestParam：" + param.getName());
                        }
                        String paramName = requestParam.value();    // 现在要求参数必须强制指定 @RequestParam 的 value
                        if(Objects.equals("", paramName)) {
                            throw new RuntimeException("The parameter's @RequestParam must have a value");
                        }
                        // 获取参数名称和类型，构造Map<String(参数名称), Class<?> argType>
                        methodParams.put(paramName, param.getType());
                    }
                }

                //4.将获取到的信息封装成RequestPathInfo实例和ControllerMethod实例，放置到映射表里
                RequestPathInfo requestPathInfo = new RequestPathInfo(url.toString(), requestMapping.method().name());
                if (this.pathControllerMethodMap.containsKey(requestPathInfo)) {
                    log.warn("duplicate url:{} registration，current class {} method{} will override the former one",
                            requestPathInfo.getHttpPath(), requestMappingClass.getName(), declaredMethod.getName());
                }
                ControllerMethod controllerMethod = new ControllerMethod(requestMappingClass, declaredMethod, methodParams);
                this.pathControllerMethodMap.put(requestPathInfo, controllerMethod);
            }
        }
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) {
        //1.解析HttpServletRequest的请求方法,请求路径，获取对应的 ControllerMethod 实例
        String requestPath = requestProcessorChain.getRequestPath();
        String requestMethod = requestProcessorChain.getRequestMethod();
        RequestPathInfo requestPathInfo = new RequestPathInfo(requestPath, requestMethod);
        ControllerMethod controllerMethod = this.pathControllerMethodMap.get(requestPathInfo);
        //2.解析请求参数，并传递给获取到的ControllerMethod实例去执行
        if(controllerMethod == null) {
            requestProcessorChain.setResultRender(new ResourceNotFoundResultRender(requestPath, requestMethod));
            return false;
        }
        Object result = invokeControllerMethod(controllerMethod, requestProcessorChain.getRequest());
        //3.根据处理的结果，选择对应的render进行渲染
        setResultRender(result, requestProcessorChain, controllerMethod);
        return false;
    }

    private void setResultRender(Object result, RequestProcessorChain requestProcessorChain, ControllerMethod controllerMethod) {
        if (result == null) {
            return;
        }

        if(controllerMethod.getInvokeMethod().isAnnotationPresent(RequestBody.class)) { // 需要Json处理
            requestProcessorChain.setResultRender(new JsonResultRender(result));
        } else {
            requestProcessorChain.setResultRender(new ViewResultRender(result));
        }
    }


    private Object invokeControllerMethod(ControllerMethod controllerMethod, HttpServletRequest request) {
        //1.根据 HttpServletRequst ，构造出参数名和参数值的映射 Map<String, String>
        Map<String, String> requestParamMap = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k, v) -> { //暂时只支持一个参数名对应一个参数
            if (!ValidationUtil.isEmpty(v)) {
                requestParamMap.put(k, v[0]);
            }
        });
        //2.从已经构造好的参数名称和参数类型映射 Map 中获取对应的值，并转换上一步获取到的值，构造参数 List
        List<Object> methodParams = new ArrayList<>();
        /** 这里一个注意点，要遍历我们构造的参数而不是前端传过来的，因为前段传过来的很可能不全 */
        Map<String, Class<?>> methodParameterMap = controllerMethod.getMethodParameterMap();
        methodParameterMap.forEach((paramName, paramType) -> {
            String requestParamValue = requestParamMap.get(paramName);
            Object paramValue;
            //只支持String 以及基础类型char,int,short,byte,double,long,float,boolean,及它们的包装类型
            if (requestParamValue == null) {
                //将请求里的参数值转成适配于参数类型的空值
                paramValue = ConverterUtil.primitiveNull(paramType);
            } else {
                paramValue = ConverterUtil.convert(paramType, requestParamValue);
            }
            methodParams.add(paramValue);
        });
        //3.执行方法
        Object controller = beanContainer.getBean(controllerMethod.getControllerClass());
        Method invokeMethod = controllerMethod.getInvokeMethod();
        invokeMethod.setAccessible(true);   /** 这一步也是要注意，方法执行时一定要设置为 Accessible */
        Object result;
        try {
            if(methodParams.size() == 0) {
                result = invokeMethod.invoke(controller);
            } else {
                result = invokeMethod.invoke(controller, methodParams);
            }
        } catch (IllegalAccessException e) {
            //参数异常
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            //如果是调用异常的话，需要通过e.getTargetException()
            // 去获取执行方法抛出的异常
            throw new RuntimeException(e.getTargetException());
        }
        return result;
    }
}
