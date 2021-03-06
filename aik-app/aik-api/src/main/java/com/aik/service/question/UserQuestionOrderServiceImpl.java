package com.aik.service.question;

import com.aik.assist.ErrorCodeEnum;
import com.aik.bean.userside.DoctorWithAnswerInfo;
import com.aik.dao.*;
import com.aik.dto.*;
import com.aik.dto.response.doctor.AnswerRespDTO;
import com.aik.dto.response.doctor.QuestionAnswerRespDTO;
import com.aik.dto.response.doctor.QuestionRespDTO;
import com.aik.dto.response.user.OrderDoctorInfoRespDTO;
import com.aik.dto.response.user.QuestionOrderDetailRespDTO;
import com.aik.enums.*;
import com.aik.enums.QuestionOrderEnum.*;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.request.IssueQORequest;
import com.aik.request.MatchDoctorsRequest;
import com.aik.resource.SystemResource;
import com.aik.response.MatchDoctorsResponse;
import com.aik.service.account.DoctorAccountService;
import com.aik.service.relation.DoctorRelationService;
import com.aik.util.BeansUtils;
import com.aik.util.ScrawlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Description:
 * Created by as on 2017/8/28.
 */
@Service
public class UserQuestionOrderServiceImpl implements UserQuestionOrderService {

    private static final Logger logger = LoggerFactory.getLogger(UserQuestionOrderServiceImpl.class);

    private static final String WAIT_AUDIT_TIPS = "平台正在审核您的问题，请您耐心等待，及时刷新状态！";
    private static final String WAIT_PAY_TIPS = "请您尽快付款，付款后问题才会提交到医生端！";
    private static final String AUDIT_FAIL_TIPS = "您好，请您咨询与肿瘤防治相关的问题，三万位医生为您解答，谢谢！可以重新编辑问题，再次提交。";

    private AikQuestionOrderMapper aikQuestionOrderMapper;

    private AikAnswerMapper aikAnswerMapper;

    private AccDoctorAccountMapper accDoctorAccountMapper;

    private AnswerService answerService;

    private AccUserFileMapper accUserFileMapper;

    private AikQuestionMapper aikQuestionMapper;

    private DoctorRelationService doctorRelationService;

    private QuestionOrderAssistService questionOrderAssistService;

    private DoctorAccountService doctorAccountService;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setAikQuestionOrderMapper(AikQuestionOrderMapper aikQuestionOrderMapper) {
        this.aikQuestionOrderMapper = aikQuestionOrderMapper;
    }

    @Autowired
    public void setAikAnswerMapper(AikAnswerMapper aikAnswerMapper) {
        this.aikAnswerMapper = aikAnswerMapper;
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
    public void setAccUserFileMapper(AccUserFileMapper accUserFileMapper) {
        this.accUserFileMapper = accUserFileMapper;
    }

    @Autowired
    public void setAikQuestionMapper(AikQuestionMapper aikQuestionMapper) {
        this.aikQuestionMapper = aikQuestionMapper;
    }

    @Autowired
    public void setDoctorRelationService(DoctorRelationService doctorRelationService) {
        this.doctorRelationService = doctorRelationService;
    }

    @Autowired
    public void setQuestionOrderAssistService(QuestionOrderAssistService questionOrderAssistService) {
        this.questionOrderAssistService = questionOrderAssistService;
    }

    @Autowired
    public void setDoctorAccountService(DoctorAccountService doctorAccountService) {
        this.doctorAccountService = doctorAccountService;
    }

    @Override
    public Integer getQuestionOrderCount(Integer userId) throws ApiServiceException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        return aikQuestionOrderMapper.selectCountByParams(params);
    }

