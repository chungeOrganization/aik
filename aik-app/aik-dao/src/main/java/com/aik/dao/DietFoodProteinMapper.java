package com.aik.dao;

import com.aik.model.DietFoodProtein;

public interface DietFoodProteinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietFoodProtein record);

    int insertSelective(DietFoodProtein record);

    DietFoodProtein selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietFoodProtein record);

    int updateByPrimaryKey(DietFoodProtein record);

    DietFoodProtein selectByFoodId(Integer foodId);
}