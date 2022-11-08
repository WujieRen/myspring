package org.myspring.mvc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 所有访问请求的入口，统一由 DispatcherServlet 交由 RequestProcessorChain 来处理。
 *  1.初始化执行一次
 *      1.初始化容器，
 *      2.初始化请求处理责任链
 *  2.每次调用需要都执行
 *      1.创建请求处理责任链对象实例
 *      2.通过责任链模式，依次调用请求处理器处理请求
 *      3.渲染请求结果
 */
@WebServlet("/*")
public class DipatcherServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}