package com.aik.service.question;

import com.aik.dto.*;
import com.aik.dto.response.user.QuestionOrderDetailRespDTO;
import com.aik.exception.ApiServiceException;
import com.aik.request.IssueQORequest;
import com.aik.request.MatchDoctorsRequest;
import com.aik.response.MatchDoctorsResponse;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/28.
 */
public interface UserQuestionOrderService {

    /**
     * 获取订单总数
     *
     * @param userId 用户id
     * @return 订单总数
     * @throws ApiServiceException Api服务异常
     */
    Integer getQuestionOrderCount(Integer userId) throws ApiServiceException;

    /**
     * 获取订单列表
     *
     * @param params 参数
     * @return 订单列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getQuestionOrderList(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取订单详情
     *
     * @param orderId 订单id
     * @return response DTO
     * @throws ApiServiceException
     */
    QuestionOrderDetailRespDTO getQuestionOrderDetail(Integer orderId) throws ApiServiceException;

    /**
     * 获取未审核通过订单详情
     *
     * @param orderId 订单id
     * @return 订单详情
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getNotPassAuditOrderDetail(Integer orderId) throws ApiServiceException;

    /**
     * 编辑未审核通过订单
     *
     * @param questionOrderDTO 编辑订单DTO
     * @throws ApiServiceException Api服务异常
     */
    void editNotPassAuditOrder(EditQuestionOrderDTO questionOrderDTO) throws ApiServiceException;

    /**
     * 获取待付款订单详情
     *
     * @param orderId 订单id
     * @return 订单详情
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getOnPayOrderDetail(Integer orderId) throws ApiServiceException;

    /**
     * 获取已付款订单详情
     *
     * @param orderId 订单id
     * @return 订单详情
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getPayOrderDetail(Integer orderId) throws ApiServiceException;

    /**
     * 获取待评价订单详情
     *
     * @param orderId 订单id
     * @return 订单详情
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getOnEvaluationOrderDetail(Integer orderId) throws ApiServiceException;

    /**
     * 申请订单退款
     *
     * @param orderRefundDTO 订单退款DTO
     * @throws ApiServiceException Api服务异常
     */
    void applicationOrderRefund(OrderRefundDTO orderRefundDTO) throws ApiServiceException;

    /**
     * 评价订单
     *
     * @param evaluateOrderDTO 评价订单DTO
     * @throws ApiServiceException Api服务异常
     */
    void evaluateOrder(EvaluateOrderDTO evaluateOrderDTO) throws ApiServiceException;

    /**
     * 获取被拒绝订单详情
     *
     * @param orderId 订单id
     * @return 订单详情
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getOnRefuseOrderDetail(Integer orderId) throws ApiServiceException;

    /**
     * 订单修改医生
     *
     * @param orderUpdateDoctorDTO 修改医生DTO
     * @throws ApiServiceException Api服务异常
     */
    void orderUpdateDoctor(OrderUpdateDoctorDTO orderUpdateDoctorDTO) throws ApiServiceException;

    /**
     * 获取公开订单回答列表
     *
     * @param orderId 订单id
     * @return 公开订单回答列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getOpenOrderAnswers(Integer orderId) throws ApiServiceException;

    /**
     * 公开订单悬赏
     *
     * @param rewardOpenOrderDTO 悬赏DTO
     * @throws ApiServiceException Api服务异常
     */
    void rewardOpenOrder(RewardOpenOrderDTO rewardOpenOrderDTO) throws ApiServiceException;

    /**
     * 获取匹配医生列表
     *
     * @param request 请求参数
     * @return 匹配医生列表
     * @throws ApiServiceException Api服务异常
     */
    MatchDoctorsResponse getMatchDoctors(MatchDoctorsRequest request) throws ApiServiceException;

    /**
     * 用户发布提问订单
     *
     * @param userId  用户id
     * @param request 请求参数
     * @throws ApiServiceException Api服务异常
     */
    void issueQuestionOrder(Integer userId, IssueQORequest request) throws ApiServiceException;
}
