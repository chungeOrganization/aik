package com.aik.service.diet;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.DietUserCustomFoodMapper;
import com.aik.exception.ApiServiceException;
import com.aik.model.DietUserCustomFood;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Desc:
 * Create by as on 2018/1/11
 */
public class UserCustomFoodServiceImpl implements UserCustomFoodService {

    @Autowired
    private DietUserCustomFoodMapper dietUserCustomFoodMapper;

    @Override
    public void add(DietUserCustomFood reqVO) {
        if (null == reqVO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Date now = new Date();
        reqVO.setCreateTime(now);
        reqVO.setUpdateTime(now);
        dietUserCustomFoodMapper.insertSelective(reqVO);
    }
}
