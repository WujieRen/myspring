package cn.rwj.fakeprj.service.combine.impl;

import cn.rwj.fakeprj.entity.bo.HeadLine;
import cn.rwj.fakeprj.entity.bo.ShopCategory;
import cn.rwj.fakeprj.entity.dto.MainPageInfoDTO;
import cn.rwj.fakeprj.entity.dto.Result;
import cn.rwj.fakeprj.service.combine.HeadLineShopCategoryCombineService;
import cn.rwj.fakeprj.service.solo.HeadLineService;
import cn.rwj.fakeprj.service.solo.ShopCategoryService;
import org.myspring.core.annotation.Service;
import org.myspring.inject.annotation.Autowired;

import java.util.List;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 组合两个solo Service，处理获取首页需要数据的数据的逻辑
 */
@Service
public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {

    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    @Override
    public Result<MainPageInfoDTO> getMainPageInfo() {
        //1.获取头条列表
        HeadLine headLineCondition = new HeadLine();
        headLineCondition.setEnableStatus(1);
        Result<List<HeadLine>> HeadLineResult = headLineService.queryHeadLine(headLineCondition, 1, 4);
        //2.获取店铺类别列表
        ShopCategory shopCategoryCondition = new ShopCategory();
        Result<List<ShopCategory>> shopCategoryResult =shopCategoryService.queryShopCategory(shopCategoryCondition, 1, 100);
        //3.合并两者并返回
        Result<MainPageInfoDTO> result = mergeMainPageInfoResult(HeadLineResult, shopCategoryResult);
        return result;
    }

    private Result<MainPageInfoDTO> mergeMainPageInfoResult(Result<List<HeadLine>> headLineResult, Result<List<ShopCategory>> shopCategoryResult) {
        return null;
    }
}
