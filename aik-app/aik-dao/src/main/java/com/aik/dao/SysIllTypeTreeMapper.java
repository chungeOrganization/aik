package com.aik.dao;

import com.aik.model.SysIllTypeTree;

import java.util.List;
import java.util.Map;

public interface SysIllTypeTreeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysIllTypeTree record);

    int insertSelective(SysIllTypeTree record);

    SysIllTypeTree selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysIllTypeTree record);

    int updateByPrimaryKey(SysIllTypeTree record);

    List<SysIllTypeTree> selectByParentId(Integer parentId);
}