package com.aik.service;

import com.aik.model.DietFood;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface FoodManageService {

    
    
    /**
     * 根据主键删除食物信息
     * @param id 食物id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增食物信息
     *
     * @param dietFood 食物信息
     * @throws Exception 异常
     */
    void save(DietFood dietFood) throws Exception;
    
    /**
     * 修改食物信息
     *
     * @param dietFood 食物信息
     * @throws Exception 异常
     */
    void update(DietFood dietFood) throws  Exception;
    
    /**
     * 查询食物信息根据主键
     *
     * @param id 食物id
     * @throws Exception 异常
     */
    DietFood findById(Integer id) throws  Exception;
    
    /**
     * 查询所有食物信息
     * @param dietFood
     * @return
     * @throws Exception
     */
    List<DietFood> findAll(DietFood dietFood) throws Exception;
    
    /**
     * 食物信息分页查询
     */
    Page<DietFood> findPage(DietFood dietFood, PageUtils pageUtils) throws  Exception;
    
    
    
}
