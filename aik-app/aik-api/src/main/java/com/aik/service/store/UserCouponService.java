package com.aik.service.store;

import com.aik.exception.ApiServiceException;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/5.
 */
public interface UserCouponService {

    /**
     * 获取用户优惠券列表
     *
     * @param params 参数
     * @return 用户优惠券列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getUserCoupons(Map<String, Object> params) throws ApiServiceException;
}
