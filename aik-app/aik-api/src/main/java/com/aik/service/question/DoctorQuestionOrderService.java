package com.aik.service.question;

import com.aik.dto.response.doctor.QuestionOrderDetailRespDTO;
import com.aik.exception.ApiServiceException;

import java.util.List;
import java.util.Map;

/**
 * Description: 提问订单管理
 * Created by as on 2017/8/20.
 */
public interface DoctorQuestionOrderService {

    /**
     * 医生已接诊患者数量
     *
     * @param doctorId 医生id
     * @return 已接诊患者数量
     * @throws ApiServiceException Api服务异常
     */
    Integer getDoctorDiagnosedOrderCount(Integer doctorId) throws ApiServiceException;

    /**
     * 医生待处理患者数量
     *
     * @param doctorId 医生id
     * @return 待处理患者数量
     * @throws ApiServiceException Api服务异常
     */
    Integer getInHandOrderCount(Integer doctorId) throws ApiServiceException;

    /**
     * 获取医生已诊订单
     *
     * @param params 参数
     * @return 医生已诊订单
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getDiagnosedOrders(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取医生已诊订单
     *
     * @param params 参数
     * @return 医生已诊订单
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getInHandleOrders(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取医生订单
     *
     * @param params 参数
     * @return 医生订单
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getMyOrders(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取公开问题列表
     *
     * @param params 参数
     * @return 公开问题列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getOpenQuestionOrders(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取公开问题详情
     *
     * @param orderId 订单id
     * @return 公开问题详情
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getOpenQuestionOrderDetail(Integer orderId) throws ApiServiceException;

    /**
     * 获取订单详情
     * 患者管理-订单详情
     *
     * @param orderId 订单id
     * @param doctorId 医生id
     * @return response DTO
     * @throws ApiServiceException
     */
    QuestionOrderDetailRespDTO getQuestionOrderDetail(Integer orderId, Integer doctorId) throws ApiServiceException;
}
