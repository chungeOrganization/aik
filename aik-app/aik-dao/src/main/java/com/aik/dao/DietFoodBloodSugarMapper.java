package com.aik.dao;

import com.aik.model.DietFoodBloodSugar;

public interface DietFoodBloodSugarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietFoodBloodSugar record);

    int insertSelective(DietFoodBloodSugar record);

    DietFoodBloodSugar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietFoodBloodSugar record);

    int updateByPrimaryKey(DietFoodBloodSugar record);

    DietFoodBloodSugar selectByFoodId(Integer foodId);
}