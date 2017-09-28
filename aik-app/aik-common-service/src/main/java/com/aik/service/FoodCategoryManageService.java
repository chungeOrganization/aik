package com.aik.service;

import com.aik.model.DietFoodCategory;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface FoodCategoryManageService {

    
    
    /**
     * 根据主键删除食物分类信息
     * @param id 食物id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增食物分类
     *
     * @param dietFood 食物分类信息
     * @throws Exception 异常
     */
    void save(DietFoodCategory dietFoodCategory) throws Exception;
    
    /**
     * 修改食物分类信息
     *
     * @param dietFood 食物分类信息
     * @throws Exception 异常
     */
    void update(DietFoodCategory dietFoodCategory) throws  Exception;
    
    /**
     * 查询食物分类信息根据主键
     *
     * @param id 食物id
     * @throws Exception 异常
     */
    DietFoodCategory findById(Integer id) throws  Exception;
    
    /**
     * 查询所有食物分类信息
     * @param dietFood
     * @return
     * @throws Exception
     */
    List<DietFoodCategory> findAll(DietFoodCategory dietFoodCategory) throws Exception;
    
    /**
     * 食物分类信息分页查询
     */
    Page<DietFoodCategory> findPage(DietFoodCategory dietFoodCategory, PageUtils pageUtils) throws  Exception;
    
    
    
}
