package com.aik.service.question;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.*;
import com.aik.dto.AppendAskDTO;
import com.aik.enums.DoctorTipsTypeEnum;
import com.aik.enums.QuestionOrderEnum;
import com.aik.enums.QuestionTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.service.account.DoctorTipsService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Description:
 * Created by as on 2017/8/12.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private AikQuestionOrderMapper aikQuestionOrderMapper;

    private AikQuestionMapper aikQuestionMapper;

    private AikAnswerMapper aikAnswerMapper;

    private QuestionOrderAssistService questionOrderAssistService;

    private DoctorTipsService doctorTipsService;

    @Autowired
    public void setAikQuestionOrderMapper(AikQuestionOrderMapper aikQuestionOrderMapper) {
        this.aikQuestionOrderMapper = aikQuestionOrderMapper;
    }

    @Autowired
    public void setAikQuestionMapper(AikQuestionMapper aikQuestionMapper) {
        this.aikQuestionMapper = aikQuestionMapper;
    }

    @Autowired
    public void setAikAnswerMapper(AikAnswerMapper aikAnswerMapper) {
        this.aikAnswerMapper = aikAnswerMapper;
    }

    @Autowired
    public void setQuestionOrderAssistService(QuestionOrderAssistService questionOrderAssistService) {
        this.questionOrderAssistService = questionOrderAssistService;
    }

    @Autowired
    public void setDoctorTipsService(DoctorTipsService doctorTipsService) {
        this.doctorTipsService = doctorTipsService;
    }

    @Override
    public AikQuestion getOriginalQuestion(Integer questionOrderId) throws ApiServiceException {
        if (null == questionOrderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        return aikQuestionMapper.selectOriginalQuestionByOrderId(questionOrderId);
    }

    @Override
    public AikQuestion getOrderLastQuestion(Integer orderId, Integer doctorId) throws ApiServiceException {
        if (null == orderId || null == doctorId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        return aikQuestionMapper.selectLastQuestionByDoctorId(orderId, doctorId);
    }

    @Override
    public List<Map<String, Object>> getHomeOpenQuestion(Integer doctorId) throws ApiServiceException {
        // 获取首页公开问题待确定
        List<AikQuestionOrder> questionOrders = aikQuestionOrderMapper.selectHomeOpenQuestion(doctorId);
        List<Map<String, Object>> rsList = new ArrayList<>();
        for (AikQuestionOrder questionOrder : questionOrders) {
            Map<String, Object> map = new HashMap<>();
            map.put("description", "有一位患者公开问题，请您去围观");
            map.put("createDate", new DateTime(questionOrder.getCreateDate().getTime()).toString("yyyy-MM-dd"));
            rsList.add(map);
        }
        return rsList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void appendAskFromAnswer(AppendAskDTO appendAskDTO) throws ApiServiceException {
        if (null == appendAskDTO || null == appendAskDTO.getAnswerId() || StringUtils.isBlank(appendAskDTO.getQuestion())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikAnswer answer = aikAnswerMapper.selectByPrimaryKey(appendAskDTO.getAnswerId());
        if (null == answer) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004003);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(answer.getOrderId());
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        if (questionOrder.getStatus() != QuestionOrderEnum.QuestionOrderStatusEnum.ON_HANDLE.getCode() &&
                questionOrder.getStatus() != QuestionOrderEnum.QuestionOrderStatusEnum.ON_EVALUATION.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004002);
        }

        AikQuestion question = aikQuestionMapper.selectByAnswerId(appendAskDTO.getAnswerId());
        if (null != question) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004004);
        }

        AikQuestion appendQuestion = new AikQuestion();
        appendQuestion.setOrderId(answer.getOrderId());
        appendQuestion.setDescription(appendAskDTO.getQuestion());
        appendQuestion.setFromAnswerId(appendAskDTO.getAnswerId());
        appendQuestion.setType(QuestionTypeEnum.ADDTION.getCode());
        appendQuestion.setCreateDate(new Date());

        aikQuestionMapper.insertSelective(appendQuestion);

        // 医聊提示
        AikDoctorTips doctorTip = new AikDoctorTips();
        doctorTip.setDoctorId(answer.getDoctorId());
        doctorTip.setUserId(questionOrder.getUserId());
        doctorTip.setTipsType(DoctorTipsTypeEnum.NEW_QUESTION.getCode());
        doctorTip.setRelationId(answer.getOrderId());
        doctorTip.setTipsMessage(appendQuestion.getDescription());
        doctorTipsService.addDoctorTips(doctorTip);

        // 添加问题订单辅助信息
        questionOrderAssistService.addQuestionOrderAssist(answer.getOrderId(), appendQuestion.getDescription());
    }
}
