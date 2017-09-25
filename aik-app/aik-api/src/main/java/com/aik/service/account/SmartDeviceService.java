package com.aik.service.account;

import com.aik.dto.UserSmartDeviceDTO;
import com.aik.exception.ApiServiceException;

/**
 * Create by as on 2017/9/25
 */
public interface SmartDeviceService {

    /**
     * 获取用户智能设备
     *
     * @param userId 用户id
     * @return 用户智能设备DTO
     * @throws ApiServiceException Api服务异常
     */
    UserSmartDeviceDTO getUserSmartDevice(Integer userId) throws ApiServiceException;
}
