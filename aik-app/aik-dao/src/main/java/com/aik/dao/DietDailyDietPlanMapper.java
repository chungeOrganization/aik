package com.aik.dao;

import com.aik.model.DietDailyDietPlan;
import com.aik.model.DietDailyDietRecord;
import com.aik.vo.DietDailyDietPlanVo;
import com.aik.vo.DietDailyDietRecordVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    int deleteUserDietPlan(@Param("userId") Integer userId, @Param("recordDate") Date recordDate);
    
    /**
     * 获取所有数据
     * @return
     */
    List<DietDailyDietPlan> findAll(DietDailyDietPlan record);

    /**
     * 分页查询数据
     * @return
     */
    Page<DietDailyDietPlanVo> findByPage(DietDailyDietPlanVo record);
}