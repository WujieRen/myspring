package cn.rwj.fakeprj.controller.superadmin;

import cn.rwj.fakeprj.entity.bo.HeadLine;
import cn.rwj.fakeprj.entity.dto.Result;
import cn.rwj.fakeprj.service.solo.HeadLineService;
import org.myspring.core.annotation.Controller;
import org.myspring.inject.annotation.Autowired;
import org.myspring.mvc.annotation.RequestMapping;
import org.myspring.mvc.annotation.RequestParam;
import org.myspring.mvc.type.ModelAndView;
import org.myspring.mvc.type.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description
 */
@Controller
@RequestMapping(value = "/headline")
public class HeadLineOperationController {

    @Autowired
    private HeadLineService headLineService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addHeadLine(@RequestParam("lineName") String lineName,
                                    @RequestParam("lineLink") String lineLink,
                                    @RequestParam("lineImg") String lineImg,
                                    @RequestParam("priority") Integer priority) {
        System.out.println("HeadLineOperationController.addHeadLine");

        HeadLine headLine = new HeadLine();
        headLine.setLineName(lineName);
        headLine.setLineLink(lineLink);
        headLine.setLineImg(lineImg);
        headLine.setPriority(priority);
        Result<Boolean> result = headLineService.addHeadLine(headLine);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("headline.jsp").addViewData("result", result);
        return modelAndView;
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
