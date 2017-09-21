package com.aik.service.store;

import com.aik.exception.ApiServiceException;

import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
public interface DoctorDealService {

    /**
     * 获取医生销售订单提成流水总数
     *
     * @param doctorId 医生用户id
     * @return 医生销售订单提成流水总数
     * @throws ApiServiceException Api service exception
     */
    Integer getSellDealCount(Integer doctorId) throws ApiServiceException;

    /**
     * 获取医生销售订单详情
     *
     * @param params 参数
     * @return 销售订单详情
     * @throws ApiServiceException Api service exception
     */
    Map<String, Object> getSellDealDetails(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取医生流水详情
     *
     * @param params 参数
     * @return 医生流水详情
     * @throws ApiServiceException Api service exception
     */
    Map<String, Object> getDealDetails(Map<String, Object> params) throws ApiServiceException;
}
