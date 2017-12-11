package com.aik.dao;

import com.aik.model.DietPlanTemplate;

import java.util.List;

public interface DietPlanTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietPlanTemplate record);

    int insertSelective(DietPlanTemplate record);

    DietPlanTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietPlanTemplate record);

    int updateByPrimaryKey(DietPlanTemplate record);

    List<Integer> getTemplateIds();

    List<DietPlanTemplate> selectByTemplateId(Integer templateId);
}