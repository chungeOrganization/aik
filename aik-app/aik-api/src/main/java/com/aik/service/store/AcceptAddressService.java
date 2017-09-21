package com.aik.service.store;

import com.aik.exception.ApiServiceException;
import com.aik.model.StoAcceptAddress;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/5.
 */
public interface AcceptAddressService {

    /**
     * 获取用户收货地址
     *
     * @param userId 用户id
     * @return 收货地址列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getUserAcceptAddresses(Integer userId) throws ApiServiceException;

    /**
     * 修改用户收货地址
     *
     * @param acceptAddress 收货地址
     * @throws ApiServiceException Api服务异常
     */
    void updateAcceptAddress(StoAcceptAddress acceptAddress) throws ApiServiceException;

    /**
     * 新增用户收货地址
     *
     * @param acceptAddress 收货地址
     * @throws ApiServiceException Api服务异常
     */
    void addAcceptAddress(StoAcceptAddress acceptAddress) throws ApiServiceException;

    /**
     * 删除用户收货地址
     *
     * @param acceptAddressId 收货地址id
     * @throws ApiServiceException Api服务异常
     */
    void delAcceptAddress(Integer acceptAddressId) throws ApiServiceException;
}
