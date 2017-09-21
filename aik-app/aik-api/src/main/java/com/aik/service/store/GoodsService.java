package com.aik.service.store;

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
}
