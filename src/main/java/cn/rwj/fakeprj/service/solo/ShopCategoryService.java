package cn.rwj.fakeprj.service.solo;

import cn.rwj.fakeprj.entity.bo.ShopCategory;
import cn.rwj.fakeprj.entity.dto.Result;

import java.util.List;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 店铺类别
 */
public interface ShopCategoryService {

    Result<Boolean> addShopCategory(ShopCategory shopCategory);
    Result<Boolean> removeShopCategory(int shopCategoryId);
    Result<Boolean> modifyShopCategory(ShopCategory shopCategory);
    Result<ShopCategory> queryShopCategoryById(int shopCategoryId);
    Result<List<ShopCategory>> queryShopCategory(ShopCategory shopCategoryCondition, int pageIndex, int pageSize);

}
