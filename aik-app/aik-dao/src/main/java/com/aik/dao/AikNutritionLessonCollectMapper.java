package com.aik.dao;

import com.aik.model.AikNutritionLessonCollect;
import org.apache.ibatis.annotations.Param;

public interface AikNutritionLessonCollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikNutritionLessonCollect record);

    int insertSelective(AikNutritionLessonCollect record);

    AikNutritionLessonCollect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikNutritionLessonCollect record);

    int updateByPrimaryKey(AikNutritionLessonCollect record);

    AikNutritionLessonCollect selectByLessonIdAndUserId(@Param("userId") Integer userId, @Param("lessonId") Integer lessonId);
}