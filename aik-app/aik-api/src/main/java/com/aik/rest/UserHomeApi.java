package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikExpertsAnswer;
import com.aik.model.AikNutritionLesson;
import com.aik.model.AikNutritionalIndex;
import com.aik.request.IssueQORequest;
import com.aik.request.MatchDoctorsRequest;
import com.aik.request.PageRequest;
import com.aik.resource.SystemResource;
import com.aik.response.FreeQODetailResponse;
import com.aik.response.FreeQOListDetailResponse;
import com.aik.response.MatchDoctorsResponse;
import com.aik.security.AuthUserDetailsThreadLocal;
import com.aik.service.account.NutritionalIndexService;
import com.aik.service.diet.DietPlanService;
import com.aik.service.expertsAnswer.ExpertsAnswerService;
import com.aik.service.nutritionLesson.NutritionLessonService;
import com.aik.service.question.FreeQuestionOrderService;
import com.aik.service.question.UserQuestionOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
@Path("/userHome")
@Produces(MediaType.APPLICATION_JSON)
public class UserHomeApi {

    private static final Logger logger = LoggerFactory.getLogger(UserHomeApi.class);

    private DietPlanService dietPlanService;

    private ExpertsAnswerService expertsAnswerService;

    private NutritionLessonService nutritionLessonService;

    private NutritionalIndexService nutritionalIndexService;

    private FreeQuestionOrderService freeQuestionOrderService;

    private UserQuestionOrderService userQuestionOrderService;

    private SystemResource systemResource;

    @Inject
    public void setDietPlanService(DietPlanService dietPlanService) {
        this.dietPlanService = dietPlanService;
    }

    @Inject
    public void setExpertsAnswerService(ExpertsAnswerService expertsAnswerService) {
        this.expertsAnswerService = expertsAnswerService;
    }

    @Inject
    public void setNutritionLessonService(NutritionLessonService nutritionLessonService) {
        this.nutritionLessonService = nutritionLessonService;
    }

    @Inject
    public void setNutritionalIndexService(NutritionalIndexService nutritionalIndexService) {
        this.nutritionalIndexService = nutritionalIndexService;
    }

    @Inject
    public void setFreeQuestionOrderService(FreeQuestionOrderService freeQuestionOrderService) {
        this.freeQuestionOrderService = freeQuestionOrderService;
    }

    @Inject
    public void setUserQuestionOrderService(UserQuestionOrderService userQuestionOrderService) {
        this.userQuestionOrderService = userQuestionOrderService;
    }

    @Inject
    public void setSystemResource(SystemResource systemResource) {
        this.systemResource = systemResource;
    }

