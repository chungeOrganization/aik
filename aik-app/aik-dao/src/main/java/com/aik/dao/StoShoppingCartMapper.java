package com.aik.dao;

import com.aik.model.StoShoppingCart;

import java.util.List;
import java.util.Map;

public interface StoShoppingCartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoShoppingCart record);

    int insertSelective(StoShoppingCart record);

    StoShoppingCart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoShoppingCart record);

    int updateByPrimaryKey(StoShoppingCart record);

    List<Map<String, Object>> selectUserShoppingCart(Integer userId);

    int deleteByPrimaryKeyBatch(List<Integer> ids);

    int selectUserShoppingCartCount(Integer userId);
}