package com.aik.dao;

import com.aik.model.AikQuestionViewHis;

import java.util.List;
import java.util.Map;

public interface AikQuestionViewHisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikQuestionViewHis record);

    int insertSelective(AikQuestionViewHis record);

    AikQuestionViewHis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikQuestionViewHis record);

    int updateByPrimaryKey(AikQuestionViewHis record);

    int selectCountBySelective(AikQuestionViewHis record);

    List<Map<String, Object>> selectViewHisByParams(Map<String, Object> params);
}