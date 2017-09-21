package com.aik.service.question;

import com.aik.dto.RefuseAnswerDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikAnswer;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
public interface AnswerService {

    /**
     * 获取医生回答总数（ps：不包括追问回答）
     *
     * @param doctorId 医生id
     * @return 医生回答总数
     * @throws ApiServiceException
     */
    Integer getDoctorAnswersCount(Integer doctorId) throws ApiServiceException;

    /**
     * 获取医生回答列表
     *
     * @param params 参数
     * @return 医生回答列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getAnswerList(Map<String, Object> params) throws ApiServiceException;

    /**
     * 回答问题（原始问题）
     *
     * @param aikAnswer 答案
     * @throws ApiServiceException Api服务异常
     */
    void answerQuestion(AikAnswer aikAnswer) throws ApiServiceException;

    /**
     * 拒绝回答问题
     *
     * @param refuseAnswerDTO DTO
     * @throws ApiServiceException Api服务异常
     */
    void refuseAnswer(RefuseAnswerDTO refuseAnswerDTO) throws ApiServiceException;

    /**
     * 根据问题获取答案
     *
     * @param questionId 问题id
     * @return
     * @throws ApiServiceException
     */
    AikAnswer getAnswerFromQuestionId(Integer questionId) throws ApiServiceException;
}
