package cn.rwj.fakeprj.entity.dto;

import cn.rwj.fakeprj.entity.bo.HeadLine;
import cn.rwj.fakeprj.entity.bo.ShopCategory;
import lombok.Data;

import java.util.List;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 首页
 */
@Data
public class MainPageInfoDTO {

    /**
     * 头条列表
     */
    private List<HeadLine> headLineList;

    /**
     * 首页
     */
    private List<ShopCategory> shopCategoryList;
}
