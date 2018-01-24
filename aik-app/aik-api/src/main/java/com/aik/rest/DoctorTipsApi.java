package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.request.doctor.DoctorTipsListReqDTO;
import com.aik.dto.response.doctor.DoctorTipsListRespDTO;
import com.aik.dto.response.doctor.QuestionOrderDetailRespDTO;
import com.aik.exception.ApiServiceException;
import com.aik.security.AuthUserDetailsThreadLocal;
import com.aik.service.account.DoctorTipsService;
import com.aik.service.question.DoctorQuestionOrderService;
import com.aik.service.relation.DoctorRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/14.
 */
@Path("/doctorTips")
@Produces({MediaType.APPLICATION_JSON})
public class DoctorTipsApi {

    private static final Logger logger = LoggerFactory.getLogger(DoctorTipsApi.class);

    private DoctorTipsService doctorTipsService;

    private DoctorQuestionOrderService doctorQuestionOrderService;

    private DoctorRelationService doctorRelationService;

    @Inject
    public void setDoctorTipsService(DoctorTipsService doctorTipsService) {
        this.doctorTipsService = doctorTipsService;
    }

    @Inject
    public void setDoctorQuestionOrderService(DoctorQuestionOrderService doctorQuestionOrderService) {
        this.doctorQuestionOrderService = doctorQuestionOrderService;
    }

    @Inject
    public void setDoctorRelationService(DoctorRelationService doctorRelationService) {
        this.doctorRelationService = doctorRelationService;
    }

    @POST
    @Path("/getDoctorTipsCount")
    public ApiResult getDoctorTipsCount() {
        ApiResult result = new ApiResult();

        try {
            int count = doctorTipsService.getDoctorTipsCount(AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("tipsCount", count);
        } catch (ApiServiceException e) {
            logger.error("get doctor tips count error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor tips count error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getDoctorTipsList")
    public ApiResult getDoctorTipsList() {
        ApiResult result = new ApiResult();

        try {
            DoctorTipsListReqDTO reqDTO = new DoctorTipsListReqDTO();
            reqDTO.setDoctorId(AuthUserDetailsThreadLocal.getCurrentUserId());
            DoctorTipsListRespDTO tipsList = doctorTipsService.getDoctorTipsList(reqDTO);
            result.withDataKV("tipsList", tipsList);
        } catch (ApiServiceException e) {
            logger.error("get doctor tips list error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get doctor tips list error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/checkNewFriendTips")
    public ApiResult checkNewFriendTips() {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> friendTipsList = doctorTipsService.checkNewFriendTips(AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("friendTipsList", friendTipsList);
        } catch (ApiServiceException e) {
            logger.error("check new friend tips error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("check new friend tips error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/checkQuestionTips")
    public ApiResult checkQuestionTips(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            QuestionOrderDetailRespDTO questionOrderDetail = doctorQuestionOrderService.getQuestionOrderDetail(orderId,
                    AuthUserDetailsThreadLocal.getCurrentUserId());

            Integer tipsId = Integer.valueOf(params.get("tipsId").toString());
            doctorTipsService.checkQuestionTips(tipsId);

            result.withDataKV("questionOrderDetail", questionOrderDetail);
        } catch (ApiServiceException e) {
            logger.error("check question tips error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("check question tips error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/deleteTips")
    public ApiResult deleteTips(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer tipsId = Integer.valueOf(params.get("tipsId").toString());
            doctorTipsService.deleteTips(tipsId);
        } catch (ApiServiceException e) {
            logger.error("delete tips error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("delete tips error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/attentionUser/{userId}/{status}")
    public ApiResult attentionUser(@PathParam("userId") Integer userId, @PathParam("status") Byte status) {
        ApiResult result = new ApiResult();

        try {
            doctorRelationService.attentionUser(userId, status,
                    AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("attention user error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("attention user error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/cancelAttentionUser/{userId}")
    public ApiResult cancelAttentionUser(@PathParam("userId") Integer userId) {
        ApiResult result = new ApiResult();

        try {
            doctorRelationService.cancelAttentionUser(userId,
                    AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("cancel attention user error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("cancel attention user error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }
}
