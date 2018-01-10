package com.aik.dao;

import com.aik.model.DietCustomFood;

public interface DietCustomFoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietCustomFood record);

    int insertSelective(DietCustomFood record);

    DietCustomFood selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietCustomFood record);

    int updateByPrimaryKey(DietCustomFood record);
}