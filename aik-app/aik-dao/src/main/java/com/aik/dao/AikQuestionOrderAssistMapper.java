package com.aik.dao;

import com.aik.model.AikQuestionOrderAssist;

public interface AikQuestionOrderAssistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikQuestionOrderAssist record);

    int insertSelective(AikQuestionOrderAssist record);

    AikQuestionOrderAssist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikQuestionOrderAssist record);

    int updateByPrimaryKey(AikQuestionOrderAssist record);
}