    @Override
    public List<Map<String, Object>> getQuestionOrderList(Map<String, Object> params) throws ApiServiceException {
        List<Map<String, Object>> rsList = new ArrayList<>();

        List<AikQuestionOrder> questionOrders = aikQuestionOrderMapper.selectUserOrders(params);
        for (AikQuestionOrder questionOrder : questionOrders) {
            Map<String, Object> orderInfo = new HashMap<>();
            orderInfo.put("id", questionOrder.getId());
            orderInfo.put("description", ScrawlUtils.aikStringOmit(questionOrder.getDescription()));
            orderInfo.put("createDate", questionOrder.getCreateDate());
            orderInfo.put("status", questionOrder.getStatus());
            orderInfo.put("failType", questionOrder.getFailType());

            AikAnswer searchAA = new AikAnswer();
            searchAA.setOrderId(questionOrder.getId());
            int answerCount = aikAnswerMapper.selectCountBySelective(searchAA);
            orderInfo.put("answerCount", answerCount);

            rsList.add(orderInfo);
        }

        return rsList;
    }

    @Override
    public QuestionOrderDetailRespDTO getQuestionOrderDetail(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        QuestionOrderDetailRespDTO questionOrderDetail = new QuestionOrderDetailRespDTO();
        questionOrderDetail.setOrderId(questionOrder.getId());

        // 医生信息
        OrderDoctorInfoRespDTO doctorInfo = new OrderDoctorInfoRespDTO();

        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(questionOrder.getDoctorId());
        if (null != doctorAccount) {
            doctorInfo.setHeadImg(systemResource.getApiFileUri() + doctorAccount.getHeadImg());
            doctorInfo.setRealName(doctorAccount.getRealName());
            doctorInfo.setHosName(doctorAccount.getHosName());
            doctorInfo.setHosDepartment(doctorAccount.getHosDepartment());
            doctorInfo.setPosition(DoctorPositionEnum.getDescFromCode(doctorAccount.getPosition()));
            doctorInfo.setAnswerCount(answerService.getDoctorAnswersCount(questionOrder.getDoctorId()));
            doctorInfo.setStartLevel(doctorAccount.getStarLevel());
        }
        questionOrderDetail.setDoctorInfo(doctorInfo);

        // 订单价格
        questionOrderDetail.setOrderPrice(questionOrder.getAmount());

        // 审核提示（待审核、审核通过（待付款）、审核不通过、拒绝）
        if (questionOrder.getStatus() == QuestionOrderStatusEnum.ON_AUDIT.getCode()) {
            questionOrderDetail.setOrderTips(WAIT_AUDIT_TIPS);
        } else if (questionOrder.getStatus() == QuestionOrderStatusEnum.ON_PAY.getCode()) {
            questionOrderDetail.setOrderTips(WAIT_PAY_TIPS);
        } else if (questionOrder.getStatus() == QuestionOrderStatusEnum.FAIL_END.getCode() &&
                questionOrder.getFailType() == QuestionOrderFailTypeEnum.AUDIT_FAIL.getCode()) {
            questionOrderDetail.setOrderTips(AUDIT_FAIL_TIPS);
        } else if (questionOrder.getStatus() == QuestionOrderStatusEnum.FAIL_END.getCode() &&
                questionOrder.getFailType() == QuestionOrderFailTypeEnum.DOCTOR_REFUSE.getCode()) {
            questionOrderDetail.setOrderTips(questionOrder.getRefuseReason());
        }

        // 疾病名称
        questionOrderDetail.setIllName(questionOrder.getIllName());

        // 状态
        questionOrderDetail.setOrderStatus(questionOrder.getStatus());
        questionOrderDetail.setFailType(questionOrder.getFailType());

        // 问答列表
        questionOrderDetail.setQuestionAnswerList(getQuestionAnswerList(questionOrder));

        return questionOrderDetail;
    }

    @Override
    public Map<String, Object> getNotPassAuditOrderDetail(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        if (questionOrder.getStatus() != QuestionOrderStatusEnum.FAIL_END.getCode() && questionOrder.getFailType() !=
                QuestionOrderFailTypeEnum.AUDIT_FAIL.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004002);
        }

        Map<String, Object> orderDetail = new HashMap<>();
        orderDetail.put("doctorInfo", getOrderDoctorInfo(questionOrder));

