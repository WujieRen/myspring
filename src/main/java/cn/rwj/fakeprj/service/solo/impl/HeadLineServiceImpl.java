package cn.rwj.fakeprj.service.solo.impl;

import cn.rwj.fakeprj.entity.bo.HeadLine;
import cn.rwj.fakeprj.entity.dto.Result;
import cn.rwj.fakeprj.service.solo.HeadLineService;
import org.myspring.core.annotation.Service;

import java.util.List;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 头条
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Override
    public Result<Boolean> addHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<Boolean> removeHeadLine(int headLineId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<HeadLine> queryHeadLineById(int headLineId) {
        return null;
    }

    @Override
    public Result<List<HeadLine>> queryHeadLine(HeadLine headLineCondition, int pageIndex, int pageSize) {
        return null;
    }
}
