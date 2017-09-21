package com.aik.dao;

import com.aik.model.AikNutritionLessonView;

public interface AikNutritionLessonViewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikNutritionLessonView record);

    int insertSelective(AikNutritionLessonView record);

    AikNutritionLessonView selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikNutritionLessonView record);

    int updateByPrimaryKey(AikNutritionLessonView record);

    int selectCountBySelective(AikNutritionLessonView record);
}