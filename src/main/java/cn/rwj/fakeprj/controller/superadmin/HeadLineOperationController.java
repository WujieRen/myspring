package cn.rwj.fakeprj.controller.superadmin;

import cn.rwj.fakeprj.entity.bo.HeadLine;
import cn.rwj.fakeprj.entity.dto.Result;
import cn.rwj.fakeprj.service.solo.HeadLineService;
import org.myspring.core.annotation.Controller;
import org.myspring.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description
 */
@Controller
public class HeadLineOperationController {

    @Autowired
    private HeadLineService headLineService;

    public Result<Boolean> addHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("HeadLineOperationController.addHeadLine");
        //TODO:参数校验以及请求参数转化
        return headLineService.addHeadLine(new HeadLine());
    }

    public Result<Boolean> removeHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        //TODO:参数校验以及请求参数转化
        return headLineService.removeHeadLine(1);
    }

    public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        //TODO:参数校验以及请求参数转化
        return headLineService.modifyHeadLine(new HeadLine());
    }

    public Result<HeadLine> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp) {
        //TODO:参数校验以及请求参数转化
        return headLineService.queryHeadLineById(1);
    }

    public Result<List<HeadLine>> queryHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        //TODO:参数校验以及请求参数转化
        return headLineService.queryHeadLine(null, 1, 100);
    }

}
