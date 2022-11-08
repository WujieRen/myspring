package org.myspring.mvc.processor.impl;

import org.myspring.mvc.RequestProcessorChain;
import org.myspring.mvc.processor.RequestProcessor;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description 静态资源请求处理,包括但不限于图片、css、以及js文件等 - 将请求交给 DefaultServlet 处理
 *
 *  1.通过请求路径判断是否是请求的静态资源 webapp/static
 *  2.如果是静态资源，则将请求转发给default servlet处理
 */
public class StaticResourceRequestProcessor implements RequestProcessor {
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) {
        return false;
    }
}
