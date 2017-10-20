package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.response.doctor.SimpleInfoRespDTO;
import com.aik.enums.DoctorPositionEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccDoctorAccount;
import com.aik.model.AikAnswer;
import com.aik.security.AuthUserDetailsThreadLocal;
import com.aik.service.SysSettingService;
import com.aik.service.account.DoctorAccountService;
import com.aik.service.account.DoctorTipsService;
import com.aik.service.question.AnswerService;
import com.aik.service.question.DoctorQuestionOrderService;
import com.aik.service.question.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/15.
 */
@Path("/doctorHome")
@Produces({MediaType.APPLICATION_JSON})
public class DoctorHomeApi {

    private static final Logger logger = LoggerFactory.getLogger(DoctorHomeApi.class);

    private DoctorAccountService doctorAccountService;

    private QuestionService questionService;

    private DoctorTipsService doctorTipsService;

    private SysSettingService sysSettingService;

    private DoctorQuestionOrderService doctorQuestionOrderService;

    private AnswerService answerService;

    @Inject
    public void setDoctorAccountService(DoctorAccountService doctorAccountService) {
        this.doctorAccountService = doctorAccountService;
    }

    @Inject
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Inject
    public void setDoctorTipsService(DoctorTipsService doctorTipsService) {
        this.doctorTipsService = doctorTipsService;
    }

    @Inject
    public void setSysSettingService(SysSettingService sysSettingService) {
        this.sysSettingService = sysSettingService;
    }

    @Inject
    public void setDoctorQuestionOrderService(DoctorQuestionOrderService doctorQuestionOrderService) {
        this.doctorQuestionOrderService = doctorQuestionOrderService;
    }

    @Inject
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @POST
    @Path("/pageInfo")
    public ApiResult getPageInfo() {
        ApiResult result = new ApiResult();

        try {
            Integer doctorId = AuthUserDetailsThreadLocal.getCurrentUserId();

            // 医生信息
            AccDoctorAccount doctorAccount = doctorAccountService.getDoctorAccount(doctorId);
            SimpleInfoRespDTO doctorInfo = new SimpleInfoRespDTO();
            doctorInfo.setHeadImg(doctorAccount.getHeadImg());
            doctorInfo.setRealName(doctorAccount.getRealName());
            doctorInfo.setPosition(DoctorPositionEnum.getDescFromCode(doctorAccount.getPosition()));
            doctorInfo.setHosName(doctorAccount.getHosName());
            doctorInfo.setHosDepartment(doctorAccount.getHosDepartment());
            result.withDataKV("doctorInfo", doctorInfo);

            // 已诊患者总数
            int processedCount = doctorQuestionOrderService.getDoctorDiagnosedOrderCount(doctorId);
            result.withDataKV("processedCount", processedCount);

            // 待诊患者总数
            int inhandCount = doctorQuestionOrderService.getInHandOrderCount(doctorId);
            result.withDataKV("inhandCount", inhandCount);

            // 待办列表
            List<Map<String, Object>> doctorTips = doctorTipsService.getHomeDoctorTips(doctorId);
            result.withDataKV("doctorTips", doctorTips);

            // 公开列表
            List<Map<String, Object>> openQuestions = questionService.getHomeOpenQuestion(doctorId);
            result.withDataKV("openQuestions", openQuestions);

            // 首页图片
            String homeImg = sysSettingService.getDoctorHomeImg();
            result.withDataKV("homeImg", homeImg);
        } catch (ApiServiceException e) {
            logger.error("get doctor center page info error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor home page info error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getDiagnosedOrders")
    public ApiResult getDiagnosedOrders(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("doctorId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> orderList = doctorQuestionOrderService.getDiagnosedOrders(params);
            result.withDataKV("orderList", orderList);
        } catch (ApiServiceException e) {
            logger.error("get diagnosed order list error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get diagnosed order list error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getDiagnosedOrderDetail")
    public ApiResult getDiagnosedOrderDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            Map<String, Object> orderDetail = doctorQuestionOrderService.getDiagnosedOrderDetail(orderId);
            result.withDataKV("orderDetail", orderDetail);
        } catch (ApiServiceException e) {
            logger.error("get diagnosed order detail error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get diagnosed order detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getInHandleOrders")
    public ApiResult getInHandleOrders(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("doctorId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> orderList = doctorQuestionOrderService.getInHandleOrders(params);
            result.withDataKV("orderList", orderList);
        } catch (ApiServiceException e) {
            logger.error("get in handle order list error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get in handle order list error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getInHandleOrderDetail")
    public ApiResult getInHandleOrderDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            Map<String, Object> orderDetail = doctorQuestionOrderService.getInHandleOrderDetail(orderId);
            result.withDataKV("orderDetail", orderDetail);
        } catch (ApiServiceException e) {
            logger.error("get in handle order detail error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get in handle order detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getMyOrders")
    public ApiResult getMyOrders(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("doctorId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> orderList = doctorQuestionOrderService.getMyOrders(params);
            result.withDataKV("orderList", orderList);
        } catch (ApiServiceException e) {
            logger.error("get my order list error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get my order list error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getMyOrderDetail")
    public ApiResult getMyOrderDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            Map<String, Object> orderDetail = doctorQuestionOrderService.getMyOrderDetail(orderId);
            result.withDataKV("orderDetail", orderDetail);
        } catch (ApiServiceException e) {
            logger.error("get my order detail error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get my order detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getOpenQuestionOrders")
    public ApiResult getOpenQuestionOrders(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("doctorId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> orderList = doctorQuestionOrderService.getOpenQuestionOrders(params);
            result.withDataKV("orderList", orderList);
        } catch (ApiServiceException e) {
            logger.error("get open question order list error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get open question order list error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getOpenQuestionOrderDetail")
    public ApiResult getOpenQuestionOrderDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            Map<String, Object> orderDetail = doctorQuestionOrderService.getOpenQuestionOrderDetail(orderId);
            result.withDataKV("orderDetail", orderDetail);
        } catch (ApiServiceException e) {
            logger.error("get open question detail error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get open question detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/answerOpenQuestion")
    public ApiResult answerOpenQuestion(AikAnswer aikAnswer) {
        ApiResult result = new ApiResult();

        try {
            aikAnswer.setDoctorId(AuthUserDetailsThreadLocal.getCurrentUserId());
            answerService.answerQuestion(aikAnswer);
        } catch (ApiServiceException e) {
            logger.error("answer open question error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("answer open question error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getMyQRCode")
    public ApiResult getMyQRCode(AikAnswer aikAnswer) {
        ApiResult result = new ApiResult();

        try {
            Map<String, Object> qrCodeInfo = doctorAccountService.getQRCode(AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("qrCodeInfo", qrCodeInfo);
        } catch (ApiServiceException e) {
            logger.error("get doctor qr code error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor qr code error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }
}
