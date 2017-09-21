package com.aik.dao;

import com.aik.model.AikFreeQuestionOrder;

import java.util.List;
import java.util.Map;

public interface AikFreeQuestionOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikFreeQuestionOrder record);

    int insertSelective(AikFreeQuestionOrder record);

    AikFreeQuestionOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikFreeQuestionOrder record);

    int updateByPrimaryKey(AikFreeQuestionOrder record);

    List<Map<String, Object>> selectOrdersByParams(Map<String, Object> params);
}