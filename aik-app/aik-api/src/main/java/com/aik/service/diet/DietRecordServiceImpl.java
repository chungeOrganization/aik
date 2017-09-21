package com.aik.service.diet;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.DietDailyDietRecordMapper;
import com.aik.dao.DietDailyNutritionMapper;
import com.aik.enums.DietTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.DietDailyDietPlan;
import com.aik.model.DietDailyDietRecord;
import com.aik.model.DietDailyNutrition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description:
 * Created by as on 2017/9/9.
 */
@Service
public class DietRecordServiceImpl implements DietRecordService {

    private static final Logger logger = LoggerFactory.getLogger(DietRecordServiceImpl.class);

    private DietDailyDietRecordMapper dietDailyDietRecordMapper;

    private DietDailyNutritionMapper dietDailyNutritionMapper;

    @Autowired
    public void setDietDailyDietRecordMapper(DietDailyDietRecordMapper dietDailyDietRecordMapper) {
        this.dietDailyDietRecordMapper = dietDailyDietRecordMapper;
    }

    @Autowired
    public void setDietDailyNutritionMapper(DietDailyNutritionMapper dietDailyNutritionMapper) {
        this.dietDailyNutritionMapper = dietDailyNutritionMapper;
    }

    @Override
    public Map<String, Object> getUserDietRecord(Integer userId, Date recordDate) throws ApiServiceException {
        if (null == userId || null == recordDate) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (recordDate.after(new Date())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004009);
        }

        Map<String, Object> userDietRecord = new HashMap<>();

        // 早餐
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("recordDate", recordDate);
        params.put("dietType", DietTypeEnum.BREAKFAST.getCode());
        List<Map<String, Object>> breakfast = dietDailyDietRecordMapper.selectUserDietRecord(params);
        userDietRecord.put("breakfast", breakfast);
        userDietRecord.put("breakfastAdviceWeight", 200);
        int breakfastTotalWeight = 0;
        for (Map<String, Object> dietRecord : breakfast) {
            breakfastTotalWeight += Integer.valueOf(dietRecord.get("weight").toString());
        }
        userDietRecord.put("breakfastTotalWeight", breakfastTotalWeight);

        // 午餐
        params.put("dietType", DietTypeEnum.LUNCH.getCode());
        List<Map<String, Object>> lunch = dietDailyDietRecordMapper.selectUserDietRecord(params);
        userDietRecord.put("lunch", lunch);
        userDietRecord.put("lunchRemark", 200);
        int lunchTotalWeight = 0;
        for (Map<String, Object> dietRecord : breakfast) {
            lunchTotalWeight += Integer.valueOf(dietRecord.get("weight").toString());
        }
        userDietRecord.put("lunchTotalWeight", lunchTotalWeight);

        // 晚餐
        params.put("dietType", DietTypeEnum.DINNER.getCode());
        List<Map<String, Object>> dinner = dietDailyDietRecordMapper.selectUserDietRecord(params);
        userDietRecord.put("dinner", dinner);
        userDietRecord.put("dinnerRemark", 200);
        int dinnerTotalWeight = 0;
        for (Map<String, Object> dietRecord : breakfast) {
            dinnerTotalWeight += Integer.valueOf(dietRecord.get("weight").toString());
        }
        userDietRecord.put("dinnerTotalWeight", dinnerTotalWeight);


        // 营养补充
        params.put("dietType", DietTypeEnum.NUTRITIONAL.getCode());
        List<Map<String, Object>> nutritional = dietDailyDietRecordMapper.selectUserDietRecord(params);
        userDietRecord.put("nutritional", nutritional);
        userDietRecord.put("nutritionalRemark", 200);
        int nutritionalTotalWeight = 0;
        for (Map<String, Object> dietRecord : breakfast) {
            nutritionalTotalWeight += Integer.valueOf(dietRecord.get("weight").toString());
        }
        userDietRecord.put("dinnerTotalWeight", nutritionalTotalWeight);

        return userDietRecord;
    }

    @Override
    public void updateUserDietRecord(DietDailyDietRecord dailyDietRecord) throws ApiServiceException {
        if (null == dailyDietRecord || null == dailyDietRecord.getId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        dietDailyDietRecordMapper.updateByPrimaryKeySelective(dailyDietRecord);
    }

    @Override
    public void delUserDietRecord(Integer dietRecordId) throws ApiServiceException {
        if (null == dietRecordId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        dietDailyDietRecordMapper.deleteByPrimaryKey(dietRecordId);
    }

    @Override
    public void addUserDietRecord(DietDailyDietRecord dailyDietRecord) throws ApiServiceException {
        if (null == dailyDietRecord) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        dailyDietRecord.setCreateDate(new Date());
        dietDailyDietRecordMapper.insertSelective(dailyDietRecord);
    }

    @Override
    public Map<String, Object> getDietRecordAnalyze(Integer userId, Date recordDate) throws ApiServiceException {
        if (null == userId || null == recordDate) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        // TODO:
        Map<String, Object> dietPlanAnalyze = new HashMap<>();
        // TODO:这里改成从配置中获取
        dietPlanAnalyze.put("healthMinGrade", 85);
        dietPlanAnalyze.put("healthMaxGrade", 100);

        // 获取今日饮食记录
        DietDailyDietRecord searchDD = new DietDailyDietRecord();
        searchDD.setUserId(userId);
        searchDD.setRecordDate(recordDate);
        List<DietDailyDietRecord> dailyDietRecords = dietDailyDietRecordMapper.selectBySelective(searchDD);

        // 饮食营养份量
        DietDailyNutrition dailyNutrition = dietDailyNutritionMapper.selectUserNutrition(userId, recordDate);

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
