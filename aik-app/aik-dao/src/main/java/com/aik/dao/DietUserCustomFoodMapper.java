package com.aik.dao;

import com.aik.model.DietUserCustomFood;

public interface DietUserCustomFoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietUserCustomFood record);

    int insertSelective(DietUserCustomFood record);

    DietUserCustomFood selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietUserCustomFood record);

    int updateByPrimaryKey(DietUserCustomFood record);
}