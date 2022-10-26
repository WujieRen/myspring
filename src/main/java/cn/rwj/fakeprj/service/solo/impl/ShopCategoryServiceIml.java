package cn.rwj.fakeprj.service.solo.impl;

import cn.rwj.fakeprj.entity.bo.ShopCategory;
import cn.rwj.fakeprj.entity.dto.Result;
import cn.rwj.fakeprj.service.solo.ShopCategoryService;

import java.util.List;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 店铺类别
 */
public class ShopCategoryServiceIml implements ShopCategoryService {
    @Override
    public Result<Boolean> addShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<Boolean> removeShopCategory(int shopCategoryId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<ShopCategory> queryShopCategoryById(int shopCategoryId) {
        return null;
    }

    @Override
    public Result<List<ShopCategory>> queryShopCategory(ShopCategory shopCategoryCondition, int pageIndex, int pageSize) {
        return null;
    }
}
