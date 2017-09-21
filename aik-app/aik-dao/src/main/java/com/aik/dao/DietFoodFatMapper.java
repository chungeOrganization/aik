package com.aik.dao;

import com.aik.model.DietFoodFat;

public interface DietFoodFatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietFoodFat record);

    int insertSelective(DietFoodFat record);

    DietFoodFat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietFoodFat record);

    int updateByPrimaryKey(DietFoodFat record);

    DietFoodFat selectByFoodId(Integer foodId);
}