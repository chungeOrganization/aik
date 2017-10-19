package com.aik.dao;

import com.aik.model.AccUserAccount;
import com.aik.vo.AccUserAccountVo;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccUserAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccUserAccount record);

    int insertSelective(AccUserAccount record);

    AccUserAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccUserAccount record);

    int updateByPrimaryKey(AccUserAccount record);

    List<AccUserAccount> selectBySelective(AccUserAccount record);

    AccUserAccount selectByUserName(@Param("userName") String userName);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AccUserAccount> findAll(AccUserAccount record);

    /**
     * 分页查询数据
     * @return
     */
    Page<AccUserAccount> findByPage(AccUserAccountVo record);
}