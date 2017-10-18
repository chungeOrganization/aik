package com.aik.service;

import com.aik.model.AikNutritionLesson;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface ArticleManageService {

    
    
    /**
     * 根据主键删除文章
     * @param id 食物id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增食物分类
     *
     * @param aikNutritionLesson 文章
     * @throws Exception 异常
     */
    void save(AikNutritionLesson aikNutritionLesson) throws Exception;
    
    /**
     * 修改文章
     *
     * @param aikNutritionLesson 文章
     * @throws Exception 异常
     */
    void update(AikNutritionLesson aikNutritionLesson) throws  Exception;
    
    /**
     * 查询文章根据主键
     *
     * @param id 食物id
     * @throws Exception 异常
     */
    AikNutritionLesson findById(Integer id) throws  Exception;
    
    /**
     * 查询所有文章
     * @param aikNutritionLesson
     * @return
     * @throws Exception
     */
    List<AikNutritionLesson> findAll(AikNutritionLesson aikNutritionLesson) throws Exception;
    
    /**
     * 文章分页查询
     */
    Page<AikNutritionLesson> findPage(AikNutritionLesson aikNutritionLesson, PageUtils pageUtils) throws  Exception;
    
    
    
}
