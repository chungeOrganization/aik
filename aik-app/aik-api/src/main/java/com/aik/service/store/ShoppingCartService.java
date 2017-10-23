package com.aik.service.store;

import com.aik.dto.UpdateShoppingCartDTO;
import com.aik.dto.request.user.ShoppingCartAddGoodsReqDTO;
import com.aik.exception.ApiServiceException;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/3.
 */
public interface ShoppingCartService {

    /**
     * 获取用户购物车
     *
     * @param userId 用户id
     * @return 用户购物车
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getUserShoppingCart(Integer userId) throws ApiServiceException;

    /**
     * 修改用户购物车
     *
     * @param updateShoppingCartDTO 购物车DTO
     * @throws ApiServiceException Api服务异常
     */
    void updateShoppingCart(UpdateShoppingCartDTO updateShoppingCartDTO) throws ApiServiceException;

    /**
     * 购物车新增商品
     *
     * @param reqDTO DTO
     * @throws ApiServiceException
     */
    void shoppingCartAddGoods(ShoppingCartAddGoodsReqDTO reqDTO) throws ApiServiceException;
}
