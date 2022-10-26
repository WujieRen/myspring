package cn.rwj.fakeprj.service.combine;

import cn.rwj.fakeprj.entity.dto.MainPageInfoDTO;
import cn.rwj.fakeprj.entity.dto.Result;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 组合两个solo Service，处理获取首页需要数据的数据的逻辑
 */
public interface HeadLineShopCategoryCombineService {
    Result<MainPageInfoDTO> getMainPageInfo();
}
