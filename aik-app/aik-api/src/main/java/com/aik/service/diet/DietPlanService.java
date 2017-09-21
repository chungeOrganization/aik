package com.aik.service.diet;

import com.aik.exception.ApiServiceException;
import com.aik.model.DietDailyDietPlan;

import java.util.Date;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public interface DietPlanService {

    /**
     * 获取用户今日营养
     *
     * @param userId 用户id
     * @return 用户今日营养
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getUserTodayNutrition(Integer userId) throws ApiServiceException;

    /**
     * 获取用户饮食计划
     *
     * @param userId     用户id
     * @param recordDate 记录日期
     * @return 用户饮食计划
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getUserDietPlan(Integer userId, Date recordDate) throws ApiServiceException;

    /**
     * 修改用户饮食计划
     *
     * @param dailyDietPlan 饮食计划
     * @throws ApiServiceException API服务异常
     */
    void updateUserDietPlan(DietDailyDietPlan dailyDietPlan) throws ApiServiceException;

    /**
     * 删除用户饮食计划
     *
     * @param dietPlanId 饮食计划id
     * @throws ApiServiceException Api服务异常
     */
    void delUserDietPlan(Integer dietPlanId) throws ApiServiceException;

    /**
     * 添加用户饮食计划
     *
     * @param dailyDietPlan 饮食计划
     * @throws ApiServiceException Api服务异常
     */
    void addUserDietPlan(DietDailyDietPlan dailyDietPlan) throws ApiServiceException;

    /**
     * 刷新用户今日饮食计划
     *
     * @param userId     用户id
     * @param recordDate 记录日期
     * @throws ApiServiceException Api服务异常
     */
    void refreshUserDietPlan(Integer userId, Date recordDate) throws ApiServiceException;

    /**
     * 粘贴到饮食记录
     *
     * @param userId     用户id
     * @param recordDate 记录日期
     * @throws ApiServiceException Api服务异常
     */
    void pasteDietPlanToRecord(Integer userId, Date recordDate) throws ApiServiceException;

    /**
     * 获取用户饮食计划分析
     *
     * @param userId     用户id
     * @param recordDate 记录日期
     * @return 用户今日饮食计划分析
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getDietPlanAnalyze(Integer userId, Date recordDate) throws ApiServiceException;
}
