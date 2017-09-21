package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.*;
import com.aik.enums.SexEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccUserAccount;
import com.aik.model.AikUserFeedback;
import com.aik.resource.SystemResource;
import com.aik.security.AuthUserDetailsThreadLocal;
import com.aik.service.CommonProblemService;
import com.aik.service.account.UserAccountService;
import com.aik.service.question.QuestionService;
import com.aik.service.question.QuestionViewService;
import com.aik.service.question.UserQuestionOrderService;
import com.aik.service.relation.UserAttentionService;
import com.aik.service.setting.FeedbackService;
import com.aik.util.AikFileUtils;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/28.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Configuration
public class UserApi {

    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Value("${file.upload-root-uri}")
    private String uploadRootUri;

    private UserAccountService userAccountService;

    private UserAttentionService userAttentionService;

    private UserQuestionOrderService userQuestionOrderService;

    private QuestionViewService questionViewService;

    private QuestionService questionService;

    private CommonProblemService commonProblemService;

    private FeedbackService feedbackService;

    private SystemResource systemResource;

    @Inject
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Inject
    public void setUserAttentionService(UserAttentionService userAttentionService) {
        this.userAttentionService = userAttentionService;
    }

    @Inject
    public void setUserQuestionOrderService(UserQuestionOrderService userQuestionOrderService) {
        this.userQuestionOrderService = userQuestionOrderService;
    }

    @Inject
    public void setQuestionViewService(QuestionViewService questionViewService) {
        this.questionViewService = questionViewService;
    }

    @Inject
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Inject
    public void setCommonProblemService(CommonProblemService commonProblemService) {
        this.commonProblemService = commonProblemService;
    }

    @Inject
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Inject
    public void setSystemResource(SystemResource systemResource) {
        this.systemResource = systemResource;
    }