    @POST
    @Path("/getHomePage")
    public ApiResult getHomePage() {
        ApiResult result = new ApiResult();

        try {

            // TODO:头部图片（多张）
            result.withDataKV("headImage", new String[]{systemResource.getApiFileUri() + "/headImage.jpg", systemResource.getApiFileUri() + "/headImage2.jpg"});

            // 今日营养
            result.withDataKV("userTodayNutrition", dietPlanService.getUserTodayNutrition(
                    AuthUserDetailsThreadLocal.getCurrentUserId()));

            // 大家问，专家答
            result.withDataKV("expertsAnswers", expertsAnswerService.getHomePageExpertsAnswers());

            // 营养课堂
            Map<String, Object> lessonParams = new HashMap<>();
            // TODO: 页面默认显示2条
            lessonParams.put("page", 1);
            lessonParams.put("size", 2);
            result.withDataKV("nutritionLessons", nutritionLessonService.getNutritionLessons(lessonParams));
        } catch (ApiServiceException e) {
            logger.error("get user home page error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user home page error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getExpertsAnswers")
    public ApiResult getExpertsAnswers(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> expertsAnswers = expertsAnswerService.getExpertsAnswers(params);
            result.withDataKV("expertsAnswers", expertsAnswers);
        } catch (ApiServiceException e) {
            logger.error("get experts answers error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get experts answers error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getExpertsAnswerDetail")
    public ApiResult getExpertsAnswerDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer expertsAnswerId = Integer.valueOf(params.get("expertsAnswerId").toString());
            AikExpertsAnswer expertsAnswerDetail = expertsAnswerService.getExpertsAnswerDetail(expertsAnswerId,
                    AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("expertsAnswerDetail", expertsAnswerDetail);
        } catch (ApiServiceException e) {
            logger.error("get experts answer detail error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get experts answer detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getNutritionLessons")
    public ApiResult getNutritionLessons(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            result.withDataKV("nutritionLessons", nutritionLessonService.getNutritionLessons(params));
        } catch (ApiServiceException e) {
            logger.error("get nutrition lesson list error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get nutrition lesson list error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getNutritionLessonDetail")
    public ApiResult getNutritionLessonDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer nutritionLessonId = Integer.valueOf(params.get("nutritionLessonId").toString());
            AikNutritionLesson nutritionLessonDetail = nutritionLessonService.getNutritionLessonDetail(nutritionLessonId,
                    AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("nutritionLessonDetail", nutritionLessonDetail);
        } catch (ApiServiceException e) {
            logger.error("get nutrition lesson detail error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get nutrition lesson detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getUserNutritionalIndex")
    public ApiResult getUserNutritionalIndex() {
        ApiResult result = new ApiResult();

        try {
            AikNutritionalIndex nutritionalIndex = nutritionalIndexService.getUserNutritionalIndex(
                    AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("nutritionalIndex", nutritionalIndex);
        } catch (ApiServiceException e) {
            logger.error("get user nutritional index error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user nutritional index error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/updateUserNutritionalIndex")
    public ApiResult updateUserNutritionalIndex(AikNutritionalIndex nutritionalIndex) {
        ApiResult result = new ApiResult();

        try {
            nutritionalIndex.setUserId(AuthUserDetailsThreadLocal.getCurrentUserId());
            nutritionalIndexService.updateUserNutritionalIndex(nutritionalIndex);
        } catch (ApiServiceException e) {
            logger.error("update user nutritional index error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("update user nutritional index error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getFreeQuestionOrders")
    public ApiResult getFreeQuestionOrders(PageRequest request) {
        ApiResult result = new ApiResult();

        try {
            List<FreeQOListDetailResponse> freeQuestionOrders = freeQuestionOrderService.getFreeQuestionOrders(request);
            result.withDataKV("freeQuestionOrders", freeQuestionOrders);
        } catch (ApiServiceException e) {
            logger.error("get free question orders error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get free question orders error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/checkFreeQuestionOrder")
    public ApiResult checkFreeQuestionOrder(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer freeOrderId = Integer.valueOf(params.get("freeOrderId").toString());
            FreeQODetailResponse response = freeQuestionOrderService.getFreeQuestionOrderDetail(freeOrderId,
                    AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("questionOrderDetail", response);
        } catch (ApiServiceException e) {
            logger.error("check free question order error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("check free question order error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/sharedFreeQuestionOrder")
    public ApiResult sharedFreeQuestionOrder(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer freeOrderId = Integer.valueOf(params.get("freeOrderId").toString());
            freeQuestionOrderService.sharedFreeQuestionOrder(freeOrderId,
                    AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("shared free question order error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("shared free question order error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getMatchDoctors")
    public ApiResult getMatchDoctors(MatchDoctorsRequest request) {
        ApiResult result = new ApiResult();

        try {
            MatchDoctorsResponse response = userQuestionOrderService.getMatchDoctors(request);
            result.withDataKV("doctorInfoList", response.getDoctorInfoList());
        } catch (ApiServiceException e) {
            logger.error("get match doctors error ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get match doctors error ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/issueQuestionOrder")
    public ApiResult issueQuestionOrder(IssueQORequest request) {
        ApiResult result = new ApiResult();

        try {
            userQuestionOrderService.issueQuestionOrder(AuthUserDetailsThreadLocal.getCurrentUserId(),
                    request);
        } catch (ApiServiceException e) {
            logger.error("issue question order error ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("issue question order error ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }
}