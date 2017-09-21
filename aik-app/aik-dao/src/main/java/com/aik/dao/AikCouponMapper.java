package com.aik.dao;

import com.aik.model.AikCoupon;

public interface AikCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikCoupon record);

    int insertSelective(AikCoupon record);

    AikCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikCoupon record);

    int updateByPrimaryKey(AikCoupon record);
}