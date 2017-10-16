package com.aik.dao;

import com.aik.model.AikCommonProblem;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface AikCommonProblemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikCommonProblem record);

    int insertSelective(AikCommonProblem record);

    AikCommonProblem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikCommonProblem record);

    int updateByPrimaryKey(AikCommonProblem record);

    List<Map<String, Object>> selectCommonProblemsByParams(Map<String, Object> params);

    Page<AikCommonProblem> selectByPage(AikCommonProblem record);
}