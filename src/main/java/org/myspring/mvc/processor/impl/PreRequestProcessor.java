package org.myspring.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.myspring.mvc.RequestProcessorChain;
import org.myspring.mvc.processor.RequestProcessor;

import java.io.UnsupportedEncodingException;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description 前置请求处理器，请求的预处理
 *  1.请求编码同意设为 UTF-8
 *  2.将请求路径末尾 / 删除为后续匹配Controller做准备
 *
 */
@Slf4j
public class PreRequestProcessor implements RequestProcessor {

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws UnsupportedEncodingException {
        //1.请求编码同意设为 UTF-8
        requestProcessorChain.getRequest().setCharacterEncoding("UTF-8");
        //2.将请求路径末尾 / 删除为后续匹配Controller做准备
        String requestPath = requestProcessorChain.getRequestPath();
        if(requestPath.length() > 1 && requestPath.endsWith("/")) {
            requestProcessorChain.setRequestPath(requestPath.substring(0, requestPath.length() - 1));
        }
        log.info("preprocess request {} {}", requestProcessorChain.getRequestMethod(), requestProcessorChain.getRequestPath());
        return true;
    }

}
