package com.aik.service.diet;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.DietDailyDietPlanMapper;
import com.aik.dao.DietDailyDietRecordMapper;
import com.aik.dao.DietDailyNutritionMapper;
import com.aik.enums.DietTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.DietDailyDietPlan;
import com.aik.model.DietDailyDietRecord;
import com.aik.model.DietDailyNutrition;
import com.aik.resource.SystemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
@Service
public class DietPlanServiceImpl implements DietPlanService {

    private static final Logger logger = LoggerFactory.getLogger(DietPlanServiceImpl.class);

    private DietDailyNutritionMapper dietDailyNutritionMapper;

    private DietDailyDietPlanMapper dietDailyDietPlanMapper;

    private DietDailyDietRecordMapper dietDailyDietRecordMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setDietDailyNutritionMapper(DietDailyNutritionMapper dietDailyNutritionMapper) {
        this.dietDailyNutritionMapper = dietDailyNutritionMapper;
    }

    @Autowired
    public void setDietDailyDietPlanMapper(DietDailyDietPlanMapper dietDailyDietPlanMapper) {
        this.dietDailyDietPlanMapper = dietDailyDietPlanMapper;
    }

    @Autowired
    public void setDietDailyDietRecordMapper(DietDailyDietRecordMapper dietDailyDietRecordMapper) {
        this.dietDailyDietRecordMapper = dietDailyDietRecordMapper;
    }

    @Override
    public Map<String, Object> getUserTodayNutrition(Integer userId) throws ApiServiceException {
        if (null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Map<String, Object> userNutrition = new HashMap<>();

        DietDailyNutrition dailyNutrition = dietDailyNutritionMapper.selectUserNutrition(userId, new Date());
        userNutrition.put("dailyNutritionGrade", dailyNutritionGrade(dailyNutrition));
        // TODO:这里改成从配置中获取
        userNutrition.put("healthMinGrade", 85);
        userNutrition.put("healthMaxGrade", 100);

        return userNutrition;
    }

    @Override
    public Map<String, Object> getUserDietPlan(Integer userId, Date recordDate) throws ApiServiceException {
        if (null == userId || null == recordDate) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (recordDate.after(new Date())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004009);
        }

        Map<String, Object> userDietPlan = new HashMap<>();

        // 早餐
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("recordDate", recordDate);
        params.put("dietType", DietTypeEnum.BREAKFAST.getCode());
        List<Map<String, Object>> breakfast = dietDailyDietPlanMapper.selectUserDietPlan(params);
        for (Map<String, Object> map : breakfast) {
            map.put("image", systemResource.getApiFileUri() + map.get("image"));
        }
        userDietPlan.put("breakfast", breakfast);

        // 午餐
        params.put("dietType", DietTypeEnum.LUNCH.getCode());
        List<Map<String, Object>> lunch = dietDailyDietPlanMapper.selectUserDietPlan(params);
        for (Map<String, Object> map : lunch) {
            map.put("image", systemResource.getApiFileUri() + map.get("image"));
        }
        userDietPlan.put("lunch", lunch);

        // 晚餐
        params.put("dietType", DietTypeEnum.DINNER.getCode());
        List<Map<String, Object>> dinner = dietDailyDietPlanMapper.selectUserDietPlan(params);
        for (Map<String, Object> map : dinner) {
            map.put("image", systemResource.getApiFileUri() + map.get("image"));
        }
        userDietPlan.put("dinner", dinner);

        return userDietPlan;
    }

