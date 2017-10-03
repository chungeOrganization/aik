package com.aik.service;

import com.aik.model.DietDailyDietPlan;
import com.aik.util.PageUtils;
import com.aik.vo.DietDailyDietPlanVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface DietPlanManageService {

    
    
    /**
     * 根据主键删除饮食计划信息
     * @param id 食物id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增饮食计划信息
     *
     * @param dietDailyDietPlan 饮食计划信息
     * @throws Exception 异常
     */
    void save(DietDailyDietPlan dietDailyDietPlan) throws Exception;
    
    /**
     * 修改饮食计划信息
     *
     * @param dietDailyDietPlan 饮食计划信息
     * @throws Exception 异常
     */
    void update(DietDailyDietPlan dietDailyDietPlan) throws  Exception;
    
    /**
     * 查询饮食计划信息根据主键
     *
     * @param id 记录id
     * @throws Exception 异常
     */
    DietDailyDietPlan findById(Integer id) throws  Exception;
    
    /**
     * 查询所有饮食计划信息
     * @param dietFood
     * @return
     * @throws Exception
     */
    List<DietDailyDietPlan> findAll(DietDailyDietPlan dietDailyDietPlan) throws Exception;
    
    /**
     * 饮食计划信息分页查询
     */
    Page<DietDailyDietPlanVo> findPage(DietDailyDietPlanVo dietDailyDietPlanVo, PageUtils pageUtils) throws  Exception;
    
    
    
}
