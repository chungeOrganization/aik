package com.aik.dao;

import com.aik.model.SysAreaTree;

import java.util.List;

public interface SysAreaTreeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAreaTree record);

    int insertSelective(SysAreaTree record);

    SysAreaTree selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAreaTree record);

    int updateByPrimaryKey(SysAreaTree record);

    List<SysAreaTree> selectByParent(Integer parent);

    SysAreaTree selectByName(String name);
}