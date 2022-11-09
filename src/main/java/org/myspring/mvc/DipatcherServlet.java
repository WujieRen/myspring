package org.myspring.mvc;

import org.myspring.aop.native_.AspectWeaver;
import org.myspring.core.BeanContainer;
import org.myspring.inject.DependencyInjector;
import org.myspring.mvc.processor.RequestProcessor;
import org.myspring.mvc.processor.impl.ControllerRequestProcessor;
import org.myspring.mvc.processor.impl.JspRequestProcessor;
import org.myspring.mvc.processor.impl.PreRequestProcessor;
import org.myspring.mvc.processor.impl.StaticResourceRequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 所有访问请求的入口，统一由 DispatcherServlet 交由 RequestProcessorChain 来处理。
 *  1.初始化执行一次
 *      1.初始化容器
 *      2.初始化请求处理责任链
 *  2.每次调用需要都执行
 *      1.创建请求处理责任链对象实例
 *      2.通过责任链模式，依次调用请求处理器处理请求
 *      3.渲染请求结果
 */
@WebServlet("/*")
public class DipatcherServlet extends HttpServlet {

    private static final List<RequestProcessor> PROCESSOR = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        //1.初始化容器
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("cn.rwj.fakeprj");
        new AspectWeaver(beanContainer).doAOP();
        new DependencyInjector().doIoc();
        //2.初始化请求处理器责任链
        PROCESSOR.add(new PreRequestProcessor());
        PROCESSOR.add(new StaticResourceRequestProcessor(getServletContext()));
        PROCESSOR.add(new JspRequestProcessor(getServletContext()));
        PROCESSOR.add(new ControllerRequestProcessor());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.创建请求处理责任链对象实例
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(PROCESSOR.iterator(), req, resp);
        //2.通过责任链模式，依次调用请求处理器处理请求
        requestProcessorChain.doRequestProcessorChain();
        //3.渲染请求结果
        requestProcessorChain.doRender();
    }

}