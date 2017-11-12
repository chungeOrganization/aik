package com.aik.service.question;

import com.aik.dto.AppendAskDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikQuestion;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
public interface QuestionService {

    /**
     * 获取原始问题
     *
     * @param questionOrderId 问题订单id
     * @return 原始问题
     * @throws ApiServiceException Api服务异常
     */
    AikQuestion getOriginalQuestion(Integer questionOrderId) throws ApiServiceException;

    /**
     * 获取订单最近提问
     * 匹配医生：获取最新提问
     * 公开订单：最新关联医生追问或原始问题
     * @param orderId
     * @param doctorId
     * @return
     * @throws ApiServiceException
     */
    AikQuestion getOrderLastQuestion(Integer orderId, Integer doctorId) throws ApiServiceException;

    /**
     * 获取首页公开问题
     *
     * @param doctorId 医生id
     * @return 首页公开问题
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getHomeOpenQuestion(Integer doctorId) throws ApiServiceException;

    /**
     * 根据医生回答追问
     *
     * @param appendAskDTO 医生追问
     * @throws ApiServiceException Api服务异常
     */
    void appendAskFromAnswer(AppendAskDTO appendAskDTO) throws ApiServiceException;
}
