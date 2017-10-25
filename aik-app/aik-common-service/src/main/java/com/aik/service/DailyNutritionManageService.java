package com.aik.service;

import com.aik.model.DietDailyDietPlan;
import com.aik.model.DietDailyNutrition;
import com.aik.util.PageUtils;
import com.aik.vo.DietDailyDietPlanVo;
import com.aik.vo.DietDailyNutritionVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface DailyNutritionManageService {

    
    
    /**
     * 根据主键删除营养元素摄于量对比分析信息
     * @param id 食物id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增营养元素摄于量对比分析信息
     *
     * @param dietDailyNutrition 营养元素摄于量对比分析信息
     * @throws Exception 异常
     */
    void save(DietDailyNutrition dietDailyNutrition) throws Exception;
    
    /**
     * 修改营养元素摄于量对比分析信息
     *
     * @param dietDailyDietPlan 营养元素摄于量对比分析信息
     * @throws Exception 异常
     */
    void update(DietDailyNutrition dietDailyNutrition) throws  Exception;
    
    /**
     * 查询营养元素摄于量对比分析信息根据主键
     *
     * @param id 记录id
     * @throws Exception 异常
     */
    DietDailyNutrition findById(Integer id) throws  Exception;
    
    /**
     * 查询所有营养元素摄于量对比分析信息
     * @param dietFood
     * @return
     * @throws Exception
     */
    List<DietDailyNutrition> findAll(DietDailyNutrition dietDailyNutrition) throws Exception;
    
    /**
     * 营养元素摄于量对比分析信息分页查询
     */
    Page<DietDailyNutritionVo> findPage(DietDailyNutritionVo dietDailyNutritionVo, PageUtils pageUtils) throws  Exception;
    
    
    
}
