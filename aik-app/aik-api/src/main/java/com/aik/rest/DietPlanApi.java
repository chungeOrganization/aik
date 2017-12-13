package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.request.user.BygoneDietRecordAnalyzeReqDTO;
import com.aik.dto.request.user.GetFoodsForDietReqDTO;
import com.aik.dto.response.user.BygoneDietRecordAnalyzeRespDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.DietDailyDietPlan;
import com.aik.model.DietDailyDietRecord;
import com.aik.resource.SystemResource;
import com.aik.security.AuthUserDetailsThreadLocal;
import com.aik.service.diet.DietPlanService;
import com.aik.service.diet.DietRecordService;
import com.aik.service.diet.FoodService;
import com.aik.service.diet.UserCollectService;
import com.aik.util.BeansUtils;
import com.aik.util.DateUtils;
import com.aik.vo.FoodBasicInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
@Path("/dietPlan")
@Produces(MediaType.APPLICATION_JSON)
public class DietPlanApi {

    private static final Logger logger = LoggerFactory.getLogger(DietPlanApi.class);

    private UserCollectService userCollectService;

    private FoodService foodService;

    private DietPlanService dietPlanService;

    private DietRecordService dietRecordService;

    private SystemResource systemResource;

    @Inject
    public void setUserCollectService(UserCollectService userCollectService) {
        this.userCollectService = userCollectService;
    }

    @Inject
    public void setFoodService(FoodService foodService) {
        this.foodService = foodService;
    }

    @Inject
    public void setDietPlanService(DietPlanService dietPlanService) {
        this.dietPlanService = dietPlanService;
    }

    @Inject
    public void setDietRecordService(DietRecordService dietRecordService) {
        this.dietRecordService = dietRecordService;
    }

    @Inject
    public void setSystemResource(SystemResource systemResource) {
        this.systemResource = systemResource;
    }

