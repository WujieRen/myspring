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
 * @Description Jsp请求处理器，如果是jsp请求，将请求转发给 JspServlet 处理
 */
@Slf4j
public class JspRequestProcessor implements RequestProcessor {

    //jsp请求的RequestDispatcher的名称
    private static final String JSP_SERVLET = "jsp";
    //Jsp请求资源路径前缀
    private static final String JSP_RESOURCE_PREFIX = "/templates/";

    /**
     * jsp的RequestDispatcher,处理jsp资源
     */
    private RequestDispatcher jspServlet;

    public JspRequestProcessor(ServletContext servletContext) {
        this.jspServlet = servletContext.getNamedDispatcher(JSP_SERVLET);
        if (null == jspServlet) {
            throw new RuntimeException("there is no jsp servlet");
        }
        log.info("The default servlet for jsp resource is {}", JSP_RESOURCE_PREFIX);
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws ServletException, IOException {
        if (isJspResource(requestProcessorChain.getRequestPath())) {
            jspServlet.forward(requestProcessorChain.getRequest(), requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 是否请求的是jsp资源 /webapp/templates/ 下的资源
     */
    private boolean isJspResource(String url) {
        return url.startsWith(JSP_RESOURCE_PREFIX);
    }
}
