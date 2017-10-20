package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.DoctorInfoDTO;
import com.aik.dto.request.FeedbackReqDTO;
import com.aik.dto.request.doctor.RebindingMobileReqDTO;
import com.aik.dto.request.doctor.UpdatePwdReqDTO;
import com.aik.enums.FeedbackEnum;
import com.aik.enums.SecurityCodeTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccDoctorAccount;
import com.aik.model.AccDoctorBankCard;
import com.aik.resource.SystemResource;
import com.aik.security.AuthUserDetailsThreadLocal;
import com.aik.service.CommonProblemService;
import com.aik.service.account.DoctorAccountService;
import com.aik.service.account.SecurityCodeService;
import com.aik.service.question.AnswerService;
import com.aik.service.relation.DoctorRelationService;
import com.aik.service.setting.FeedbackService;
import com.aik.service.store.DoctorDealService;
import com.aik.util.AikFileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/7.
 */
@Path("/doctorCenter")
@Produces(MediaType.APPLICATION_JSON)
@Configuration
public class DoctorCenterApi {

    private static final Logger logger = LoggerFactory.getLogger(DoctorCenterApi.class);

    @Value("${file.upload-root-uri}")
    private String uploadRootUri;

    private DoctorAccountService doctorAccountService;

    private AnswerService answerService;

    private DoctorDealService doctorDealService;

    private DoctorRelationService doctorRelationService;

    private FeedbackService feedbackService;

    private CommonProblemService commonProblemService;

    private SystemResource systemResource;

    private SecurityCodeService securityCodeService;

    @Inject
    public void setDoctorAccountService(DoctorAccountService doctorAccountService) {
        this.doctorAccountService = doctorAccountService;
    }

    @Inject
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Inject
    public void setDoctorDealService(DoctorDealService doctorDealService) {
        this.doctorDealService = doctorDealService;
    }

    @Inject
    public void setDoctorRelationService(DoctorRelationService doctorRelationService) {
        this.doctorRelationService = doctorRelationService;
    }

    @Inject
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Inject
    public void setCommonProblemService(CommonProblemService commonProblemService) {
        this.commonProblemService = commonProblemService;
    }

    @Inject
    public void setSystemResource(SystemResource systemResource) {
        this.systemResource = systemResource;
    }

    @Inject
    public void setSecurityCodeService(SecurityCodeService securityCodeService) {
        this.securityCodeService = securityCodeService;
    }

