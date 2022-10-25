package cn.rwj.fakeprj;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author rwj
 * @Date 2022/10/25
 * @Description
 */
@Slf4j
@WebServlet("/hello")   //自servlet3.0以后可以通过注解指明servlet服务而不用在web.xml中去做配置
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = "我的简易框架";
        log.debug("我的名字是：" + name);
        req.setAttribute("name", name);
        req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req, resp);
    }
}
