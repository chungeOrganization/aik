package com.aik.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aik.model.SysUser;
import com.github.pagehelper.Page;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    SysUser selectByUserName(@Param("userName") String userName);
    
    /**
     * 获取所有数据
     * @return
     */
    List<SysUser> findAll(SysUser record);

    /**
     * 分页查询数据
     * @return
     */
    Page<SysUser> findByPage(SysUser record);
}