package cn.rwj.fakeprj.controller;

import cn.rwj.fakeprj.controller.frontend.MainPageController;
import cn.rwj.fakeprj.controller.superadmin.ShopCategoryOperationController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description
 */
@WebServlet("/")
public class DipatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("请求路径：" + req.getServletPath());
        System.out.println("请求方式：" + req.getMethod());
        //由此可以依据请求路径和请求方式，来决定访问的是哪个Controller中的哪个方法。
        //因此，通过一个Servlet对众多Controller按照不同URL路径以及方法请求进行请求派发的思路是可行的
        if(Objects.equals(req.getServletPath(), "/frontend/getmainpageinfo") && Objects.equals(req.getMethod(), "GET")) {
            new MainPageController().getMainPageInfo(req, resp);
        } else if(Objects.equals(req.getServletPath(), "/superadmin/addheadline") && Objects.equals(req.getMethod(), "POST")) {
            new ShopCategoryOperationController().addShopCategory(req, resp);
        }
    }

}