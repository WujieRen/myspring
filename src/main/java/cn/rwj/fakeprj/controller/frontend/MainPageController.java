package cn.rwj.fakeprj.controller.frontend;

import cn.rwj.fakeprj.entity.dto.MainPageInfoDTO;
import cn.rwj.fakeprj.entity.dto.Result;
import cn.rwj.fakeprj.service.combine.HeadLineShopCategoryCombineService;
import lombok.Getter;
import org.myspring.core.annotation.Controller;
import org.myspring.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description
 */
@Getter
@Controller
public class MainPageController {

    @Autowired
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

//    验证该接口没有实现类被注入容器，会报错：No implemented interface or parent class interface cn.rwj.fakeprj.service.TestNoImplementClassServicehas found.
//    @Autowired
//    private TestNoImplementClassService testNoImplementClassService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return headLineShopCategoryCombineService.getMainPageInfo();
    }

}
