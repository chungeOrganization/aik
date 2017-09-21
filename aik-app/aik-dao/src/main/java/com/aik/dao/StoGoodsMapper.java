package com.aik.dao;

import com.aik.model.StoGoods;

import java.util.List;
import java.util.Map;

public interface StoGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoGoods record);

    int insertSelective(StoGoods record);

    StoGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoGoods record);

    int updateByPrimaryKey(StoGoods record);

    List<Map<String, Object>> selectGoodsBySelective(StoGoods record);

    List<Map<String, Object>> selectByParams(Map<String, Object> params);
}