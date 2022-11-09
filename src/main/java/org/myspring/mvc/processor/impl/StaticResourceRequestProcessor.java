package org.myspring.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.myspring.mvc.RequestProcessorChain;
import org.myspring.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description 静态资源请求处理,包括但不限于图片、css、以及js文件等 - 将请求交给 DefaultServlet 处理
 *
 *  1.通过请求路径判断是否是请求的静态资源 webapp/static
 *  2.如果是静态资源，则将请求转发给default servlet处理
 */
@Slf4j
public class StaticResourceRequestProcessor implements RequestProcessor {

    public static final String DEFAULT_TOMCAT_SERVLET = "default";

    public static final String STATIC_RESOURCE_PREFIX = "/static/";
    private RequestDispatcher defaultDispatcher;

    public StaticResourceRequestProcessor(ServletContext servletContext) {
        this.defaultDispatcher = servletContext.getNamedDispatcher(DEFAULT_TOMCAT_SERVLET);
        if(this.defaultDispatcher == null){
            throw new RuntimeException("There is no default tomcat servlet");
        }
        log.info("The default servlet for static resource is {}", DEFAULT_TOMCAT_SERVLET);
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws ServletException, IOException {
        if(isStaticResource(requestProcessorChain.getRequestPath())) {
            defaultDispatcher.forward(requestProcessorChain.getRequest(), requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 是否请求的是静态资源 /webapp/static/ 下的资源
     */
    private boolean isStaticResource(String path){
        return path.startsWith(STATIC_RESOURCE_PREFIX);
    }
}
