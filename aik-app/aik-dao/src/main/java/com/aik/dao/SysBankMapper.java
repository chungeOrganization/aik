package com.aik.dao;

import com.aik.model.SysBank;

import java.util.List;

public interface SysBankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysBank record);

    int insertSelective(SysBank record);

    SysBank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysBank record);

    int updateByPrimaryKey(SysBank record);

    List<SysBank> selectAllBank();
}