package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.DoctorInfoDTO;
import com.aik.dto.request.FeedbackReqDTO;
import com.aik.dto.request.doctor.*;
import com.aik.dto.response.doctor.ApplyWithdrawRespDTO;
import com.aik.dto.response.doctor.BankCardDiscernRespDTO;
import com.aik.dto.response.doctor.DoctorInfoRespDTO;
import com.aik.dto.response.doctor.ShowBankWithdrawRespDTO;
import com.aik.enums.DoctorPositionEnum;
import com.aik.enums.FeedbackEnum;
import com.aik.enums.SecurityCodeTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccDoctorAccount;
import com.aik.model.AccDoctorBankCard;
import com.aik.model.AikCommonProblem;
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
import org.apache.http.auth.AUTH;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Description: 医生端-个人中心
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

    /**
     * 获取医生-个人中心页面信息
     */
    @POST
    @Path("/pageInfo")
    public ApiResult getPageInfo() {
        ApiResult result = new ApiResult();

        try {
            Integer doctorId = AuthUserDetailsThreadLocal.getCurrentUserId();

            // 医生信息
            AccDoctorAccount doctorAccount = doctorAccountService.getDoctorAccount(doctorId);
            DoctorInfoRespDTO doctorInfo = new DoctorInfoRespDTO();
            doctorInfo.setHeadImg(systemResource.getApiFileUri() + doctorAccount.getHeadImg());
            doctorInfo.setRealName(doctorAccount.getRealName());
            doctorInfo.setHosName(doctorAccount.getHosName());
            doctorInfo.setHosDepartment(doctorAccount.getHosDepartment());
            doctorInfo.setPosition(DoctorPositionEnum.getDescFromCode(doctorAccount.getPosition()));
            result.withDataKV("doctorInfo", doctorInfo);

            // "我的回答"数量
            int answersCount = answerService.getDoctorAnswersCount(doctorId);
            result.withDataKV("answersCount", answersCount);

            // "销售订单"数量
            int ordersCount = doctorDealService.getSellDealCount(doctorId);
            result.withDataKV("ordersCount", ordersCount);

            // "粉丝"数量
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

    /**
     * 获取医生信息
     */
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

    /**
     * 编辑医生信息
     */
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

    /**
     * 我的回答列表
     */
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

    /**
     * 销售订单
     */
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

    /**
     * 粉丝列表
     */
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

    /**
     * 医生钱包
     */
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

    /**
     * 医生提现申请
     */
    @POST
    @Path("/applyWithdraw")
    public ApiResult applyWithdraw(ApplyWithdrawReqDTO reqDTO) {
        ApiResult result = new ApiResult();

        try {
            reqDTO.setDoctorId(AuthUserDetailsThreadLocal.getCurrentUserId());
            ApplyWithdrawRespDTO respDTO = doctorAccountService.applyWithdraw(reqDTO);
            result.withDataKV("withdrawInfo", respDTO);
        } catch (ApiServiceException e) {
            logger.error("doctor apply withdraw error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("doctor apply withdraw error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/showBankWithdraw")
    public ApiResult showBankWithdraw() {
        ApiResult result = new ApiResult();

        try {
            ShowBankWithdrawRespDTO respDTO = doctorAccountService.showBankWithdraw(AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("withdrawInfo", respDTO);
        } catch (ApiServiceException e) {
            logger.error("show doctor bank withdraw error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("show doctor bank withdraw error: ", e);
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
    @Path("/setPayPassword")
    public ApiResult setPayPassword(PayPasswordReqDTO reqDTO) {
        ApiResult result = new ApiResult();
        try {
            reqDTO.setAccountId(AuthUserDetailsThreadLocal.getCurrentUserId());
            doctorAccountService.setPayPassword(reqDTO);
        } catch (ApiServiceException e) {
            logger.error("set pay password error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("set pay password error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/validPayPassword")
    public ApiResult validPayPassword(PayPasswordReqDTO reqDTO) {
        ApiResult result = new ApiResult();
        try {
            reqDTO.setAccountId(AuthUserDetailsThreadLocal.getCurrentUserId());
            boolean validRs = doctorAccountService.validPayPassword(reqDTO);
            result.withDataKV("validRs", validRs);
        } catch (ApiServiceException e) {
            logger.error("valid pay password error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("valid pay password error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/resetPayPassword")
    public ApiResult resetPayPassword(ResetPayPasswordReqDTO reqDTO) {
        ApiResult result = new ApiResult();
        try {
            // 校验验证码
            boolean validRs = securityCodeService.validSecurityCode(String.valueOf(SecurityCodeTypeEnum.CODE_TYPE_DOCTOR_RESET_PAY_PASSWORD.getType()),
                    reqDTO.getMobileNo(), reqDTO.getSecurityCode());

            if (!validRs) {
                throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001002);
            }

            reqDTO.setAccountId(AuthUserDetailsThreadLocal.getCurrentUserId());
            doctorAccountService.resetPayPassword(reqDTO);
        } catch (ApiServiceException e) {
            logger.error("reset pay password error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("reset pay password error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/bankCardDiscern")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ApiResult bankCardDiscern(@FormDataParam("file") InputStream fileInputStream,
                                     @FormDataParam("file") FormDataContentDisposition disposition) {
        ApiResult result = new ApiResult();
        try {
            BankCardDiscernRespDTO respDTO = new BankCardDiscernRespDTO();
            throw new Exception("识别图片异常");
        } catch (Exception e) {
            logger.error("discern bank card error: ", e);
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
            boolean validRs = securityCodeService.validSecurityCode(String.valueOf(SecurityCodeTypeEnum.CODE_TYPE_DOCTOR_MOBILE_BINDING.getType()),
                    rebindingMobileReqDTO.getMobileNo(), rebindingMobileReqDTO.getSecurityCode());
            if (!validRs) {
                return result.withFailResult(ErrorCodeEnum.ERROR_CODE_1001002);
            }

            rebindingMobileReqDTO.setAccountId(AuthUserDetailsThreadLocal.getCurrentUserId());
            doctorAccountService.rebindingMobileNo(rebindingMobileReqDTO);
        } catch (Exception e) {
            logger.error("rebinding mobile error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/updateDoctorFile")
    public ApiResult uploadDoctorFile(@FormDataParam("file") InputStream fileInputStream,
                                      @FormDataParam("file") FormDataContentDisposition disposition) {
        ApiResult result = new ApiResult();

        try {
            String fileName = disposition.getFileName();
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            String imageName = Calendar.getInstance().getTimeInMillis() + "-doctor" + fileType;

            String fileUri = "doctor" + File.separator + imageName;
            String fileUrl = systemResource.getApiFileUri() + fileUri;
            String uploadUrl = uploadRootUri + fileUri;

            AikFileUtils.uploadImg(fileInputStream, uploadUrl);

            result.withDataKV("fileUri", fileUri);
            result.withDataKV("fileUrl", fileUrl);
        } catch (IOException e) {
            logger.error("upload doctor file error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1001008);
        } catch (Exception e) {
            logger.error("upload doctor file error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }
}
