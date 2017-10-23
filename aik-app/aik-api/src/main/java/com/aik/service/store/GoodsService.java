package com.aik.service.store;

import com.aik.dto.response.user.GoodsDetailRespDTO;
import com.aik.exception.ApiServiceException;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public interface GoodsService {

    /**
     * 获取推荐商品
     *
     * @return 推荐商品列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getRecommendGoods() throws ApiServiceException;

    /**
     * 根据商品类型获取商品
     *
     * @param params 参数
     * @return 商品列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getGoodsWithType(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取商品详情
     *
     * @param goodsId 商品id
     * @param userId  用户id
     * @return 商品详情
     * @throws ApiServiceException
     */
    GoodsDetailRespDTO getGoodsDetail(Integer goodsId, Integer userId) throws ApiServiceException;

    /**
     * 收藏商品
     *
     * @param goodsId 商品id
     * @param userId  用户id
     * @throws ApiServiceException
     */
    void collectionGoods(Integer goodsId, Integer userId) throws ApiServiceException;

    /**
     * 用户取消收藏商品
     *
     * @param goodsId 商品id
     * @param userId  用户id
     * @throws ApiServiceException
     */
    void cancelCollectionGoods(Integer goodsId, Integer userId) throws ApiServiceException;
}
