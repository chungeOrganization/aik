package com.aik.service.store;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.StoAcceptAddressMapper;
import com.aik.enums.IsDefaultEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.StoAcceptAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/5.
 */
@Service
public class AcceptAddressServiceImpl implements AcceptAddressService {

    private static final Logger logger = LoggerFactory.getLogger(AcceptAddressServiceImpl.class);

    private StoAcceptAddressMapper stoAcceptAddressMapper;

    @Autowired
    public void setStoAcceptAddressMapper(StoAcceptAddressMapper stoAcceptAddressMapper) {
        this.stoAcceptAddressMapper = stoAcceptAddressMapper;
    }

    @Override
    public List<Map<String, Object>> getUserAcceptAddresses(Integer userId) throws ApiServiceException {
        if (null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        return stoAcceptAddressMapper.selectUserAcceptAddresses(userId);
    }

    @Override
    public void updateAcceptAddress(StoAcceptAddress acceptAddress) throws ApiServiceException {
        if (null == acceptAddress || null == acceptAddress.getId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (acceptAddress.getIsDefault() == IsDefaultEnum.DEFAULT.getCode()) {
            stoAcceptAddressMapper.clearUserDefaultAddress(acceptAddress.getUserId());
        }

        acceptAddress.setUpdateDate(new Date());
        stoAcceptAddressMapper.updateByPrimaryKeySelective(acceptAddress);
    }

    @Override
    public void addAcceptAddress(StoAcceptAddress acceptAddress) throws ApiServiceException {
        if (null == acceptAddress) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        acceptAddress.setCreateDate(new Date());

        stoAcceptAddressMapper.insertSelective(acceptAddress);
    }

    @Override
    public void delAcceptAddress(Integer acceptAddressId) throws ApiServiceException {
        if (null == acceptAddressId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        stoAcceptAddressMapper.deleteByPrimaryKey(acceptAddressId);
    }
}