    @Override
    public void updateUserDietPlan(DietDailyDietPlan dailyDietPlan) throws ApiServiceException {
        if (null == dailyDietPlan || null == dailyDietPlan.getId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        dietDailyDietPlanMapper.updateByPrimaryKeySelective(dailyDietPlan);
    }

    @Override
    public void delUserDietPlan(Integer dietPlanId) throws ApiServiceException {
        if (null == dietPlanId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        dietDailyDietPlanMapper.deleteByPrimaryKey(dietPlanId);
    }

    @Override
    public void addUserDietPlan(DietDailyDietPlan dailyDietPlan) throws ApiServiceException {
        if (null == dailyDietPlan) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        dailyDietPlan.setCreateDate(new Date());

        dietDailyDietPlanMapper.insertSelective(dailyDietPlan);
    }

    @Override
    public void refreshUserDietPlan(Integer userId, Date recordDate) throws ApiServiceException {
        if (null == userId || null == recordDate) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        // TODO:换一批用户今日饮食计划
    }

    @Override
    public void pasteDietPlanToRecord(Integer userId, Date recordDate) throws ApiServiceException {
        if (null == userId || null == recordDate) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        DietDailyDietPlan searchDD = new DietDailyDietPlan();
        searchDD.setUserId(userId);
        searchDD.setRecordDate(recordDate);
        List<DietDailyDietPlan> dailyDietPlans = dietDailyDietPlanMapper.selectBySelective(searchDD);

        for (DietDailyDietPlan dailyDietPlan : dailyDietPlans) {
            DietDailyDietRecord dailyDietRecord = new DietDailyDietRecord();
            dailyDietRecord.setUserId(dailyDietPlan.getUserId());
            dailyDietRecord.setFoodId(dailyDietPlan.getFoodId());
            dailyDietRecord.setRecordDate(recordDate);
            dailyDietRecord.setWeight(dailyDietPlan.getWeight());
            dailyDietRecord.setDietType(dailyDietPlan.getDietType());
            dailyDietRecord.setCreateDate(new Date());

            dietDailyDietRecordMapper.insertSelective(dailyDietRecord);
        }
    }

    @Override
    public Map<String, Object> getDietPlanAnalyze(Integer userId, Date recordDate) throws ApiServiceException {
        if (null == userId || null == recordDate) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        // TODO:
        Map<String, Object> dietPlanAnalyze = new HashMap<>();
        // TODO:这里改成从配置中获取
        dietPlanAnalyze.put("healthMinGrade", 85);
        dietPlanAnalyze.put("healthMaxGrade", 100);

        // 获取今日饮食计划
        DietDailyDietPlan searchDD = new DietDailyDietPlan();
        searchDD.setUserId(userId);
        searchDD.setRecordDate(recordDate);
        List<DietDailyDietPlan> dailyDietPlans = dietDailyDietPlanMapper.selectBySelective(searchDD);

        // 算出饮食营养份量
        DietDailyNutrition dailyNutrition = new DietDailyNutrition();

        // 获取营养评分
        dietPlanAnalyze.put("dailyNutritionGrade", dailyNutritionGrade(dailyNutrition));

        // 计算分析评分
        dietPlanAnalyze.put("totalAverageGrade", 71);

        // 获取分析结果(0：不通过 1：通过)
        dietPlanAnalyze.put("analyzeResult", 0);

        // 获取不达标营养份量详情
        List<Map<String, Object>> notQualifiedNutritionDetail = new ArrayList<>();
        dietPlanAnalyze.put("notQualifiedNutritionDetail", notQualifiedNutritionDetail);

        // 不达标提示
        dietPlanAnalyze.put("notQualifiedTips", "摄入的脂肪过高，您身体的基本营养将不达标");

        return dietPlanAnalyze;
    }

    /**
     * TODO:每日营养评分
     *
     * @param dailyNutrition 每日营养
     * @return 营养评分
     */
    private Map<String, Object> dailyNutritionGrade(DietDailyNutrition dailyNutrition) {
        Map<String, Object> dailyNutritionGrade = new HashMap<>();

        if (null == dailyNutrition) {
            return dailyNutritionGrade;
        }

        dailyNutritionGrade.put("proteinGrade", 50);
        dailyNutritionGrade.put("lipidGrade", 50);
        dailyNutritionGrade.put("carbsGrade", 50);
        dailyNutritionGrade.put("vitaminGrade", 50);
        dailyNutritionGrade.put("mineralsGrade", 50);
        dailyNutritionGrade.put("waterGrade", 50);
        dailyNutritionGrade.put("dietaryFiberGrade", 50);

        return dailyNutritionGrade;
    }
}
