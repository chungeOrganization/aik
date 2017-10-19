package com.aik.service.question;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.*;
import com.aik.enums.AnswerTypeEnum;
import com.aik.enums.QuestionOrderEnum.*;
import com.aik.enums.QuestionTypeEnum;
import com.aik.enums.SexEnum;
import com.aik.enums.UserFileTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccUserFile;
import com.aik.model.AikAnswer;
import com.aik.model.AikQuestion;
import com.aik.model.AikQuestionOrder;
import com.aik.util.DateUtils;
import com.aik.util.ScrawlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description:
 * Created by as on 2017/8/20.
 */
@Service
public class DoctorQuestionOrderServiceImpl implements DoctorQuestionOrderService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorQuestionOrderService.class);

    private AikQuestionOrderMapper aikQuestionOrderMapper;

    private AccUserAccountMapper accUserAccountMapper;

    private AccDoctorAccountMapper accDoctorAccountMapper;

    private AikQuestionMapper aikQuestionMapper;

    private AikAnswerMapper aikAnswerMapper;

    private AccUserFileMapper accUserFileMapper;

    @Autowired
    public void setAikQuestionOrderMapper(AikQuestionOrderMapper aikQuestionOrderMapper) {
        this.aikQuestionOrderMapper = aikQuestionOrderMapper;
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
    public void setAikQuestionMapper(AikQuestionMapper aikQuestionMapper) {
        this.aikQuestionMapper = aikQuestionMapper;
    }

    @Autowired
    public void setAikAnswerMapper(AikAnswerMapper aikAnswerMapper) {
        this.aikAnswerMapper = aikAnswerMapper;
    }

    @Autowired
    public void setAccUserFileMapper(AccUserFileMapper accUserFileMapper) {
        this.accUserFileMapper = accUserFileMapper;
    }

    @Override
    public Integer getDoctorDiagnosedOrderCount(Integer doctorId) throws ApiServiceException {
        if (null == doctorId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("doctorId", doctorId);
        params.put("type", QuestionOrderTypeEnum.MATCH_DOCTOR.getCode());
        params.put("statusArray", QuestionOrderStatusEnum.getDoctorProcessedStatusWithRefuse());
        params.put("failTypeArray", new byte[]{QuestionOrderFailTypeEnum.NOT_FAIL.getCode(), QuestionOrderFailTypeEnum.DOCTOR_REFUSE.getCode()});

        return aikQuestionOrderMapper.selectCountByParams(params);
    }

    @Override
    public Integer getInHandOrderCount(Integer doctorId) throws ApiServiceException {
        if (null == doctorId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("doctorId", doctorId);
        params.put("type", QuestionOrderTypeEnum.MATCH_DOCTOR.getCode());
        params.put("statusArray", new byte[]{QuestionOrderStatusEnum.ON_HANDLE.getCode()});

        return aikQuestionOrderMapper.selectCountByParams(params);
    }

    @Override
    public List<Map<String, Object>> getDiagnosedOrders(Map<String, Object> params) throws ApiServiceException {
        params.put("statusArray", QuestionOrderStatusEnum.getDoctorProcessedStatusWithRefuse());
        params.put("typesArray", new byte[]{AnswerTypeEnum.INITIAL.getCode(), AnswerTypeEnum.REFUSE.getCode()});

        List<Map<String, Object>> rsList = aikQuestionOrderMapper.selectDoctorDiagnosedOrders(params);

        // TODO: 回答显示处理
        for (Map<String, Object> map : rsList) {
            map.put("sickSex", SexEnum.getDescFromCode(Byte.valueOf(map.get("sickSex").toString())));
            map.put("createDate", DateUtils.aikPersonaliseDate((Date) map.get("createDate")));
        }

        return rsList;
    }

    @Override
    public Map<String, Object> getDiagnosedOrderDetail(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003004);
        }

        Map<String, Object> rsData = new HashMap<>();
        fillOrderDetail(questionOrder, rsData);

        return rsData;
    }

    @Override
    public List<Map<String, Object>> getInHandleOrders(Map<String, Object> params) throws ApiServiceException {
        params.put("statusArray", new byte[]{QuestionOrderStatusEnum.ON_HANDLE.getCode()});
        params.put("typesArray", new byte[]{QuestionTypeEnum.INITIAL.getCode()});
        List<Map<String, Object>> rsList = aikQuestionOrderMapper.selectDoctorInHandOrders(params);

        for (Map<String, Object> map : rsList) {
            map.put("sickSex", SexEnum.getDescFromCode(Byte.valueOf(map.get("sickSex").toString())));
            map.put("createDate", DateUtils.aikPersonaliseDate((Date) map.get("createDate")));
        }

        return rsList;
    }

    @Override
    public Map<String, Object> getInHandleOrderDetail(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003004);
        }

        Map<String, Object> rsData = new HashMap<>();
        fillOrderDetail(questionOrder, rsData);

        return rsData;
    }

    @Override
    public List<Map<String, Object>> getMyOrders(Map<String, Object> params) throws ApiServiceException {
        int orderStatus = Integer.valueOf(params.get("orderStatus").toString());
        if (orderStatus == -1) {
            params.put("statusArray", QuestionOrderStatusEnum.getDoctorValidStatus());
        } else if(orderStatus == 0) {
            params.put("statusArray", new byte[]{QuestionOrderStatusEnum.ON_HANDLE.getCode()});
        } else if(orderStatus == 1) {
            params.put("statusArray", QuestionOrderStatusEnum.getDoctorProcessedStatus());
        } else {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        List<Map<String, Object>> rsList = aikQuestionOrderMapper.selectDoctorMyOrders(params);

        for (Map<String, Object> map : rsList) {
            map.put("sickSex", SexEnum.getDescFromCode(Byte.valueOf(map.get("sickSex").toString())));
            Date birthday = null != map.get("sickBirthday") ? (Date) map.get("sickBirthday") : null;
            map.remove("sickBirthday");
            map.put("sickAge", ScrawlUtils.getAgeFromBirthday(birthday));

            byte status = Byte.valueOf(map.get("status").toString());
            // answerStatus
            if (Arrays.binarySearch(QuestionOrderStatusEnum.getDoctorProcessedStatusWithRefuse(), status) != -1) {
                map.put("orderStatus", "已完成");
            } else if (status == QuestionOrderStatusEnum.ON_HANDLE.getCode()) {
                map.put("orderStatus", "待处理");
            }
        }

        return rsList;
    }

    @Override
    public Map<String, Object> getMyOrderDetail(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003004);
        }

        Map<String, Object> rsData = new HashMap<>();
        // 订单状态
        byte doctorOrderStatus = getDoctorOrderStatus(questionOrder);
        rsData.put("doctorOrderStatus", doctorOrderStatus);
        if (questionOrder.getServiceAttitude() > 0 && questionOrder.getAnswerQuality() > 0) {
            rsData.put("serviceAttitude", questionOrder.getServiceAttitude());
            rsData.put("answerQuality", questionOrder.getAnswerQuality());
        }

        fillOrderDetail(questionOrder, rsData);

        return rsData;
    }

    @Override
    public List<Map<String, Object>> getOpenQuestionOrders(Map<String, Object> params) throws ApiServiceException {
        List<Map<String, Object>> rsList = aikQuestionOrderMapper.selectOpenQuestionOrders(params);

        for (Map<String, Object> map : rsList) {
            map.put("sickSex", SexEnum.getDescFromCode(Byte.valueOf(map.get("sickSex").toString())));
            Date birthday = null != map.get("sickBirthday") ? (Date) map.get("sickBirthday") : null;
            map.remove("sickBirthday");
            map.put("sickAge", ScrawlUtils.getAgeFromBirthday(birthday));
        }

        return rsList;
    }

    @Override
    public Map<String, Object> getOpenQuestionOrderDetail(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003004);
        }

        Map<String, Object> rsData = new HashMap<>();
        fillOpenOrderDetail(questionOrder, rsData);

        return rsData;
    }

    private void fillOrderDetail(AikQuestionOrder questionOrder, Map<String, Object> rsData) {
        // 用户头像
        String sickHeadImg = accUserAccountMapper.selectByPrimaryKey(questionOrder.getUserId()).getHeadImg();

        // 医生头像
        String doctorHeadImg = accDoctorAccountMapper.selectByPrimaryKey(questionOrder.getDoctorId()).getHeadImg();

        AikQuestion searchAQ = new AikQuestion();
        searchAQ.setOrderId(questionOrder.getId());
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
                searchAU.setRelationId(questionOrder.getId());
                List<String> userFiles = accUserFileMapper.selectFilesBySelective(searchAU);
                question.put("questionFiles", userFiles);

                // answerStatus
                if (Arrays.binarySearch(QuestionOrderStatusEnum.getDoctorProcessedStatus(), questionOrder.getStatus()) != -1) {
                    question.put("answerStatus", "已回答");
                } else if (questionOrder.getStatus() == QuestionOrderStatusEnum.FAIL_END.getCode()){
                    question.put("answerStatus", "已拒绝");
                } else if (questionOrder.getStatus() == QuestionOrderStatusEnum.ON_HANDLE.getCode()) {
                    question.put("answerStatus", "待回答");
                }

                // 疾病名称
                question.put("illName", questionOrder.getIllName());
                // 疾病图片
                question.put("illImg", "xxxx.jpg");
            }

            Map<String, Object> answer = new HashMap<>();
            AikAnswer aikAnswer = aikAnswerMapper.selectByQuestionId(aikQuestion.getId());
            if (null != aikAnswer) {
                answer.put("doctorHeadImg", doctorHeadImg);
                answer.put("answer", aikAnswer.getAnswer());
                answer.put("answerDate", aikAnswer.getCreateDate());

                questionAnswerMap.put("answer", answer);
            }

            questionAnswerMap.put("question", question);

            questionAnswerList.add(questionAnswerMap);
        }
        rsData.put("questionAnswerList", questionAnswerList);
    }

    private void fillOpenOrderDetail(AikQuestionOrder questionOrder, Map<String, Object> rsData) {
        // 用户头像
        String sickHeadImg = accUserAccountMapper.selectByPrimaryKey(questionOrder.getUserId()).getHeadImg();

        AikQuestion searchAQ = new AikQuestion();
        searchAQ.setOrderId(questionOrder.getId());
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
                searchAU.setRelationId(questionOrder.getId());
                List<String> userFiles = accUserFileMapper.selectFilesBySelective(searchAU);
                question.put("questionFiles", userFiles);

                // 疾病名称
                question.put("illName", questionOrder.getIllName());
                // 疾病图片
                question.put("illImg", "xxxx.jpg");
            }

            Map<String, Object> answer = new HashMap<>();
            AikAnswer aikAnswer = aikAnswerMapper.selectByQuestionId(aikQuestion.getId());
            if (null != aikAnswer) {
                answer.put("answer", aikAnswer.getAnswer());
                answer.put("answerDate", aikAnswer.getCreateDate());
            }

            questionAnswerMap.put("question", question);
            questionAnswerMap.put("answer", answer);

            questionAnswerList.add(questionAnswerMap);
        }
        rsData.put("questionAnswerList", questionAnswerList);
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
}
