package com.aik.service.question;

import com.aik.exception.ApiServiceException;
import com.aik.request.PageRequest;
import com.aik.response.FreeQODetailResponse;
import com.aik.response.FreeQOListDetailResponse;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/9.
 */
public interface FreeQuestionOrderService {

    /**
     * 获取免费问题订单列表
     *
     * @param request 请求
     * @return 免费问题订单列表
     * @throws ApiServiceException Api服务异常
     */
    List<FreeQOListDetailResponse> getFreeQuestionOrders(PageRequest request) throws ApiServiceException;

    /**
     * 获取免费订单详情
     *
     * @param freeOrderId 免费订单id
     * @param userId      用户id
     * @return 订单详情
     * @throws ApiServiceException Api服务异常
     */
    FreeQODetailResponse getFreeQuestionOrderDetail(Integer freeOrderId, Integer userId) throws ApiServiceException;
}
