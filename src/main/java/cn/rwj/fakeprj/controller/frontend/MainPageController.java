package cn.rwj.fakeprj.controller.frontend;

import cn.rwj.fakeprj.entity.dto.MainPageInfoDTO;
import cn.rwj.fakeprj.entity.dto.Result;
import cn.rwj.fakeprj.service.combine.HeadLineShopCategoryCombineService;
import org.myspring.core.annotation.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description
 */
@Controller
public class MainPageController {

    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return headLineShopCategoryCombineService.getMainPageInfo();
    }

}
