package com.aik.dao;

import com.aik.model.AccUserAccount;
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
}