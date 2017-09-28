package com.aik.dao;

import com.aik.model.DietFood;
import com.aik.model.DietFoodCategory;
import com.github.pagehelper.Page;

import java.util.List;

public interface DietFoodCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietFoodCategory record);

    int insertSelective(DietFoodCategory record);

    DietFoodCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietFoodCategory record);

    int updateByPrimaryKey(DietFoodCategory record);

    List<DietFoodCategory> selectAllFoodCategory();/**
     * 获取所有数据
     * @return
     */
    List<DietFoodCategory> findAll(DietFoodCategory record);

    /**
     * 分页查询数据
     * @return
     */
    Page<DietFoodCategory> findByPage(DietFoodCategory record);
    
    
}