    @POST
    @Path("/pageInfo")
    public ApiResult getPageInfo() {
        ApiResult result = new ApiResult();

        try {
            Integer doctorId = AuthUserDetailsThreadLocal.getCurrentUserId();
            AccDoctorAccount doctorAccount = doctorAccountService.getDoctorAccount(doctorId);
            Map<String, Object> doctorInfo = new HashMap<>();
            doctorInfo.put("headImg", systemResource.getApiFileUri() + doctorAccount.getHeadImg());
            doctorInfo.put("realName", doctorAccount.getRealName());
            doctorInfo.put("position", doctorAccount.getPosition());
            doctorInfo.put("hosName", doctorAccount.getHosName());
            doctorInfo.put("hosDepartment", doctorAccount.getHosDepartment());
            result.withDataKV("doctorInfo", doctorInfo);

            int answersCount = answerService.getDoctorAnswersCount(doctorId);
            result.withDataKV("answersCount", answersCount);
            int ordersCount = doctorDealService.getSellDealCount(doctorId);
            result.withDataKV("ordersCount", ordersCount);
            int fansCount = doctorRelationService.getDoctorFansCount(doctorId);
            result.withDataKV("fansCount", fansCount);
        } catch (ApiServiceException e) {
            logger.error("get doctor center page info error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor center page info error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("getDoctorInfo")
    public ApiResult getDoctorInfo() {
        ApiResult result = new ApiResult();

        try {
            DoctorInfoDTO doctorInfoDTO = doctorAccountService.getDoctorInfo(AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("doctorInfo", doctorInfoDTO);
        } catch (ApiServiceException e) {
            logger.error("get doctor info error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor info error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/editDoctorInfo")
    public ApiResult editDoctorInfo(DoctorInfoDTO doctorInfoDTO) {
        ApiResult result = new ApiResult();

        try {
            doctorInfoDTO.setAccountId(AuthUserDetailsThreadLocal.getCurrentUserId());
            doctorAccountService.editDoctorInfo(doctorInfoDTO);
        } catch (ApiServiceException e) {
            logger.error("get doctor center page info error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor center page error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/answerList")
    public ApiResult answerList(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("doctorId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> aikAnswers = answerService.getAnswerList(params);
            result.withDataKV("answerList", aikAnswers);
        } catch (ApiServiceException e) {
            logger.error("get doctor answers error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor answers error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/sellOrderList")
    public ApiResult sellOrderList(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("doctorId", AuthUserDetailsThreadLocal.getCurrentUserId());
            Map<String, Object> rsData = doctorDealService.getSellDealDetails(params);
            result.withData(rsData);
        } catch (ApiServiceException e) {
            logger.error("get doctor orders error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor orders error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/fansList")
    public ApiResult fansList(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("attentionId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> fansList = doctorRelationService.getDoctorFansList(params);
            result.withDataKV("fansList", fansList);
        } catch (ApiServiceException e) {
            logger.error("get doctor fans error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor fans error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/doctorWallet")
    public ApiResult doctorWallet() {
        ApiResult result = new ApiResult();

        try {
            BigDecimal amount = doctorAccountService.getWallet(AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("amount", amount);
        } catch (ApiServiceException e) {
            logger.error("get doctor wallet error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor wallet error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/dealDetails")
    public ApiResult dealDetails(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("doctorId", AuthUserDetailsThreadLocal.getCurrentUserId());
            Map<String, Object> dealDetails = doctorDealService.getDealDetails(params);
            result.withData(dealDetails);
        } catch (ApiServiceException e) {
            logger.error("get doctor deal details error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor deal details error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/bankCards")
    public ApiResult bankCards() {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> bankCards = doctorAccountService.getBankCards(AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("bankCards", bankCards);
        } catch (ApiServiceException e) {
            logger.error("get doctor bankCards error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor bankCards error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/addBankCard")
    public ApiResult addBankCard(AccDoctorBankCard bankCard) {
        ApiResult result = new ApiResult();

        try {
            bankCard.setDoctorId(AuthUserDetailsThreadLocal.getCurrentUserId());
            doctorAccountService.addBankCard(bankCard);
        } catch (ApiServiceException e) {
            logger.error("add bank card error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("add bank card error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/addDoctorFeedback")
    public ApiResult addDoctorFeedback(FeedbackReqDTO reqDTO) {
        ApiResult result = new ApiResult();

        try {
            reqDTO.setUserId(AuthUserDetailsThreadLocal.getCurrentUserId());
            feedbackService.addFeedback(reqDTO, FeedbackEnum.FbUserTypeEnum.TYPE_DOCTOR);
        } catch (ApiServiceException e) {
            logger.error("add doctor feedback error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("add doctor feedback error: ", e);
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

    @POST
    @Path("/updatePassword")
    public ApiResult updatePassword(UpdatePwdReqDTO updatePwdDTO) {
        ApiResult result = new ApiResult();

        try {
            updatePwdDTO.setAccountId(AuthUserDetailsThreadLocal.getCurrentUserId());
            doctorAccountService.updatePassword(updatePwdDTO);
        } catch (ApiServiceException e) {
            logger.error("update password error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("update password error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getBindingMobileNo")
    public ApiResult getBindingMobileNo() {
        ApiResult result = new ApiResult();

        try {
            AccDoctorAccount doctorAccount = doctorAccountService.getDoctorAccount(AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("mobileNo", doctorAccount.getMobileNo());
        } catch (Exception e) {
            logger.error("get binding mobile error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/rebindingMobileNo")
    public ApiResult rebindingMobileNo(RebindingMobileReqDTO rebindingMobileReqDTO) {
        ApiResult result = new ApiResult();

        try {
            // 校验验证码
            boolean validRs = securityCodeService.validSecurityCode(String.valueOf(SecurityCodeTypeEnum.CODE_TYPE_USER_REGISTER_BINDING.getType()),
                    rebindingMobileReqDTO.getMobileNo(), rebindingMobileReqDTO.getSecurityCode());
            if (!validRs) {
                result.withFailResult(ErrorCodeEnum.ERROR_CODE_1001002);
            }

            rebindingMobileReqDTO.setAccountId(AuthUserDetailsThreadLocal.getCurrentUserId());
            doctorAccountService.rebindingMobileNo(rebindingMobileReqDTO);
        } catch (Exception e) {
            logger.error("rebinding mobile error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }
}
