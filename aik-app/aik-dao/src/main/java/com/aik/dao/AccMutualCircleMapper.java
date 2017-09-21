package com.aik.dao;

import com.aik.model.AccMutualCircle;

import java.util.List;
import java.util.Map;

public interface AccMutualCircleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccMutualCircle record);

    int insertSelective(AccMutualCircle record);

    AccMutualCircle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccMutualCircle record);

    int updateByPrimaryKey(AccMutualCircle record);

    List<Map<String, Object>> selectByParams(Map<String, Object> params);
}