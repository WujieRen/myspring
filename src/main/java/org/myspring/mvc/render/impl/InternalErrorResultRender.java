package org.myspring.mvc.render.impl;

import org.myspring.mvc.RequestProcessorChain;
import org.myspring.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description 内部异常/错误渲染器
 */
public class InternalErrorResultRender implements ResultRender {

    private String errorMsg;

    public InternalErrorResultRender(String errMsg) {
        this.errorMsg = errMsg;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws IOException {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMsg);
    }
}
