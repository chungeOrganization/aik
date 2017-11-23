package com.aik.dao;

import com.aik.model.AikNutritionLessonCollect;

public interface AikNutritionLessonCollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikNutritionLessonCollect record);

    int insertSelective(AikNutritionLessonCollect record);

    AikNutritionLessonCollect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikNutritionLessonCollect record);

    int updateByPrimaryKey(AikNutritionLessonCollect record);
}