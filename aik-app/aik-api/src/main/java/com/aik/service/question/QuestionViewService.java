package com.aik.service.question;

import com.aik.exception.ApiServiceException;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/28.
 */
public interface QuestionViewService {

    /**
     * 获取用户问题查看总数
     *
     * @param userId 用户id
     * @return 问题查看总数
     * @throws ApiServiceException Api服务异常
     */
    Integer getUserQuestionViewCount(Integer userId) throws ApiServiceException;

    /**
     * 获取用户查看历史
     *
     * @param params 参数
     * @return 查看历史
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getQuestionViewHis(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取订单查看次数
     * @param orderId
     * @return
     * @throws ApiServiceException
     */
    Integer getQuestionViewCount(Integer orderId) throws ApiServiceException;
}
