package com.aik.dao;

import com.aik.model.DietFoodCategory;

import java.util.List;

public interface DietFoodCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietFoodCategory record);

    int insertSelective(DietFoodCategory record);

    DietFoodCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietFoodCategory record);

    int updateByPrimaryKey(DietFoodCategory record);

    List<DietFoodCategory> selectAllFoodCategory();
}