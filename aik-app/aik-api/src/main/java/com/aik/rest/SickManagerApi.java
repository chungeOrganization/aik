package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.RefuseAnswerDTO;
import com.aik.dto.request.doctor.SickListReqDTO;
import com.aik.dto.request.doctor.SickOrderListReqDTO;
import com.aik.dto.response.doctor.SickListRespDTO;
import com.aik.dto.response.doctor.SickOrderListRespDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikAnswer;
import com.aik.model.AikDoctorSickGroup;
import com.aik.security.AuthUserDetailsThreadLocal;
import com.aik.service.question.AnswerService;
import com.aik.service.question.QuestionService;
import com.aik.service.relation.DoctorRelationService;
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
 * Created by as on 2017/8/7.
 */
@Path("/sickManager")
@Produces(MediaType.APPLICATION_JSON)
public class SickManagerApi {

    private static final Logger logger = LoggerFactory.getLogger(SickManagerApi.class);

    private DoctorRelationService doctorRelationService;

    private QuestionService questionService;

    private AnswerService answerService;

    @Inject
    public void setDoctorRelationService(DoctorRelationService doctorRelationService) {
        this.doctorRelationService = doctorRelationService;
    }

    @Inject
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Inject
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @POST
    @Path("/getSickGroups")
    public ApiResult getSickGroups() {
        ApiResult result = new ApiResult();

        try {
            List<AikDoctorSickGroup> sickGroups = doctorRelationService.getDoctorSickGroups(
                    AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("sickGroups", sickGroups);
        } catch (ApiServiceException e) {
            logger.error("get sick groups error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get sick groups error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getSickList")
    public ApiResult getSickList(SickListReqDTO reqDTO) {
        ApiResult result = new ApiResult();

        try {
            reqDTO.setDoctorId(AuthUserDetailsThreadLocal.getCurrentUserId());
            List<SickListRespDTO> sickList = doctorRelationService.getSickList(reqDTO);
            result.withDataKV("sickList", sickList);
        } catch (ApiServiceException e) {
            logger.error("get sick list error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get sick list error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("getSickOrders")
    public ApiResult getSickOrders(SickOrderListReqDTO reqDTO) {
        ApiResult result = new ApiResult();

        try {
            List<SickOrderListRespDTO> sickOrderList = doctorRelationService.getSickOrderList(reqDTO);
            result.withDataKV("sickOrderList", sickOrderList);
        } catch (ApiServiceException e) {
            logger.error("get sick orders error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get sick orders error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getSickDetail")
    public ApiResult getSickDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer sickId = Integer.valueOf(params.get("sickId").toString());
            Map<String, Object> rsData = doctorRelationService.getSickDetail(sickId, AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withData(rsData);
        } catch (ApiServiceException e) {
            logger.error("get sick list error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get sick detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getSickGroupsWithSick")
    public ApiResult getSickGroupsWithSick(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer sickId = Integer.valueOf(params.get("sickId").toString());
            List<AikDoctorSickGroup> sickGroups = doctorRelationService.getDoctorSickGroups(
                    AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("sickGroups", sickGroups);
            Integer sickGroup = doctorRelationService.getSickGroup(sickId);
            result.withDataKV("sickGroup", sickGroup);
        } catch (ApiServiceException e) {
            logger.error("get sick groups with select error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get sick groups with select error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/addSickGroup")
    public ApiResult addSickGroup(AikDoctorSickGroup aikDoctorSickGroup) {
        ApiResult result = new ApiResult();

        try {
            aikDoctorSickGroup.setDoctorId(AuthUserDetailsThreadLocal.getCurrentUserId());
            doctorRelationService.addSickGroup(aikDoctorSickGroup);
        } catch (ApiServiceException e) {
            logger.error("add sick group error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("add sick group error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/deleteSickGroup")
    public ApiResult deleteSickGroup(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer sickGroupId = Integer.valueOf(params.get("sickGroupId").toString());
            doctorRelationService.deleteSickGroup(sickGroupId);
        } catch (ApiServiceException e) {
            logger.error("delete sick group error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("delete sick group error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/updateSickGroup")
    public ApiResult updateSickGroup(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer sickId = Integer.valueOf(params.get("sickId").toString());
            Integer sickGroupId = Integer.valueOf(params.get("sickGroupId").toString());
            doctorRelationService.updateSickGroup(sickId, sickGroupId);
        } catch (ApiServiceException e) {
            logger.error("update sick group error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("update sick group error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/processingQuestionOrder")
    public ApiResult processingQuestionOrder(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer questionOrderId = Integer.valueOf(params.get("questionOrderId").toString());
            Map<String, Object> rsData = questionService.getProcessingQODetail(questionOrderId);
            result.withData(rsData);
        } catch (ApiServiceException e) {
            logger.error("get processing question order detail error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get processing question order detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/answerQuestion")
    public ApiResult answerQuestion(AikAnswer aikAnswer) {
        ApiResult result = new ApiResult();

        try {
            aikAnswer.setDoctorId(AuthUserDetailsThreadLocal.getCurrentUserId());
            answerService.answerQuestion(aikAnswer);
        } catch (ApiServiceException e) {
            logger.error("answer question error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("answer question error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/refuseAnswer")
    public ApiResult refuseAnswer(RefuseAnswerDTO refuseAnswerDTO) {
        ApiResult result = new ApiResult();

        try {
            answerService.refuseAnswer(refuseAnswerDTO);
        } catch (ApiServiceException e) {
            logger.error("refuse answer question error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("refuse answer question error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/completedQuestionOrder")
    public ApiResult completedQuestionOrder(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer questionOrderId = Integer.valueOf(params.get("questionOrderId").toString());
            Map<String, Object> rsData = questionService.getCompletedQODetail(questionOrderId);
            result.withData(rsData);
        } catch (ApiServiceException e) {
            logger.error("get completed question order detail error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get completed question order detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }
}
