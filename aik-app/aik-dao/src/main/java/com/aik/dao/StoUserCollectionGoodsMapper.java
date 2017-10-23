package com.aik.dao;

import com.aik.model.StoUserCollectionGoods;
import org.apache.ibatis.annotations.Param;

public interface StoUserCollectionGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoUserCollectionGoods record);

    int insertSelective(StoUserCollectionGoods record);

    StoUserCollectionGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoUserCollectionGoods record);

    int updateByPrimaryKey(StoUserCollectionGoods record);

    StoUserCollectionGoods selectByUserIdAndGoodsID(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);
}