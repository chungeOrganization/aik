package com.aik.dao;

import com.aik.model.StoAcceptAddress;

import java.util.List;
import java.util.Map;

public interface StoAcceptAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoAcceptAddress record);

    int insertSelective(StoAcceptAddress record);

    StoAcceptAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoAcceptAddress record);

    int updateByPrimaryKey(StoAcceptAddress record);

    StoAcceptAddress selectUserDefaultAddress(Integer userId);

    List<Map<String, Object>> selectUserAcceptAddresses(Integer userId);

    int clearUserDefaultAddress(Integer userId);
}