package org.myspring.mvc.processor.impl;

import org.myspring.mvc.RequestProcessorChain;
import org.myspring.mvc.processor.RequestProcessor;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description Controller请求处理器
 *
 *  1.初始化：依靠容器的能力，建立起请求路径、请求方法与Controller方法实例的映射
 *      1.获取容器实例
 *      2.获取容器中所有由 @RequestMapping 标记的 Controller
 *      3.构建 请求路径、请求方法与 Controller 方法实例的映射
 *          1.
 *  2.处理
 *      1.解析HttpServletRequest的请求方法,请求路径，获取对应的 ControllerMethod 实例
 *      2.解析请求参数，并传递给获取到的ControllerMethod实例去执行
 *          1.
 *      3.根据处理的结果，选择对应的render进行渲染
 *
 */
public class ControllerRequestProcessor implements RequestProcessor {
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) {
        return false;
    }
}
