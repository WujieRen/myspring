package org.myspring.mvc.processor.impl;

import org.myspring.mvc.RequestProcessorChain;
import org.myspring.mvc.processor.RequestProcessor;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description 前置请求处理器，请求的预处理
 *  1.请求编码同意设为 UTF-8
 *  2.将请求路径末尾 / 删除为后续匹配Controller做准备
 *
 */
public class PreRequestProcessor implements RequestProcessor {
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) {
        return false;
    }
}
