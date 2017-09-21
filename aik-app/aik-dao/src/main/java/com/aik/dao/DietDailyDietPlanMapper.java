package com.aik.dao;

import com.aik.model.DietDailyDietPlan;

import java.util.List;
import java.util.Map;

public interface DietDailyDietPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietDailyDietPlan record);

    int insertSelective(DietDailyDietPlan record);

    DietDailyDietPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietDailyDietPlan record);

    int updateByPrimaryKey(DietDailyDietPlan record);

    List<DietDailyDietPlan> selectBySelective(DietDailyDietPlan record);

    List<Map<String, Object>> selectUserDietPlan(Map<String, Object> params);
}