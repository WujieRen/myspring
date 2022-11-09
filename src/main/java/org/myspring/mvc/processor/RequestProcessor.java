package org.myspring.mvc.processor;

import org.myspring.mvc.RequestProcessorChain;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description 请求处理器
 */
public interface RequestProcessor {
    boolean process(RequestProcessorChain requestProcessorChain) throws Exception;
}
