package com.aik.service;

import com.aik.model.DietUserCollectFood;
import com.aik.util.PageUtils;
import com.aik.vo.DietUserCollectFoodVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface FoodCollectManageService {

    
    
    /**
     * 根据主键删除食物收藏信息
     * @param id 食物id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增食物收藏信息
     *
     * @param dietUserCollectFood 食物收藏信息
     * @throws Exception 异常
     */
    void save(DietUserCollectFood dietUserCollectFood) throws Exception;
    
    /**
     * 修改食物收藏信息
     *
     * @param dietUserCollectFood 食物收藏信息
     * @throws Exception 异常
     */
    void update(DietUserCollectFood dietUserCollectFood) throws  Exception;
    
    /**
     * 查询食物收藏信息根据主键
     *
     * @param id 食物id
     * @throws Exception 异常
     */
    DietUserCollectFood findById(Integer id) throws  Exception;
    
    /**
     * 查询所有食物收藏信息
     * @param dietFood
     * @return
     * @throws Exception
     */
    List<DietUserCollectFood> findAll(DietUserCollectFood dietUserCollectFood) throws Exception;
    
    /**
     * 食物收藏信息分页查询
     */
    Page<DietUserCollectFoodVo> findPage(DietUserCollectFoodVo dietUserCollectFoodVo, PageUtils pageUtils) throws  Exception;
    
    
    
}
