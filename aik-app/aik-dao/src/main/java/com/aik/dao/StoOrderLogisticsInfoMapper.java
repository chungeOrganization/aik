package com.aik.dao;

import com.aik.model.StoOrderLogisticsInfo;

public interface StoOrderLogisticsInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoOrderLogisticsInfo record);

    int insertSelective(StoOrderLogisticsInfo record);

    StoOrderLogisticsInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoOrderLogisticsInfo record);

    int updateByPrimaryKey(StoOrderLogisticsInfo record);

    StoOrderLogisticsInfo selectByOrderId(Integer orderId);
}