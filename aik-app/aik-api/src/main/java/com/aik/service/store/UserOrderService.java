package com.aik.service.store;

import com.aik.dto.PayStoOrderDTO;
import com.aik.dto.request.user.AppraiseOrderReqDTO;
import com.aik.dto.request.user.AtOncePurchaseGoodsReqDTO;
import com.aik.dto.response.user.OrderLogisticsInfoRespDTO;
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

    /**
     * 用户取消订单
     *
     * @param orderId 订单id
     * @param userId  用户id
     * @throws ApiServiceException
     */
    void cancelUserOrder(Integer orderId, Integer userId) throws ApiServiceException;

    /**
     * 获取订单物流信息
     *
     * @param orderId 订单id
     * @return 物流信息
     * @throws ApiServiceException
     */
    OrderLogisticsInfoRespDTO getOrderLogisticsInfo(Integer orderId) throws ApiServiceException;

    /**
     * 确认收货
     *
     * @param orderId 订单id
     * @throws ApiServiceException
     */
    void confirmReceipt(Integer orderId) throws ApiServiceException;

    /**
     * 订单退货
     *
     * @param orderId 订单id
     * @throws ApiServiceException
     */
    void returnOrder(Integer orderId) throws ApiServiceException;

    /**
     * 再来一单
     *
     * @param orderId 订单id
     * @throws ApiServiceException
     */
    void againOrder(Integer orderId) throws ApiServiceException;

    /**
     * 评价订单
     *
     * @param reqDTO DTO
     * @throws ApiServiceException
     */
    void appraiseOrder(AppraiseOrderReqDTO reqDTO) throws ApiServiceException;

    /**
     * 立即购买商品
     *
     * @param reqDTO DTO
     * @return 获取结算订单详情
     * @throws ApiServiceException
     */
    Map<String, Object> atOncePurchaseGoods(AtOncePurchaseGoodsReqDTO reqDTO) throws ApiServiceException;
}
