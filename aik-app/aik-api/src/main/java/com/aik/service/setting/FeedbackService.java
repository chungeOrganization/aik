package com.aik.service.setting;

import com.aik.exception.ApiServiceException;
import com.aik.model.AikDoctorFeedback;
import com.aik.model.AikUserFeedback;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
public interface FeedbackService {

    /**
     * 添加医生问题反馈
     *
     * @param doctorFeedback 问题反馈
     * @throws ApiServiceException Api服务异常
     */
    void addDoctorFeedback(AikDoctorFeedback doctorFeedback) throws ApiServiceException;

    /**
     * 添加用户问题反馈
     *
     * @param userFeedback 问题反馈
     * @throws ApiServiceException Api服务异常
     */
    void addUserFeedback(AikUserFeedback userFeedback) throws ApiServiceException;
}
