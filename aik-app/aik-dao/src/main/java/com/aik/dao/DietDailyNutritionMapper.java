package com.aik.dao;

import com.aik.model.DietDailyDietPlan;
import com.aik.model.DietDailyNutrition;
import com.aik.vo.DietDailyDietPlanVo;
import com.aik.vo.DietDailyNutritionVo;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DietDailyNutritionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietDailyNutrition record);

    int insertSelective(DietDailyNutrition record);

    DietDailyNutrition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietDailyNutrition record);

    int updateByPrimaryKey(DietDailyNutrition record);

    DietDailyNutrition selectUserNutrition(@Param("userId") Integer userId, @Param("recordDate") Date recordDate);
   
    /**
     * 获取所有数据
     * @return
     */
    List<DietDailyNutrition> findAll(DietDailyNutrition record);
    
    /**
     * 分页查询数据
     * @return
     */
    Page<DietDailyNutritionVo> findByPage(DietDailyNutritionVo record);
}