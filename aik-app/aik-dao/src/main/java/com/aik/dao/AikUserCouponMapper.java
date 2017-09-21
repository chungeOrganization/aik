package com.aik.dao;

import com.aik.model.AikUserCoupon;

import java.util.List;
import java.util.Map;

public interface AikUserCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikUserCoupon record);

    int insertSelective(AikUserCoupon record);

    AikUserCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikUserCoupon record);

    int updateByPrimaryKey(AikUserCoupon record);

    List<Map<String, Object>> selectByParams(Map<String, Object> params);
}