package org.myspring.mvc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.myspring.mvc.processor.RequestProcessor;
import org.myspring.mvc.render.ResultRender;
import org.myspring.mvc.render.impl.DefaultResultRender;
import org.myspring.mvc.render.impl.InternalErrorResultRender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description 请求处理器责任链，
 *
 *  1.以责任链模式处理请求
 *      1.遍历注册的请求处理器列表
 *        1.直到某个请求处理器返回 false 为止
 *      2.期间如果出现异常，则交由内部错误渲染器处理
 *  2.委派给特定的渲染（Render）实例对处理后的结果进行渲染
 *      1.如果请求处理器实现类均未选择合适的渲染器，则使用默认的
 *      2.调用渲染器的render方法对结果进行渲染
 *
 * RequestProcessorChain 串联起了 DispatcherServlet 与 ( RequestProcessor 和  ResultRender )
 *  通过构造函数接收 DispatcherServlet 以及需要初始化的各种实体信息（包括输入 与 输出），在构造函数中将他们初始化
 *  然后在通过将自身 this 以将自身初始化好的各种属性传递给 RequestProcessor(输入信息处理逻辑) 和 ResultRender（输出信息逻辑）
 *
 */
@Slf4j
@Data
public class RequestProcessorChain {

    private Iterator<RequestProcessor> iterator;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String requestPath;
    private String requestMethod;
    private  int responseCode;
    private ResultRender resultRender;

    public RequestProcessorChain(Iterator<RequestProcessor> iterator, HttpServletRequest req, HttpServletResponse resp) {
        this.iterator = iterator;
        this.request = req;
        this.response = resp;
        this.requestPath = req.getPathInfo();
        this.requestMethod = req.getMethod();
        this.responseCode =HttpServletResponse.SC_OK;
    }

    public void doRequestProcessorChain() {
        try {
            //1.遍历注册的请求处理器列表
            while (iterator.hasNext()) {
                //1.直到某个请求处理器返回 false 为止
                if(!iterator.next().process(this)) {    /** */
                    break;
                }
            }
        } catch (Exception e) {
            //2.期间如果出现异常，则交由内部错误渲染器处理
            this.resultRender = new InternalErrorResultRender(e.getMessage());
            log.error("内部异常渲染器 -- doRequestProcessorChain error:", e);
        }
    }

    public void doRender() {
        //1.如果请求处理器实现类均未选择合适的渲染器，则使用默认的
        if(this.resultRender == null){
            this.resultRender = new DefaultResultRender();
        }
        //2.调用渲染器的render方法对结果进行渲染
        try {
            this.resultRender.render(this); /**  */
        } catch (Exception e) {
            log.error("doRender error: ", e);
            throw new RuntimeException(e);
        }
    }
}
