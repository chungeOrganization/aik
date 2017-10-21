package com.aik.service;

import com.aik.model.AikNutritionalIndex;
import com.aik.model.DietUserCollectFood;
import com.aik.util.PageUtils;
import com.aik.vo.AikNutritionalIndexVo;
import com.aik.vo.DietUserCollectFoodVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface NutritionalIndexManageService {

    
    
    /**
     * 根据主键删除BMI
     * @param id 食物id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增BMI
     *
     * @param dietUserCollectFood BMI
     * @throws Exception 异常
     */
    void save(AikNutritionalIndex aikNutritionalIndex) throws Exception;
    
    /**
     * 修改BMI
     *
     * @param dietUserCollectFood BMI
     * @throws Exception 异常
     */
    void update(AikNutritionalIndex aikNutritionalIndex) throws  Exception;
    
    /**
     * 查询BMI根据主键
     *
     * @param id 食物id
     * @throws Exception 异常
     */
    AikNutritionalIndex findById(Integer id) throws  Exception;
    
    /**
     * 查询所有BMI
     * @param dietFood
     * @return
     * @throws Exception
     */
    List<AikNutritionalIndex> findAll(AikNutritionalIndex aikNutritionalIndex) throws Exception;
    
    /**
     * BMI分页查询
     */
    Page<AikNutritionalIndexVo> findPage(AikNutritionalIndexVo aikNutritionalIndexVo, PageUtils pageUtils) throws  Exception;
    
    
    
}
