package com.aik.service.diet;

import com.aik.exception.ApiServiceException;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface FoodService {

    /**
     * 获取食物详情（用户）
     *
     * @param foodId 食物id
     * @param userId 用户id
     * @return 食物详情
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getFoodDetailWithUser(Integer foodId, Integer userId) throws ApiServiceException;

    /**
     * 获取食物更多元素
     *
     * @param foodId 食物id
     * @return 返回食物更多元素
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getFoodMoreElements(Integer foodId) throws ApiServiceException;

    /**
     * 获取食物分类
     *
     * @return 食物分类
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getFoodCategories() throws ApiServiceException;

    /**
     * 获取食物列表
     *
     * @param params 参数
     * @return 食物列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getFoods(Map<String, Object> params) throws ApiServiceException;

//    List<FoodRespDTO>
}
