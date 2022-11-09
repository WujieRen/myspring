package org.myspring.mvc.render.impl;

import com.google.gson.Gson;
import org.myspring.mvc.RequestProcessorChain;
import org.myspring.mvc.render.ResultRender;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description Json渲染器
 */
public class JsonResultRender implements ResultRender {

    private static final String JSON_CONTENT = "application/json";
    private static final String CHARSET_UTF8 = "UTF-8";

    private Object jsonData;
    public JsonResultRender(Object jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws IOException {
        // 设置响应头
        requestProcessorChain.getResponse().setContentType(JSON_CONTENT);
        requestProcessorChain.getResponse().setCharacterEncoding(CHARSET_UTF8);
        // 响应流写入经过gson格式化之后的处理结果
        try(PrintWriter writer = requestProcessorChain.getResponse().getWriter()) {
            Gson gson = new Gson();
            writer.write(gson.toJson(jsonData));
            writer.flush();
        }
    }
}
