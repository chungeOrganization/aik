package com.aik.service.account;

import com.aik.exception.ApiServiceException;
import com.aik.model.AikNutritionalIndex;

import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/9.
 */
public interface NutritionalIndexService {

    /**
     * 获取用户营养指标
     *
     * @param userId 用户id
     * @return 用户营养指标
     * @throws ApiServiceException Api服务异常
     */
    AikNutritionalIndex getUserNutritionalIndex(Integer userId) throws ApiServiceException;

    /**
     * 修改用户营养指标
     * @param nutritionalIndex 用户营养指标
     * @throws ApiServiceException Api服务异常
     */
    void updateUserNutritionalIndex(AikNutritionalIndex nutritionalIndex) throws ApiServiceException;
}
