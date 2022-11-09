package org.myspring.mvc.render.impl;

import org.myspring.mvc.RequestProcessorChain;
import org.myspring.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description 资源找不到时使用的渲染器
 */
public class ResourceNotFoundResultRender implements ResultRender {

    private String httpPath;
    private String httpMethod;
    public ResourceNotFoundResultRender(String path, String method) {
        this.httpPath = path;
        this.httpMethod = method;
    }
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws IOException {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND,
                "获取不到对应的请求资源：请求路径[" + httpPath + "]" + "请求方法[" + httpMethod + "]");
    }
}
