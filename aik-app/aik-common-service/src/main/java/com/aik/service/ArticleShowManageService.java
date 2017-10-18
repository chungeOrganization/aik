package com.aik.service;

import com.aik.model.AikNutritionLesson;
import com.aik.model.AikNutritionLessonView;
import com.aik.util.PageUtils;
import com.aik.vo.AikNutritionLessonViewVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface ArticleShowManageService {

    
    
    /**
     * 根据主键删除文章查看
     * @param id 食物id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增食物分类
     *
     * @param aikNutritionLesson 文章查看
     * @throws Exception 异常
     */
    void save(AikNutritionLessonView aikNutritionLessonView) throws Exception;
    
    /**
     * 修改文章查看
     *
     * @param aikNutritionLesson 文章查看
     * @throws Exception 异常
     */
    void update(AikNutritionLessonView aikNutritionLessonView) throws  Exception;
    
    /**
     * 查询文章查看根据主键
     *
     * @param id 食物id
     * @throws Exception 异常
     */
    AikNutritionLessonView findById(Integer id) throws  Exception;
    
    /**
     * 查询所有文章查看
     * @param aikNutritionLesson
     * @return
     * @throws Exception
     */
    List<AikNutritionLessonView> findAll(AikNutritionLessonView aikNutritionLessonView) throws Exception;
    
    /**
     * 文章查看分页查询
     */
    Page<AikNutritionLessonViewVo> findPage(AikNutritionLessonViewVo aikNutritionLessonViewVo, PageUtils pageUtils) throws  Exception;
    
    
    
}
