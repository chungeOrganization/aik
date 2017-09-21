package com.aik.dao;

import com.aik.model.StoUserOrder;

import java.util.List;
import java.util.Map;

public interface StoUserOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoUserOrder record);

    int insertSelective(StoUserOrder record);

    StoUserOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoUserOrder record);

    int updateByPrimaryKey(StoUserOrder record);

    List<StoUserOrder> selectByParams(Map<String, Object> params);
}