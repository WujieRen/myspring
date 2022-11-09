package org.myspring.mvc.render;

import org.myspring.mvc.RequestProcessorChain;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description 结果渲染器
 */
public interface ResultRender {
    void render(RequestProcessorChain requestProcessorChain) throws Exception;
}