    @POST
    @Path("/getUserCollectFoods")
    public ApiResult getUserCollectFoods() {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> collectFoods = userCollectService.getUserCollectFoods(AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("collectFoods", collectFoods);
        } catch (ApiServiceException e) {
            logger.error("get user collect foods error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user collect foods error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/delUserCollectFood")
    public ApiResult delUserCollectFood(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            List<Integer> collectFoodIds = (List<Integer>) params.get("collectFoodIds");
            userCollectService.delUserCollectFoods(collectFoodIds);
        } catch (ApiServiceException e) {
            logger.error("delete user collect food error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("delete user collect food error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getFoodDetail")
    public ApiResult getFoodDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer foodId = Integer.valueOf(params.get("foodId").toString());
            Map<String, Object> foodDetail = foodService.getFoodDetailWithUser(foodId,
                    AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withData(foodDetail);
        } catch (ApiServiceException e) {
            logger.error("get food detail error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get food detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/userCollectFood")
    public ApiResult userCollectFood(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer foodId = Integer.valueOf(params.get("foodId").toString());
            userCollectService.userCollectFood(foodId, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("user collect food error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user collect food error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/userCancelCollectFood")
    public ApiResult userCancelCollectFood(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer foodId = Integer.valueOf(params.get("foodId").toString());
            userCollectService.userCancelCollectFood(foodId, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("user cancel collect food error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user cancel collect food error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getFoodMoreElements")
    public ApiResult getFoodMoreElements(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer foodId = Integer.valueOf(params.get("foodId").toString());
            List<Map<String, Object>> foodMoreElements = foodService.getFoodMoreElements(foodId);
            result.withDataKV("foodMoreElements", foodMoreElements);
        } catch (ApiServiceException e) {
            logger.error("user food more elements error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user food more elements error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getFoodCategories")
    public ApiResult getFoodCategories() {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> foodCategories = foodService.getFoodCategories();
            result.withDataKV("foodCategories", foodCategories);
        } catch (ApiServiceException e) {
            logger.error("get food categories error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get food categories error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getFoods")
    public ApiResult getFoods(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> foods = foodService.getFoods(params);
            result.withDataKV("foods", foods);
        } catch (ApiServiceException e) {
            logger.error("get foods error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get foods error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getUserDietPlan")
    public ApiResult getUserDietPlan(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Date recordDate = DateUtils.parseDate(params.get("recordDate").toString(), "yyyy-MM-dd");
            Map<String, Object> userDietPlan = dietPlanService.getUserDietPlan(AuthUserDetailsThreadLocal.getCurrentUserId(), recordDate);
            result.withDataKV("userDietPlan", userDietPlan);
        } catch (ApiServiceException e) {
            logger.error("get user diet plan error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user diet plan error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/updateUserDietPlan")
    public ApiResult updateUserDietPlan(DietDailyDietPlan dailyDietPlan) {
        ApiResult result = new ApiResult();

        try {
            dietPlanService.updateUserDietPlan(dailyDietPlan);
        } catch (ApiServiceException e) {
            logger.error("update user diet plan error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("update user diet plan error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/delUserDietPlan")
    public ApiResult delUserDietPlan(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer dietPlanId = Integer.valueOf(params.get("dietPlanId").toString());
            dietPlanService.delUserDietPlan(dietPlanId);
        } catch (ApiServiceException e) {
            logger.error("delete user diet plan error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("delete user diet plan error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/addUserDietPlan")
    public ApiResult addUserDietPlan(DietDailyDietPlan dailyDietPlan) {
        ApiResult result = new ApiResult();

        try {
            dailyDietPlan.setUserId(AuthUserDetailsThreadLocal.getCurrentUserId());
            Integer id = dietPlanService.addUserDietPlan(dailyDietPlan);

            result.withDataKV("id", id);
        } catch (ApiServiceException e) {
            logger.error("add user diet plan error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("add user diet plan error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/refreshUserDietPlan")
    public ApiResult refreshUserDietPlan(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Date recordDate = DateUtils.parseDate(params.get("recordDate").toString(), "yyyy-MM-dd");
            dietPlanService.refreshUserDietPlan(AuthUserDetailsThreadLocal.getCurrentUserId(), recordDate);
        } catch (ApiServiceException e) {
            logger.error("refresh user diet plan error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("refresh user diet plan error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("pasteDietPlanToRecord")
    public ApiResult pasteDietPlanToRecord(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Date recordDate = DateUtils.parseDate(params.get("recordDate").toString(), "yyyy-MM-dd");
            dietPlanService.pasteDietPlanToRecord(AuthUserDetailsThreadLocal.getCurrentUserId(), recordDate);
        } catch (ApiServiceException e) {
            logger.error("paste user diet plan to record error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("paste user diet plan to record error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getDietPlanAnalyze")
    public ApiResult getDietPlanAnalyze(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Date recordDate = DateUtils.parseDate(params.get("recordDate").toString(), "yyyy-MM-dd");
            Map<String, Object> dietPlanAnalyze = dietPlanService.getDietPlanAnalyze(AuthUserDetailsThreadLocal.getCurrentUserId(), recordDate);
            result.withData(dietPlanAnalyze);
        } catch (ApiServiceException e) {
            logger.error("get user diet plan analyze error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user diet plan analyze error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getUserDietRecord")
    public ApiResult getUserDietRecord(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Date recordDate = DateUtils.parseDate(params.get("recordDate").toString(), "yyyy-MM-dd");
            Map<String, Object> userDietRecord = dietRecordService.getUserDietRecord(AuthUserDetailsThreadLocal.getCurrentUserId(),
                    recordDate);
            result.withData(userDietRecord);
        } catch (ApiServiceException e) {
            logger.error("get user diet record error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user diet record error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/updateUserDietRecord")
    public ApiResult updateUserDietRecord(DietDailyDietRecord dailyDietRecord) {
        ApiResult result = new ApiResult();

        try {
            dietRecordService.updateUserDietRecord(dailyDietRecord);
        } catch (ApiServiceException e) {
            logger.error("update user diet Record error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("update user diet Record error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/delUserDietRecord")
    public ApiResult delUserDietRecord(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer dietRecordId = Integer.valueOf(params.get("dietRecordId").toString());
            dietRecordService.delUserDietRecord(dietRecordId);
        } catch (ApiServiceException e) {
            logger.error("delete user diet record error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("delete user diet record error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/addUserDietRecord")
    public ApiResult addUserDietRecord(DietDailyDietRecord dailyDietRecord) {
        ApiResult result = new ApiResult();

        try {
            dailyDietRecord.setUserId(AuthUserDetailsThreadLocal.getCurrentUserId());
            Integer id = dietRecordService.addUserDietRecord(dailyDietRecord);

            result.withDataKV("id", id);
        } catch (ApiServiceException e) {
            logger.error("add user diet record error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("add user diet record error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getDietRecordAnalyze")
    public ApiResult getDietRecordAnalyze(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Date recordDate = DateUtils.parseDate(params.get("recordDate").toString(), "yyyy-MM-dd");
            Map<String, Object> dietRecordAnalyze = dietRecordService.getDietRecordAnalyze(AuthUserDetailsThreadLocal.getCurrentUserId(),
                    recordDate);
            result.withData(dietRecordAnalyze);
        } catch (ApiServiceException e) {
            logger.error("get user diet record analyze error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user diet record analyze error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getBygoneDietRecordAnalyze")
    public ApiResult getBygoneDietRecordAnalyze(BygoneDietRecordAnalyzeReqDTO reqDTO) {
        ApiResult result = new ApiResult();

        try {
            reqDTO.setUserId(AuthUserDetailsThreadLocal.getCurrentUserId());
            BygoneDietRecordAnalyzeRespDTO dietRecordAnalyze = dietRecordService.getBygoneDietRecordAnalyze(reqDTO);
            result.withDataKV("dietRecordAnalyze", dietRecordAnalyze);
        } catch (ApiServiceException e) {
            logger.error("get bygone diet record analyze error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get bygone diet record analyze error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getFoodsForDiet")
    public ApiResult getFoodsForDiet(GetFoodsForDietReqDTO reqDTO) {
        ApiResult result = new ApiResult();

        try {
            List<FoodBasicInfoVO> foods = new ArrayList<>();
            // 查询收藏食物分页
            if (StringUtils.isNotBlank(reqDTO.getFoodName())) {
                Map<String, Object> params = new HashMap<>();
                params.put("name", reqDTO.getFoodName());
                params.put("page", reqDTO.getPage());
                params.put("size", reqDTO.getSize());
                List<Map<String, Object>> foodList = foodService.getFoods(params);
                foods = BeansUtils.transListMap2ListBean(foodList, FoodBasicInfoVO.class);
            } else if (null != reqDTO.getFindType()) {
                // TODO:推荐
                if (reqDTO.getFindType() == 1) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("page", reqDTO.getPage());
                    params.put("size", reqDTO.getSize());
                    List<Map<String, Object>> foodList = foodService.getFoods(params);
                    foods = BeansUtils.transListMap2ListBean(foodList, FoodBasicInfoVO.class);
                }
                // 收藏
                else if ((reqDTO.getFindType() == 2)){
                    List<Map<String, Object>> foodList =  userCollectService.getUserCollectFoodsPage(
                            AuthUserDetailsThreadLocal.getCurrentUserId(), reqDTO);
                    foods = BeansUtils.transListMap2ListBean(foodList, FoodBasicInfoVO.class);
                } else {
                    throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
                }
            } else {
                throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
            }

            result.withDataKV("foods", foods);
        } catch (ApiServiceException e) {
            logger.error("get foods for diet error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get foods for diet error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }


        return result;
    }

    @POST
    @Path("/getDietRecordDates")
    public ApiResult getDietRecordDates() {
        ApiResult result = new ApiResult();

        try {
            List<String> recordDates = dietRecordService.getDietRecordDates(new Date(),
                    AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("recordDates", recordDates);
        } catch (ApiServiceException e) {
            logger.error("get diet record date error ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get diet record date error ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }
}