        Map<String, Object> questionInfo = new HashMap<>();
        questionInfo.put("description", questionOrder.getDescription());
        questionInfo.put("createDate", questionOrder.getCreateDate());
        questionInfo.put("illName", questionOrder.getIllName());
        questionInfo.put("auditReason", questionOrder.getAuditReason());

        AccUserFile searchUF = new AccUserFile();
        searchUF.setUserId(questionOrder.getUserId());
        searchUF.setType(UserFileTypeEnum.ORDER_REFUND_FILE.getCode());
        searchUF.setRelationId(orderId);
        searchUF.setDelflag(DelFlagEnum.NOT_DELETED.getCode());
        List<String> orderFiles = accUserFileMapper.selectFilesBySelective(searchUF);
        for (int i = 0; i < orderFiles.size(); i++) {
            orderFiles.set(i, systemResource.getApiFileUri() + orderFiles.get(i));
        }
        questionInfo.put("orderFiles", orderFiles);

        orderDetail.put("questionInfo", questionInfo);

        return orderDetail;
    }

    @Override
    @Transactional
    public void editNotPassAuditOrder(EditQuestionOrderDTO questionOrderDTO) throws ApiServiceException {
        if (null == questionOrderDTO || null == questionOrderDTO.getOrderId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder originalOrder = aikQuestionOrderMapper.selectByPrimaryKey(questionOrderDTO.getOrderId());
        if (null == originalOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        if (originalOrder.getStatus() != QuestionOrderStatusEnum.FAIL_END.getCode() ||
                originalOrder.getFailType() != QuestionOrderFailTypeEnum.AUDIT_FAIL.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004002);
        }

        originalOrder.setDescription(questionOrderDTO.getDescription());
        originalOrder.setIllType(questionOrderDTO.getIllType());
        originalOrder.setIllName(questionOrderDTO.getIllName());
        originalOrder.setType(questionOrderDTO.getType());
        originalOrder.setDoctorId(questionOrderDTO.getDoctorId());

        if (null != questionOrderDTO.getDoctorId()) {
            AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(questionOrderDTO.getDoctorId());
            originalOrder.setAmount(doctorAccount.getPrice());
        } else {
            originalOrder.setAmount(BigDecimal.ZERO);
        }

        originalOrder.setStatus(QuestionOrderStatusEnum.ON_AUDIT.getCode());
        originalOrder.setFailType(QuestionOrderFailTypeEnum.NOT_FAIL.getCode());
        originalOrder.setAuditStatus(QuestionOrderAuditStatusEnum.NOT_AUDIT.getCode());
        originalOrder.setAuditorId(null);
        originalOrder.setAuditReason(null);
        originalOrder.setAuditDate(null);
        originalOrder.setUpdateDate(new Date());

        aikQuestionOrderMapper.updateByPrimaryKey(originalOrder);

        // 问题图片处理
        accUserFileMapper.deleteOrderFiles(originalOrder.getId());

        List<String> questionFiles = questionOrderDTO.getOrderFiles();
        if (null != questionFiles && questionFiles.size() > 0) {
            for (String fileUrl : questionFiles) {
                AccUserFile userFile = new AccUserFile();
                userFile.setUserId(originalOrder.getUserId());
                userFile.setFileUrl(fileUrl);
                userFile.setType(UserFileTypeEnum.QUESTION_FILE.getCode());
                userFile.setRelationId(originalOrder.getId());
                userFile.setCreateDate(new Date());

                accUserFileMapper.insertSelective(userFile);
            }
        }
    }

    @Override
    public Map<String, Object> getOnPayOrderDetail(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        if (questionOrder.getStatus() != QuestionOrderStatusEnum.ON_PAY.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004002);
        }

        Map<String, Object> orderDetail = new HashMap<>();
        orderDetail.put("doctorInfo", getOrderDoctorInfo(questionOrder));

        Map<String, Object> questionInfo = new HashMap<>();
        questionInfo.put("description", questionOrder.getDescription());
        questionInfo.put("createDate", questionOrder.getCreateDate());
        questionInfo.put("illName", questionOrder.getIllName());
        questionInfo.put("auditReason", questionOrder.getAuditReason());

        AccUserFile searchUF = new AccUserFile();
        searchUF.setUserId(questionOrder.getUserId());
        searchUF.setType(UserFileTypeEnum.ORDER_REFUND_FILE.getCode());
        searchUF.setRelationId(orderId);
        searchUF.setDelflag(DelFlagEnum.NOT_DELETED.getCode());
        List<String> orderFiles = accUserFileMapper.selectFilesBySelective(searchUF);
        for (int i = 0; i < orderFiles.size(); i++) {
            orderFiles.set(i, systemResource.getApiFileUri() + orderFiles.get(i));
        }
        questionInfo.put("orderFiles", orderFiles);

        orderDetail.put("questionInfo", questionInfo);

        return orderDetail;
    }

    @Override
    public Map<String, Object> getPayOrderDetail(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        if (questionOrder.getStatus() != QuestionOrderStatusEnum.ON_PAY.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004002);
        }

        Map<String, Object> orderDetail = new HashMap<>();

        Map<String, Object> doctorInfo = new HashMap<>();
        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(questionOrder.getDoctorId());
        doctorInfo.put("headImg", systemResource.getApiFileUri() + doctorAccount.getHeadImg());
        doctorInfo.put("realName", doctorAccount.getRealName());
        doctorInfo.put("department", doctorAccount.getHosDepartment());
        doctorInfo.put("position", DoctorPositionEnum.getDescFromCode(doctorAccount.getPosition()));
        doctorInfo.put("hospital", doctorAccount.getHosName());
        doctorInfo.put("price", doctorAccount.getPrice());

        orderDetail.put("doctorInfo", doctorInfo);
        orderDetail.put("illName", questionOrder.getIllName());
        orderDetail.put("orderAmount", questionOrder.getAmount());

        // TODO：服务类型（0 普通提问 1 加急提问）
        orderDetail.put("serviceType", 0);

        return orderDetail;
    }

    @Override
    public Map<String, Object> getOnEvaluationOrderDetail(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        if (questionOrder.getStatus() != QuestionOrderStatusEnum.ON_EVALUATION.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004002);
        }

        Map<String, Object> orderDetail = new HashMap<>();
        orderDetail.put("doctorInfo", getOrderDoctorInfo(questionOrder));

        List<Map<String, Object>> questionAnswerList = new ArrayList<>();
        // 获取订单所有提问
        AikQuestion searchAQ = new AikQuestion();
        searchAQ.setOrderId(orderId);
        List<AikQuestion> aikQuestions = aikQuestionMapper.selectBySelective(searchAQ);
        for (AikQuestion aikQuestion : aikQuestions) {
            Map<String, Object> questionAnswerMap = new HashMap<>();

            Map<String, Object> question = new HashMap<>();
            question.put("description", aikQuestion.getDescription());
            question.put("questionDate", aikQuestion.getCreateDate());
            question.put("questionType", aikQuestion.getType());

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
            }

            questionAnswerMap.put("question", question);

            Map<String, Object> answer = new HashMap<>();
            AikAnswer aikAnswer = aikAnswerMapper.selectByQuestionId(aikQuestion.getId());
            if (null != aikAnswer) {
                answer.put("answer", aikAnswer.getAnswer());
                answer.put("answerDate", aikAnswer.getCreateDate());
                answer.put("answerId", aikAnswer.getId());

                questionAnswerMap.put("answer", answer);
            }

            questionAnswerList.add(questionAnswerMap);
        }

        orderDetail.put("questionAnswerList", questionAnswerList);

        return orderDetail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applicationOrderRefund(OrderRefundDTO orderRefundDTO) throws ApiServiceException {
        if (null == orderRefundDTO || null == orderRefundDTO.getOrderId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderRefundDTO.getOrderId());
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        if (questionOrder.getStatus() != QuestionOrderStatusEnum.ON_EVALUATION.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004002);
        }

        questionOrder.setStatus(QuestionOrderStatusEnum.ON_REFUND.getCode());
        questionOrder.setEvaluation(orderRefundDTO.getEvaluation());
        questionOrder.setUpdateDate(new Date());

        aikQuestionOrderMapper.updateByPrimaryKeySelective(questionOrder);

        List<String> questionFiles = orderRefundDTO.getQuestionFiles();
        if (null != questionFiles && questionFiles.size() > 0) {
            for (String fileUrl : questionFiles) {
                AccUserFile userFile = new AccUserFile();
                userFile.setUserId(questionOrder.getUserId());
                userFile.setFileUrl(fileUrl);
                userFile.setType(UserFileTypeEnum.ORDER_REFUND_FILE.getCode());
                userFile.setRelationId(questionOrder.getId());
                userFile.setCreateDate(new Date());

                accUserFileMapper.insertSelective(userFile);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void evaluateOrder(EvaluateOrderDTO evaluateOrderDTO) throws ApiServiceException {
        if (null == evaluateOrderDTO || null == evaluateOrderDTO.getOrderId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(evaluateOrderDTO.getOrderId());
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        if (questionOrder.getStatus() != QuestionOrderStatusEnum.ON_EVALUATION.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004002);
        }

        questionOrder.setStatus(QuestionOrderStatusEnum.NORMAL_END.getCode());
        questionOrder.setEvaluation(evaluateOrderDTO.getEvaluation());
        questionOrder.setServiceAttitude(evaluateOrderDTO.getServiceAttitude());
        questionOrder.setAnswerQuality(evaluateOrderDTO.getAnswerQuality());
        questionOrder.setUpdateDate(new Date());

        aikQuestionOrderMapper.updateByPrimaryKeySelective(questionOrder);

        // 支付给医生
        doctorAccountService.payDoctorOrderAmount(questionOrder.getId());
    }

    @Override
    public Map<String, Object> getOnRefuseOrderDetail(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderId);
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        if (questionOrder.getStatus() != QuestionOrderStatusEnum.FAIL_END.getCode() &&
                questionOrder.getFailType() != QuestionOrderFailTypeEnum.DOCTOR_REFUSE.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004002);
        }

        Map<String, Object> orderDetail = new HashMap<>();
        orderDetail.put("doctorInfo", getOrderDoctorInfo(questionOrder));

        Map<String, Object> questionInfo = new HashMap<>();
        questionInfo.put("description", questionOrder.getDescription());
        questionInfo.put("createDate", questionOrder.getCreateDate());
        questionInfo.put("illName", questionOrder.getIllName());
        questionInfo.put("refuseReason", questionOrder.getRefuseReason());

        AccUserFile searchUF = new AccUserFile();
        searchUF.setUserId(questionOrder.getUserId());
        searchUF.setType(UserFileTypeEnum.ORDER_REFUND_FILE.getCode());
        searchUF.setRelationId(orderId);
        searchUF.setDelflag(DelFlagEnum.NOT_DELETED.getCode());
        List<String> orderFiles = accUserFileMapper.selectFilesBySelective(searchUF);
        for (int i = 0; i < orderFiles.size(); i++) {
            orderFiles.set(i, systemResource.getApiFileUri() + orderFiles.get(i));
        }
        questionInfo.put("orderFiles", orderFiles);

        orderDetail.put("questionInfo", questionInfo);

        return orderDetail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderUpdateDoctor(OrderUpdateDoctorDTO orderUpdateDoctorDTO) throws ApiServiceException {
        if (null == orderUpdateDoctorDTO || null == orderUpdateDoctorDTO.getOrderId() ||
                null == orderUpdateDoctorDTO.getDoctorId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(orderUpdateDoctorDTO.getOrderId());
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        if (questionOrder.getStatus() != QuestionOrderStatusEnum.FAIL_END.getCode() &&
                questionOrder.getFailType() != QuestionOrderFailTypeEnum.DOCTOR_REFUSE.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004002);
        }

        questionOrder.setStatus(QuestionOrderStatusEnum.ON_HANDLE.getCode());
        questionOrder.setFailType(QuestionOrderFailTypeEnum.NOT_FAIL.getCode());
        questionOrder.setDoctorId(orderUpdateDoctorDTO.getDoctorId());
        questionOrder.setUpdateDate(new Date());

        aikQuestionOrderMapper.updateByPrimaryKeySelective(questionOrder);
    }

    @Override
    public List<Map<String, Object>> getOpenOrderAnswers(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        List<Map<String, Object>> orderAnswers = new ArrayList<>();
        AikAnswer searchAA = new AikAnswer();
        searchAA.setOrderId(orderId);
        searchAA.setType(AnswerTypeEnum.INITIAL.getCode());
        List<AikAnswer> aikAnswers = aikAnswerMapper.selectBySelective(searchAA);
        for (AikAnswer aikAnswer : aikAnswers) {
            Map<String, Object> orderAnswer = new HashMap<>();
            AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(aikAnswer.getDoctorId());

            orderAnswer.put("answerId", aikAnswer.getId());
            orderAnswer.put("doctorHeadImg", systemResource.getApiFileUri() + doctorAccount.getHeadImg());
            orderAnswer.put("doctorName", doctorAccount.getRealName());
            orderAnswer.put("answer", ScrawlUtils.aikStringOmit(aikAnswer.getAnswer()));

            orderAnswers.add(orderAnswer);
        }

        return orderAnswers;
    }

    @Override
    public void rewardOpenOrder(RewardOpenOrderDTO rewardOpenOrderDTO) throws ApiServiceException {
        if (null == rewardOpenOrderDTO || null == rewardOpenOrderDTO.getOrderId() || null == rewardOpenOrderDTO.getAnswerId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(rewardOpenOrderDTO.getOrderId());
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }

        if (questionOrder.getStatus() != QuestionOrderStatusEnum.ON_HANDLE.getCode()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004002);
        }

        AikAnswer answer = aikAnswerMapper.selectByPrimaryKey(rewardOpenOrderDTO.getAnswerId());

        questionOrder.setDoctorId(answer.getDoctorId());
        questionOrder.setStatus(QuestionOrderStatusEnum.NORMAL_END.getCode());
        questionOrder.setAmount(rewardOpenOrderDTO.getRewardAmount());
        questionOrder.setUpdateDate(new Date());

        aikQuestionOrderMapper.updateByPrimaryKeySelective(questionOrder);
    }

    @Override
    public MatchDoctorsResponse getMatchDoctors(MatchDoctorsRequest request) throws ApiServiceException {
        // sortType 0:：综合排序 1：职称排序 2：活跃度排序
        // TODO:转换排序、疾病
        List<AccDoctorAccount> matchDoctors = accDoctorAccountMapper.selectByParams(BeansUtils.transBean2Map(request));

        MatchDoctorsResponse response = new MatchDoctorsResponse();
        List<DoctorWithAnswerInfo> doctorInfoList = new ArrayList<>();

        for (AccDoctorAccount doctorAccount : matchDoctors) {
            DoctorWithAnswerInfo doctorWithAnswerInfo = new DoctorWithAnswerInfo();
            doctorWithAnswerInfo.setId(doctorAccount.getId());
            doctorWithAnswerInfo.setHeadImg(systemResource.getApiFileUri() + doctorAccount.getHeadImg());
            doctorWithAnswerInfo.setHosDepartment(doctorAccount.getHosDepartment());
            doctorWithAnswerInfo.setHosName(doctorAccount.getHosName());
            doctorWithAnswerInfo.setPosition(DoctorPositionEnum.getDescFromCode(doctorAccount.getPosition()));
            doctorWithAnswerInfo.setPrice(doctorAccount.getPrice());
            doctorWithAnswerInfo.setStarLevel(doctorAccount.getStarLevel());
            doctorWithAnswerInfo.setRealName(doctorAccount.getRealName());

            // 获取回答总数
            AikAnswer searchAA = new AikAnswer();
            searchAA.setDoctorId(doctorAccount.getId());
            searchAA.setType(AnswerTypeEnum.INITIAL.getCode());
            int answerCount = aikAnswerMapper.selectCountBySelective(searchAA);
            doctorWithAnswerInfo.setAnswerCount(answerCount);

            doctorInfoList.add(doctorWithAnswerInfo);
        }

        response.setDoctorInfoList(doctorInfoList);

        return response;
    }

    @Override
    public void issueQuestionOrder(Integer userId, IssueQORequest request) throws ApiServiceException {
        if (null == userId || null == request) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikQuestionOrder questionOrder = new AikQuestionOrder();
        questionOrder.setUserId(userId);
        questionOrder.setDescription(request.getDescription());
        questionOrder.setIllType(request.getIllType());
        questionOrder.setIllName(request.getIllName());
        questionOrder.setType(request.getType());
        questionOrder.setDoctorId(request.getDoctorId());

        if (null != request.getDoctorId()) {
            AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(request.getDoctorId());
            questionOrder.setAmount(doctorAccount.getPrice());
        }

        questionOrder.setCreateDate(new Date());

        aikQuestionOrderMapper.insertSelective(questionOrder);

        // 图片信息
        List<String> questionFiles = request.getOrderFiles();
        if (null != questionFiles && questionFiles.size() > 0) {
            for (String fileUrl : questionFiles) {
                AccUserFile userFile = new AccUserFile();
                userFile.setUserId(questionOrder.getUserId());
                userFile.setFileUrl(fileUrl);
                userFile.setType(UserFileTypeEnum.QUESTION_FILE.getCode());
                userFile.setRelationId(questionOrder.getId());
                userFile.setCreateDate(new Date());

                accUserFileMapper.insertSelective(userFile);
            }
        }

        // 插入question
        AikQuestion question = new AikQuestion();
        question.setOrderId(questionOrder.getId());
        question.setType(QuestionTypeEnum.INITIAL.getCode());
        question.setDescription(questionOrder.getDescription());
        question.setCreateDate(new Date());
        aikQuestionMapper.insertSelective(question);

        // 成为该医生的患者
        if (null != request.getDoctorId()) {
            doctorRelationService.addDoctorSick(userId, request.getDoctorId());
        }

        // 添加问题订单辅助信息
        questionOrderAssistService.addQuestionOrderAssist(questionOrder.getId(), question.getDescription());
    }

    /**
     * 获取订单中医生详情
     *
     * @param questionOrder 订单
     * @return 医生详情
     */
    private Map<String, Object> getOrderDoctorInfo(AikQuestionOrder questionOrder) throws ApiServiceException {
        Map<String, Object> doctorInfo = new HashMap<>();
        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(questionOrder.getDoctorId());

        doctorInfo.put("headImg", systemResource.getApiFileUri() + doctorAccount.getHeadImg());
        doctorInfo.put("realName", doctorAccount.getRealName());
        doctorInfo.put("department", doctorAccount.getHosDepartment());
        doctorInfo.put("position", DoctorPositionEnum.getDescFromCode(doctorAccount.getPosition()));
        doctorInfo.put("hospital", doctorAccount.getHosName());
        doctorInfo.put("answersCount", answerService.getDoctorAnswersCount(questionOrder.getDoctorId()));
        doctorInfo.put("price", doctorAccount.getPrice());
        doctorInfo.put("starLevel", doctorAccount.getStarLevel());

        return doctorInfo;
    }

    /**
     * 获取问答列表
     *
     * @param questionOrder 咨询订单
     * @return 问答列表
     */
    private List<QuestionAnswerRespDTO> getQuestionAnswerList(AikQuestionOrder questionOrder) {
        List<QuestionAnswerRespDTO> questionAnswerList = new ArrayList<>();

        AikQuestion searchAQ = new AikQuestion();
        searchAQ.setOrderId(questionOrder.getId());
        List<AikQuestion> questionsList = aikQuestionMapper.selectBySelective(searchAQ);

        for (AikQuestion aikQuestion : questionsList) {
            if (aikQuestion.getType() == QuestionTypeEnum.GRADE.getCode()) {
                continue;
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
}
