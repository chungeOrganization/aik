package com.aik.service.setting;

import com.aik.dto.request.FeedbackReqDTO;
import com.aik.enums.FeedbackEnum;
import com.aik.exception.ApiServiceException;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
public interface FeedbackService {

    /**
     * 添加问题反馈
     *
     * @param feedbackReq 问题反馈
     * @param userTypeEnum 用户类型
     * @throws ApiServiceException Api服务异常
     */
    void addFeedback(FeedbackReqDTO feedbackReq, FeedbackEnum.FbUserTypeEnum userTypeEnum) throws ApiServiceException;
}
