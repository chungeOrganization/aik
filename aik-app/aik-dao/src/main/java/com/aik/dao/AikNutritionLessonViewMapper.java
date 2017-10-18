package com.aik.dao;

import java.util.List;

import com.aik.model.AikNutritionLessonView;
import com.aik.vo.AikNutritionLessonViewVo;
import com.github.pagehelper.Page;

public interface AikNutritionLessonViewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikNutritionLessonView record);

    int insertSelective(AikNutritionLessonView record);

    AikNutritionLessonView selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikNutritionLessonView record);

    int updateByPrimaryKey(AikNutritionLessonView record);

    int selectCountBySelective(AikNutritionLessonView record);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AikNutritionLessonView> findAll(AikNutritionLessonView record);

    /**
     * 分页查询数据
     * @return
     */
    Page<AikNutritionLessonViewVo> findByPage(AikNutritionLessonViewVo record);
}