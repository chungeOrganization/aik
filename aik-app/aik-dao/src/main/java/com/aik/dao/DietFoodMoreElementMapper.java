package com.aik.dao;

import com.aik.model.DietFoodMoreElement;

import java.util.List;
import java.util.Map;

public interface DietFoodMoreElementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietFoodMoreElement record);

    int insertSelective(DietFoodMoreElement record);

    DietFoodMoreElement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietFoodMoreElement record);

    int updateByPrimaryKey(DietFoodMoreElement record);

    List<Map<String, Object>> selectFoodMoreElements(Integer foodId);
}