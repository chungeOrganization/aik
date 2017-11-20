package com.aik.service.question;

import com.aik.exception.ApiServiceException;

/**
 * Description:
 * Created by as on 2017/11/20.
 */
public interface QuestionOrderAssistService {

    /**
     * 添加订单辅助信息
     *
     * @param orderId 订单id
     * @param content 内容
     */
    void addQuestionOrderAssist(Integer orderId, String content) throws ApiServiceException;
}
