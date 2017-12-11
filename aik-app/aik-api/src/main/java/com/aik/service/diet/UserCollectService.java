package com.aik.service.diet;

import com.aik.dto.request.PageReqDTO;
import com.aik.exception.ApiServiceException;
import com.aik.vo.FoodBasicInfoVO;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface UserCollectService {

    /**
     * 获取用户收藏食物
     *
     * @param userId 用户id
     * @return 用户收藏食物列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getUserCollectFoods(Integer userId) throws ApiServiceException;

    /**
     * 删除用户收藏食物
     *
     * @param collectFoodIds 收藏食物id集合
     * @throws ApiServiceException Api服务异常
     */
    void delUserCollectFoods(List<Integer> collectFoodIds) throws ApiServiceException;

    /**
     * 用户收藏食物
     *
     * @param foodId 食物id
     * @param userId 用户id
     * @throws ApiServiceException Api服务异常
     */
    void userCollectFood(Integer foodId, Integer userId) throws ApiServiceException;

    /**
     * 用户取消收藏食物
     *
     * @param foodId 食物id
     * @param userId 用户id
     * @throws ApiServiceException Api服务异常
     */
    void userCancelCollectFood(Integer foodId, Integer userId) throws ApiServiceException;

    /**
     * 获取用户收藏食物分页
     *
     * @param userId 用户id
     * @param reqDTO 分页信息
     * @return 收藏食物
     * @throws ApiServiceException
     */
    List<Map<String, Object>> getUserCollectFoodsPage(Integer userId, PageReqDTO reqDTO) throws ApiServiceException;
}
