package cn.rwj.fakeprj.controller.frontend;

import cn.rwj.fakeprj.entity.dto.MainPageInfoDTO;
import cn.rwj.fakeprj.entity.dto.Result;
import cn.rwj.fakeprj.service.combine.HeadLineShopCategoryCombineService;
import lombok.Getter;
import org.myspring.core.annotation.Controller;
import org.myspring.inject.annotation.Autowired;
import org.myspring.mvc.annotation.RequestMapping;
import org.myspring.mvc.type.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description
 */
@Getter
@Controller
@RequestMapping(value = "main")
public class MainPageController {

    @Autowired
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

//    验证该接口没有实现类被注入容器，会报错：No implemented interface or parent class interface cn.rwj.fakeprj.service.TestNoImplementClassServicehas found.
//    @Autowired
//    private TestNoImplementClassService testNoImplementClassService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("MainPageController.getMainPageInfo");
        return headLineShopCategoryCombineService.getMainPageInfo();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void throwException(){
        throw new RuntimeException("抛出异常测试");
    }

}
