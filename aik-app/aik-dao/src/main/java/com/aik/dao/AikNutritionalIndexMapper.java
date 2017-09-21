package com.aik.dao;

import com.aik.model.AikNutritionalIndex;

public interface AikNutritionalIndexMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikNutritionalIndex record);

    int insertSelective(AikNutritionalIndex record);

    AikNutritionalIndex selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikNutritionalIndex record);

    int updateByPrimaryKey(AikNutritionalIndex record);

    AikNutritionalIndex selectByUserId(Integer userId);
}