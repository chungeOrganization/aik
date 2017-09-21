package com.aik.dao;

import com.aik.model.DietDailyNutrition;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface DietDailyNutritionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietDailyNutrition record);

    int insertSelective(DietDailyNutrition record);

    DietDailyNutrition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietDailyNutrition record);

    int updateByPrimaryKey(DietDailyNutrition record);

    DietDailyNutrition selectUserNutrition(@Param("userId") Integer userId, @Param("recordDate") Date recordDate);
}