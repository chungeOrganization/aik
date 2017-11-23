package com.aik.dao;

import com.aik.model.AikNutritionLesson;
import com.aik.model.DietFoodCategory;
import com.github.pagehelper.Page;

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
    /**
    * 获取所有数据
    * @return
    */
   List<AikNutritionLesson> findAll(AikNutritionLesson record);

   /**
    * 分页查询数据
    * @return
    */
   Page<AikNutritionLesson> findByPage(AikNutritionLesson record);

   List<AikNutritionLesson> selectUserCollect(Map<String, Object> params);
}