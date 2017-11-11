package com.aik.service.question;

import com.aik.assist.ErrorCodeEnum;
import com.aik.bean.userside.QuestionOrderDetail;
import com.aik.dao.*;
import com.aik.dto.response.doctor.AnswerRespDTO;
import com.aik.dto.response.doctor.QuestionAnswerRespDTO;
import com.aik.dto.response.doctor.QuestionOrderDetailRespDTO;
import com.aik.dto.response.doctor.QuestionRespDTO;
import com.aik.enums.AnswerTypeEnum;
import com.aik.enums.QuestionOrderEnum.*;
import com.aik.enums.QuestionTypeEnum;
import com.aik.enums.SexEnum;
import com.aik.enums.UserFileTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.resource.SystemResource;
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

    private SystemResource systemResource;

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

    @Autowired
    public void setSystemResource(SystemResource systemResource) {
        this.systemResource = systemResource;
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

        List<Map<String, Object>> rsList = aikQuestionOrderMapper.selectDoctorDiagnosedOrders(params);

        // TODO: 回答显示处理
        for (Map<String, Object> map : rsList) {
            map.put("sickSex", SexEnum.getDescFromCode(Byte.valueOf(map.get("sickSex").toString())));
            map.put("replyDate", DateUtils.aikPersonaliseDate((Date) map.get("replyDate")));
            map.put("replyContent", ScrawlUtils.aikStringOmit(map.get("replayContent").toString()));
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

    @Override
    public QuestionOrderDetailRespDTO getQuestionOrderDetail(Integer orderId, Integer doctorId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003004);
        }

        QuestionOrderDetailRespDTO questionOrderDetail = new QuestionOrderDetailRespDTO();

        // 订单状态
        questionOrderDetail.setOrderStatus(getDoctorOrderStatus(questionOrder));

        // 回答状态
        if (questionOrder.getStatus() != QuestionOrderStatusEnum.ON_HANDLE.getCode()) {
            questionOrderDetail.setAnswerStatus("完成");
        } else {
            questionOrderDetail.setAnswerStatus("待回答");
        }

        // serviceAttitude 服务态度评分
        questionOrderDetail.setServiceAttitude(questionOrder.getServiceAttitude());

        // answerQuality 回答质量评分
        questionOrderDetail.setAnswerQuality(questionOrder.getAnswerQuality());

        // 用户头像
        AccUserAccount userAccount = accUserAccountMapper.selectByPrimaryKey(questionOrder.getUserId());
        questionOrderDetail.setUserHeaderImg(systemResource.getApiFileUri() + userAccount.getHeadImg());

        // 医生头像（）
        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(doctorId);
        questionOrderDetail.setDoctorHeaderImg(systemResource.getApiFileUri() + doctorAccount.getHeadImg());

        // 问答列表
        List<QuestionAnswerRespDTO> questionAnswerList = getQuestionAnswerList(questionOrder, doctorId);
        questionOrderDetail.setQuestionAnswerList(questionAnswerList);

        return questionOrderDetail;
    }

    /**
     * 获取问答列表
     *
     * @param questionOrder 咨询订单
     * @param doctorId 医生id
     * @return 问答列表
     */
    private List<QuestionAnswerRespDTO> getQuestionAnswerList(AikQuestionOrder questionOrder, Integer doctorId) {
        List<QuestionAnswerRespDTO> questionAnswerList = new ArrayList<>();

        AikQuestion searchAQ = new AikQuestion();
        searchAQ.setOrderId(questionOrder.getId());
        List<AikQuestion> questionsList = aikQuestionMapper.selectBySelective(searchAQ);

        for (AikQuestion aikQuestion : questionsList) {
            // 评分类型过滤
            if (aikQuestion.getType() == QuestionTypeEnum.GRADE.getCode()) {
                continue;
            }

            // 其他用户回答过滤（公开问题）
            if (null != aikQuestion.getFromAnswerId()) {
                AikAnswer fromAnswer = aikAnswerMapper.selectByQuestionId(aikQuestion.getFromAnswerId());
                if (null != fromAnswer && !fromAnswer.getDoctorId().equals(doctorId)) {
                    continue;
                }
            }

            QuestionAnswerRespDTO questionAnswer = new QuestionAnswerRespDTO();

            // 提问
            QuestionRespDTO question = new QuestionRespDTO();
            question.setQuestionId(aikQuestion.getId());
            question.setDescription(aikQuestion.getDescription());
            question.setCreateDate(aikQuestion.getCreateDate());
            if (aikQuestion.getType() == QuestionTypeEnum.INITIAL.getCode()) {
                // 获取图片信息
                AccUserFile searchAU = new AccUserFile();
                searchAU.setUserId(questionOrder.getUserId());
                searchAU.setType(UserFileTypeEnum.QUESTION_FILE.getCode());
                searchAU.setRelationId(questionOrder.getId());
                List<String> files = accUserFileMapper.selectFilesBySelective(searchAU);
                for (int i = 0; i < files.size(); i++) {
                    files.set(i, systemResource.getApiFileUri() + files.get(i));
                }
                question.setFiles(files);
            }
            questionAnswer.setQuestion(question);

            AikAnswer aikAnswer = aikAnswerMapper.selectByQuestionId(aikQuestion.getId());
            if (null != aikAnswer) {
                AnswerRespDTO answer = new AnswerRespDTO();
                answer.setAnswerId(aikAnswer.getId());
                answer.setAnswer(aikAnswer.getAnswer());
                answer.setCreateDate(aikAnswer.getCreateDate());

                questionAnswer.setAnswer(answer);
            }

            questionAnswerList.add(questionAnswer);
        }

        return questionAnswerList;
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
        byte doctorOrderStatus;
        if (questionOrder.getStatus() == QuestionOrderStatusEnum.ON_HANDLE.getCode()) {
            doctorOrderStatus = 2;
        } else {
            if (questionOrder.getIsPayDoctor() == QuestionOrderIsPayDoctorEnum.IS_PAY.getCode()) {
                doctorOrderStatus = 4;
            } else {
                doctorOrderStatus = 3;
            }
        }

        return doctorOrderStatus;
    }
}
