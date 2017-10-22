package com.aik.dao;

import com.aik.model.StoLogisticsTrackInfo;

import java.util.List;

public interface StoLogisticsTrackInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoLogisticsTrackInfo record);

    int insertSelective(StoLogisticsTrackInfo record);

    StoLogisticsTrackInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoLogisticsTrackInfo record);

    int updateByPrimaryKey(StoLogisticsTrackInfo record);

    List<StoLogisticsTrackInfo> selectByOrderId(Integer orderId);
}