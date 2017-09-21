package com.aik.dao;

import com.aik.model.StoUserOrderDetail;

import java.util.List;
import java.util.Map;

public interface StoUserOrderDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoUserOrderDetail record);

    int insertSelective(StoUserOrderDetail record);

    StoUserOrderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoUserOrderDetail record);

    int updateByPrimaryKey(StoUserOrderDetail record);

    List<Map<String, Object>> selectDetailsBySelective(StoUserOrderDetail record);

    int selectGoodsCountBySelective(StoUserOrderDetail record);
}