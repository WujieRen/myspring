package org.myspring.mvc.processor.impl;

import org.myspring.mvc.RequestProcessorChain;
import org.myspring.mvc.processor.RequestProcessor;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description Jsp请求处理器，如果是jsp请求，将请求转发给 JspServlet 处理
 */
public class JspRequestProcessor implements RequestProcessor {
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) {
        return false;
    }
}
