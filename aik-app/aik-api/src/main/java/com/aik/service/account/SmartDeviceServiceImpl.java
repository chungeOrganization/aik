package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.UserSmartDeviceDTO;
import com.aik.exception.ApiServiceException;
import com.aik.resource.SystemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Create by as on 2017/9/25
 */
@Service
public class SmartDeviceServiceImpl implements SmartDeviceService {

    private static final Logger logger = LoggerFactory.getLogger(SmartDeviceServiceImpl.class);

    @Resource
    private SystemResource systemResource;

    @Override
    public UserSmartDeviceDTO getUserSmartDevice(Integer userId) throws ApiServiceException {
        if (null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        UserSmartDeviceDTO userSmartDeviceDTO = new UserSmartDeviceDTO();

        userSmartDeviceDTO.setImage(systemResource.getApiFileUri() + "system/smartDevice.png");
        userSmartDeviceDTO.setName("智能体脂秤");
        userSmartDeviceDTO.setStatus((byte) 0);// 0：未绑定 1：已绑定

        return userSmartDeviceDTO;
    }
}
