package com.aik.dao;

import com.aik.model.AikNutritionLesson;

import java.util.List;
import java.util.Map;

public interface AikNutritionLessonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikNutritionLesson record);

    int insertSelective(AikNutritionLesson record);

    AikNutritionLesson selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikNutritionLesson record);

    int updateByPrimaryKey(AikNutritionLesson record);

    List<AikNutritionLesson> selectByParams(Map<String, Object> params);
}