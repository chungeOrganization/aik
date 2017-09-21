package com.aik.service.store;

import com.aik.dto.PayStoOrderDTO;
import com.aik.exception.ApiServiceException;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/2.
 */
public interface UserOrderService {

    /**
     * 获取用户订单列表
     *
     * @param params 参数
     * @return 订单列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getUserOrderList(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取结算订单详情
     *
     * @param orderId 订单id
     * @return 结算订单详情
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getConfirmOrderDetail(Integer orderId) throws ApiServiceException;

    /**
     * 支付订单成功
     *
     * @param payStoOrderDTO 支付订单DTO
     * @throws ApiServiceException Api服务异常
     */
    void payOrderSuccess(PayStoOrderDTO payStoOrderDTO) throws ApiServiceException;
}