    @POST
    @Path("/pageInfo")
    public ApiResult pageInfo() {
        ApiResult result = new ApiResult();

        try {
            Integer userId = AuthUserDetailsThreadLocal.getCurrentUserId();

            // 用户信息
            AccUserAccount userAccount = userAccountService.getUserAccount(userId);
            Map<String, Object> userAccountMap = new HashMap<>();
            userAccountMap.put("nickName", userAccount.getNickName());
            userAccountMap.put("headImg", userAccount.getHeadImg());
            result.withDataKV("userAccount", userAccountMap);
            result.withDataKV("attentionCount", userAttentionService.getUserAttentionCount(userId));
            result.withDataKV("questionOrderCount", userQuestionOrderService.getQuestionOrderCount(userId));
            result.withDataKV("questionViewCount", questionViewService.getQuestionViewCount(userId));
        } catch (ApiServiceException e) {
            logger.error("get user page info error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user page info error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getUserInfo")
    public ApiResult getUserInfo() {
        ApiResult result = new ApiResult();

        try {
            AccUserAccount userAccount = userAccountService.getUserAccount(AuthUserDetailsThreadLocal.getCurrentUserId());
            Map<String, Object> userAccountMap = new HashMap<>();
            userAccountMap.put("headImg", systemResource.getApiFileUri() + userAccount.getHeadImg());
            userAccountMap.put("nickName", userAccount.getNickName());
            userAccountMap.put("mobileNo", userAccount.getMobileNo());
            userAccountMap.put("sex", SexEnum.getDescFromCode(userAccount.getSex()));

            if (null != userAccount.getBirthday()) {
                DateTime birthday = new DateTime(userAccount.getBirthday().getTime());
                userAccountMap.put("birthday", birthday.toLocalDate().toString());
            } else {
                userAccountMap.put("birthday", "");
            }

            userAccountMap.put("areaProvince", userAccount.getAreaProvince());
            userAccountMap.put("areaCity", userAccount.getAreaCity());
            userAccountMap.put("isElseIllness", userAccount.getIsElseIllness());

            result.withData(userAccountMap);
        } catch (ApiServiceException e) {
            logger.error("get user info error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user info error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/uploadUserFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ApiResult uploadUserFile(@FormDataParam("file") InputStream fileInputStream,
                                    @FormDataParam("file") FormDataContentDisposition disposition) {
        ApiResult result = new ApiResult();

        try {
            String imageName = Calendar.getInstance().getTimeInMillis()
                    + disposition.getFileName();

            String fileUri = "user" + File.separator + imageName;
            String fileUrl = uploadRootUri + fileUri;

            AikFileUtils.uploadImg(fileInputStream, fileUrl);

            result.withDataKV("fileUri", fileUri);
            result.withDataKV("fileUrl", fileUrl);
        } catch (Exception e) {
            logger.error("upload user file error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/updateUserInfo")
    public ApiResult updateUserInfo(UserInfoDTO userInfoDTO) {
        ApiResult result = new ApiResult();

        try {
            userInfoDTO.setAccountId(AuthUserDetailsThreadLocal.getCurrentUserId());
            userAccountService.updateUserInfo(userInfoDTO);
        } catch (ApiServiceException e) {
            logger.error("update user info error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("update user info error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getAttentionList")
    public ApiResult getAttentionList(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("userId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> rsList = userAttentionService.getUserAttentionList(params);
            result.withDataKV("attentionList", rsList);
        } catch (ApiServiceException e) {
            logger.error("get user attention list error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user attention list error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getDoctorIntroduction")
    public ApiResult getDoctorIntroduction(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer doctorId = Integer.parseInt(params.get("doctorId").toString());
            Map<String, Object> introduction = userAttentionService.getDoctorIntroduction(doctorId, AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("introduction", introduction);
        } catch (ApiServiceException e) {
            logger.error("get doctor introduction error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor introduction error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/cancelAttentionDoctor")
    public ApiResult cancelAttentionDoctor(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer doctorId = Integer.parseInt(params.get("doctorId").toString());
            userAttentionService.cancelAttentionDoctor(doctorId, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("cancel attention doctor error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("cancel attention doctor error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/attentionDoctor")
    public ApiResult attentionDoctor(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer doctorId = Integer.parseInt(params.get("doctorId").toString());
            userAttentionService.attentionDoctor(doctorId, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("attention doctor error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("attention doctor error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/cancelAttentionUser")
    public ApiResult cancelAttentionUser(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer userId = Integer.parseInt(params.get("userId").toString());
            userAttentionService.cancelAttentionUser(userId, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("cancel attention user error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("cancel attention user error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/attentionUser")
    public ApiResult attentionUser(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer userId = Integer.parseInt(params.get("userId").toString());
            userAttentionService.attentionUser(userId, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("attention user error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("attention user error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getQuestionOrderList")
    public ApiResult getQuestionOrderList(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("userId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> orderList = userQuestionOrderService.getQuestionOrderList(params);
            result.withDataKV("orderList", orderList);
        } catch (ApiServiceException e) {
            logger.error("get question order list error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get question order list error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/notPassAuditOrderDetail")
    public ApiResult notPassAuditOrderDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            Map<String, Object> orderDetail = userQuestionOrderService.getNotPassAuditOrderDetail(orderId);
            result.withData(orderDetail);
        } catch (ApiServiceException e) {
            logger.error("get not pass audit order detail error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get not pass audit order detail error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/editNotPassAuditOrder")
    public ApiResult editNotPassAuditOrder(EditQuestionOrderDTO questionOrderDTO) {
        ApiResult result = new ApiResult();

        try {
            userQuestionOrderService.editNotPassAuditOrder(questionOrderDTO);
        } catch (ApiServiceException e) {
            logger.error("get not pass audit order detail error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get not pass audit order detail error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getOnPayOrderDetail")
    public ApiResult getOnPayOrderDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            Map<String, Object> orderDetail = userQuestionOrderService.getOnPayOrderDetail(orderId);
            result.withData(orderDetail);
        } catch (ApiServiceException e) {
            logger.error("get on pay order detail error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get on pay order detail error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getPayOrderDetail")
    public ApiResult getPayOrderDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            Map<String, Object> orderDetail = userQuestionOrderService.getPayOrderDetail(orderId);
            result.withData(orderDetail);
        } catch (ApiServiceException e) {
            logger.error("get pay order detail error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get pay order detail error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/onEvaluationOrderDetail")
    public ApiResult onEvaluationOrderDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            Map<String, Object> orderDetail = userQuestionOrderService.getOnEvaluationOrderDetail(orderId);
            result.withData(orderDetail);
        } catch (ApiServiceException e) {
            logger.error("get on evaluation order detail error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get on evaluation order detail error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("appendAskFromAnswer")
    public ApiResult appendAskFromAnswer(AppendAskDTO appendAskDTO) {
        ApiResult result = new ApiResult();

        try {
            questionService.appendAskFromAnswer(appendAskDTO);
        } catch (ApiServiceException e) {
            logger.error("append ask from answer error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("append ask from answer error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("applicationOrderRefund")
    public ApiResult applicationOrderRefund(OrderRefundDTO orderRefundDTO) {
        ApiResult result = new ApiResult();

        try {
            userQuestionOrderService.applicationOrderRefund(orderRefundDTO);
        } catch (ApiServiceException e) {
            logger.error("application order refund error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("application order refund error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/evaluateOrder")
    public ApiResult evaluateOrder(EvaluateOrderDTO evaluateOrderDTO) {
        ApiResult result = new ApiResult();

        try {
            userQuestionOrderService.evaluateOrder(evaluateOrderDTO);
        } catch (ApiServiceException e) {
            logger.error("evaluate order error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("evaluate order error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("getOnRefuseOrderDetail")
    public ApiResult getOnRefuseOrderDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            Map<String, Object> orderDetail = userQuestionOrderService.getOnRefuseOrderDetail(orderId);
            result.withData(orderDetail);
        } catch (ApiServiceException e) {
            logger.error("get on refuse order detail error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get on refuse order detail error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("orderUpdateDoctor")
    public ApiResult orderUpdateDoctor(OrderUpdateDoctorDTO orderUpdateDoctorDTO) {
        ApiResult result = new ApiResult();

        try {
            userQuestionOrderService.orderUpdateDoctor(orderUpdateDoctorDTO);
        } catch (ApiServiceException e) {
            logger.error("order update doctor error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("order update doctor error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getOpenOrderAnswers")
    public ApiResult getOpenOrderAnswers(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            List<Map<String, Object>> orderAnswers =  userQuestionOrderService.getOpenOrderAnswers(orderId);
            result.withDataKV("orderAnswers", orderAnswers);
        } catch (ApiServiceException e) {
            logger.error("get open order answers error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get open order answers error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/rewardOpenOrder")
    public ApiResult rewardOpenOrder(RewardOpenOrderDTO rewardOpenOrderDTO) {
        ApiResult result = new ApiResult();

        try {
            userQuestionOrderService.rewardOpenOrder(rewardOpenOrderDTO);
        } catch (ApiServiceException e) {
            logger.error("reward open order error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("reward open order error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getQuestionViewHis")
    public ApiResult getQuestionViewHis(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("userId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> viewHis = questionViewService.getQuestionViewHis(params);
            result.withDataKV("viewHis", viewHis);
        } catch (ApiServiceException e) {
            logger.error("get question view his error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get question view his error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/addUserFeedback")
    public ApiResult addDoctorFeedback(AikUserFeedback feedback) {
        ApiResult result = new ApiResult();

        try {
            feedback.setUserId(AuthUserDetailsThreadLocal.getCurrentUserId());
            feedbackService.addUserFeedback(feedback);
        } catch (ApiServiceException e) {
            logger.error("add user feedback error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("add user feedback error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getCommonProblems")
    public ApiResult getCommonProblems(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> commonProblems = commonProblemService.getCommonProblems(params);
            result.withDataKV("commonProblemList", commonProblems);
        } catch (Exception e) {
            logger.error("get common problems error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }
}