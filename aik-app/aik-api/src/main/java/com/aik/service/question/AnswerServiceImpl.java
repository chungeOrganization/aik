package com.aik.service.question;


import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AikAnswerMapper;
import com.aik.dao.AikQuestionOrderMapper;
import com.aik.dto.RefuseAnswerDTO;
import com.aik.enums.AnswerTypeEnum;
import com.aik.enums.QuestionOrderEnum.*;
import com.aik.enums.QuestionTypeEnum;
import com.aik.enums.SexEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikAnswer;
import com.aik.model.AikQuestion;
import com.aik.model.AikQuestionOrder;
import com.aik.util.ScrawlUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    private static final Logger logger = LoggerFactory.getLogger(AnswerServiceImpl.class);

    private AikAnswerMapper aikAnswerMapper;

    private QuestionService questionService;

    private AikQuestionOrderMapper aikQuestionOrderMapper;

    @Autowired
    public void setAikAnswerMapper(AikAnswerMapper aikAnswerMapper) {
        this.aikAnswerMapper = aikAnswerMapper;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setAikQuestionOrderMapper(AikQuestionOrderMapper aikQuestionOrderMapper) {
        this.aikQuestionOrderMapper = aikQuestionOrderMapper;
    }

    @Override
    public Integer getDoctorAnswersCount(Integer doctorId) throws ApiServiceException {
        AikAnswer searchAA = new AikAnswer();
        searchAA.setDoctorId(doctorId);
        searchAA.setType(AnswerTypeEnum.INITIAL.getCode());
        return aikAnswerMapper.selectCountBySelective(searchAA);
    }

    @Override
    public List<Map<String, Object>> getAnswerList(Map<String, Object> params) throws ApiServiceException {
        params.put("type", AnswerTypeEnum.INITIAL.getCode());
        Integer doctorId = Integer.valueOf(params.get("doctorId").toString());

        List<Map<String, Object>> rsList = aikAnswerMapper.selectMyAnswerPage(params);
        for (Map<String, Object> map : rsList) {
            byte sex = null != map.get("sickSex") ? Byte.valueOf(map.get("sickSex").toString()) : (byte) 0;
            map.put("sickSex", SexEnum.getDescFromCode(sex));
            Date birthday = null != map.get("sickBirthday") ? (Date) map.get("sickBirthday") : null;
            map.remove("sickBirthday");
            map.put("sickAge", ScrawlUtils.getAgeFromBirthday(birthday));

            map.put("sickDetail", ScrawlUtils.aikStringOmit(map.get("sickDetail").toString()));
            map.put("answerDetail", ScrawlUtils.aikStringOmit(map.get("answerDetail").toString()));

            // 判断医生id是否为当前医生id
            if (null == map.get("doctorId") || !doctorId.equals(Integer.valueOf(map.get("doctorId").toString()))) {
                map.put("answerAmount", new BigDecimal(0));
            }
            map.remove("doctorId");
        }
        return rsList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void answerQuestion(AikAnswer aikAnswer) throws ApiServiceException {
        if (null == aikAnswer) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKeyForUpdate(aikAnswer.getOrderId());
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003004);
        }

        if (questionOrder.getStatus() != QuestionOrderStatusEnum.ON_HANDLE.getCode() &&
                questionOrder.getStatus() != QuestionOrderStatusEnum.ON_EVALUATION.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003007);
        }

        // 获取咨询订单最近提问
        AikQuestion lastQuestion = questionService.getOrderLastQuestion(aikAnswer.getOrderId(), aikAnswer.getDoctorId());

        if (null == lastQuestion) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003014);
        }
        aikAnswer.setFromQuestionId(lastQuestion.getId());

        // 判断是否回答过该问题
        if (aikAnswerMapper.selectCountBySelective(aikAnswer) > 0) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003006);
        }

        // 初始提问回答
        if (lastQuestion.getType() == QuestionTypeEnum.INITIAL.getCode()) {
            aikAnswer.setType(AnswerTypeEnum.INITIAL.getCode());
            aikAnswer.setCreateDate(new Date());
            aikAnswerMapper.insertSelective(aikAnswer);

            // 公开问题不需要修改状态
            if (questionOrder.getType() == QuestionOrderTypeEnum.MATCH_DOCTOR.getCode()) {
                questionOrder.setStatus(QuestionOrderStatusEnum.ON_EVALUATION.getCode());
            }
            questionOrder.setUpdateDate(new Date());
            aikQuestionOrderMapper.updateByPrimaryKeySelective(questionOrder);
        }
        // 追问回答
        else if(lastQuestion.getType() == QuestionTypeEnum.ADDTION.getCode()) {
            aikAnswer.setType(AnswerTypeEnum.ADDITION.getCode());
            aikAnswer.setCreateDate(new Date());
            aikAnswerMapper.insertSelective(aikAnswer);

            questionOrder.setUpdateDate(new Date());
            aikQuestionOrderMapper.updateByPrimaryKeySelective(questionOrder);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseAnswer(RefuseAnswerDTO refuseAnswerDTO) throws ApiServiceException {
        if (null == refuseAnswerDTO || null == refuseAnswerDTO.getOrderId() ||
                StringUtils.isBlank(refuseAnswerDTO.getRefuseReason())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKeyForUpdate(refuseAnswerDTO.getOrderId());
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003004);
        }

        if (questionOrder.getType() != QuestionOrderTypeEnum.MATCH_DOCTOR.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003015);
        }

        if (questionOrder.getStatus() != QuestionOrderStatusEnum.ON_HANDLE.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003007);
        }

        questionOrder.setStatus(QuestionOrderStatusEnum.FAIL_END.getCode());
        questionOrder.setFailType(QuestionOrderFailTypeEnum.DOCTOR_REFUSE.getCode());
        questionOrder.setRefuseReason(refuseAnswerDTO.getRefuseReason());
        questionOrder.setUpdateDate(new Date());
        aikQuestionOrderMapper.updateByPrimaryKeySelective(questionOrder);

        // 添加拒绝answer
        AikQuestion question = questionService.getOriginalQuestion(questionOrder.getId());

        AikAnswer answer = new AikAnswer();
        answer.setOrderId(questionOrder.getId());
        answer.setType(AnswerTypeEnum.REFUSE.getCode());
        answer.setDoctorId(questionOrder.getDoctorId());
        answer.setFromQuestionId(question.getId());
        answer.setAnswer("您拒绝了他");
        answer.setCreateDate(new Date());

        aikAnswerMapper.insertSelective(answer);

        // TODO:返还用户金额
    }

    @Override
    public AikAnswer getAnswerFromQuestionId(Integer questionId) throws ApiServiceException {
        return aikAnswerMapper.selectByQuestionId(questionId);
    }
}
