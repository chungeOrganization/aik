package com.aik.service.question;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.*;
import com.aik.dto.AppendAskDTO;
import com.aik.enums.QuestionOrderEnum.*;
import com.aik.enums.QuestionTypeEnum;
import com.aik.enums.UserFileTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Description:
 * Created by as on 2017/8/12.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private AikQuestionOrderMapper aikQuestionOrderMapper;

    private AccUserFileMapper accUserFileMapper;

    private AikQuestionMapper aikQuestionMapper;

    private AccUserAccountMapper accUserAccountMapper;

    private AccDoctorAccountMapper accDoctorAccountMapper;

    private AnswerService answerService;

    private AikAnswerMapper aikAnswerMapper;

    @Autowired
    public void setAikQuestionOrderMapper(AikQuestionOrderMapper aikQuestionOrderMapper) {
        this.aikQuestionOrderMapper = aikQuestionOrderMapper;
    }

    @Autowired
    public void setAccUserFileMapper(AccUserFileMapper accUserFileMapper) {
        this.accUserFileMapper = accUserFileMapper;
    }

    @Autowired
    public void setAikQuestionMapper(AikQuestionMapper aikQuestionMapper) {
        this.aikQuestionMapper = aikQuestionMapper;
    }

    @Autowired
    public void setAccUserAccountMapper(AccUserAccountMapper accUserAccountMapper) {
        this.accUserAccountMapper = accUserAccountMapper;
    }

    @Autowired
    public void setAccDoctorAccountMapper(AccDoctorAccountMapper accDoctorAccountMapper) {
        this.accDoctorAccountMapper = accDoctorAccountMapper;
    }

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setAikAnswerMapper(AikAnswerMapper aikAnswerMapper) {
        this.aikAnswerMapper = aikAnswerMapper;
    }

    @Override
    public Map<String, Object> getProcessingQODetail(Integer questionOrderId) throws ApiServiceException {
        if (null == questionOrderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Map<String, Object> questionOrderDetail = aikQuestionOrderMapper.selectProcessingDetailById(questionOrderId);
        if (null == questionOrderDetail) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003004);
        }

        Integer userId = Integer.valueOf(questionOrderDetail.get("userId").toString());

        // 获取图片信息
        AccUserFile searchAU = new AccUserFile();
        searchAU.setUserId(userId);
        searchAU.setType(UserFileTypeEnum.ORDER_REFUND_FILE.getCode());
        searchAU.setRelationId(questionOrderId);
        List<String> userFiles = accUserFileMapper.selectFilesBySelective(searchAU);
        questionOrderDetail.put("userFiles", userFiles);

        return questionOrderDetail;
    }

    @Override
    public AikQuestion getOriginalQuestion(Integer questionOrderId) throws ApiServiceException {
        if (null == questionOrderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        return aikQuestionMapper.selectOriginalQuestionByOrderId(questionOrderId);
    }

    @Override
    public Map<String, Object> getCompletedQODetail(Integer questionOrderId) throws ApiServiceException {
        if (null == questionOrderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Map<String, Object> rsData = new HashMap<>();
        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(questionOrderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003004);
        }

        // 订单状态
        byte doctorOrderStatus = getDoctorOrderStatus(questionOrder);
        rsData.put("doctorOrderStatus", doctorOrderStatus);
        rsData.put("serviceAttitude", questionOrder.getServiceAttitude());
        rsData.put("answerQuality", questionOrder.getAnswerQuality());

        // 用户头像
        String sickHeadImg = accUserAccountMapper.selectByPrimaryKey(questionOrder.getUserId()).getHeadImg();

        // 医生头像
        String doctorHeadImg = accDoctorAccountMapper.selectByPrimaryKey(questionOrder.getDoctorId()).getHeadImg();

        AikQuestion searchAQ = new AikQuestion();
        searchAQ.setOrderId(questionOrderId);
        List<AikQuestion> questionsList = aikQuestionMapper.selectBySelective(searchAQ);

        List<Map<String, Object>> questionAnswerList = new ArrayList<>();
        for (AikQuestion aikQuestion : questionsList) {
            Map<String, Object> questionAnswerMap = new HashMap<>();

            Map<String, Object> question = new HashMap<>();
            question.put("sickHeadImg", sickHeadImg);
            question.put("description", aikQuestion.getDescription());
            question.put("questionDate", aikQuestion.getCreateDate());

            if (aikQuestion.getType() == QuestionTypeEnum.INITIAL.getCode()) {
                // 获取图片信息
                AccUserFile searchAU = new AccUserFile();
                searchAU.setUserId(questionOrder.getUserId());
                searchAU.setType(UserFileTypeEnum.ORDER_REFUND_FILE.getCode());
                searchAU.setRelationId(questionOrderId);
                List<String> userFiles = accUserFileMapper.selectFilesBySelective(searchAU);
                question.put("questionFiles", userFiles);
            }

            Map<String, Object> answer = new HashMap<>();
            AikAnswer aikAnswer = answerService.getAnswerFromQuestionId(aikQuestion.getId());
            if (null != aikAnswer) {
                answer.put("doctorHeadImg", doctorHeadImg);
                answer.put("answer", aikAnswer.getAnswer());
                answer.put("answerDate", aikAnswer.getCreateDate());
            }

            questionAnswerMap.put("question", question);
            questionAnswerMap.put("answer", answer);

            questionAnswerList.add(questionAnswerMap);
        }
        rsData.put("questionAnswerList", questionAnswerList);

        return rsData;
    }

    /**
     * 订单状态
     *
     * @param questionOrder 订单
     * @return 1：付款 2：完成回答 3：平台处理 4：金额到账
     */
    private byte getDoctorOrderStatus(AikQuestionOrder questionOrder) {
        byte doctorOorderStatus = 2;
        if (questionOrder.getStatus() == QuestionOrderStatusEnum.NORMAL_END.getCode()) {
            doctorOorderStatus = 3;
        }
        if (questionOrder.getIsPayDoctor() == QuestionOrderIsPayDoctorEnum.IS_PAY.getCode()) {
            doctorOorderStatus = 4;
        }
        return doctorOorderStatus;
    }

    @Override
    public List<Map<String, Object>> getHomeOpenQuestion(Integer doctorId) throws ApiServiceException {
        // TODO：获取首页公开问题待确定
        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectHomeOpenQuestion(doctorId);
        List<Map<String, Object>> rsList = new ArrayList<>();
        if (null != questionOrder) {
            Map<String, Object> map = new HashMap<>();
            map.put("description", "有一位患者公开问题，请您去围观");
            map.put("createDate", new DateTime(questionOrder.getCreateDate().getTime()).toString("yyyy-MM-dd"));
            rsList.add(map);
        }
        return rsList;
    }

    @Override
    public void appendAskFromAnswer(AppendAskDTO appendAskDTO) throws ApiServiceException {
        if (null == appendAskDTO || null == appendAskDTO.getAnswerId() || StringUtils.isBlank(appendAskDTO.getQuestion())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikAnswer answer = aikAnswerMapper.selectByPrimaryKey(appendAskDTO.getAnswerId());
        if (null == answer) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004003);
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
    }
}
