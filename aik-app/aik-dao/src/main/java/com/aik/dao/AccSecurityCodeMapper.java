package com.aik.dao;

import com.aik.model.AccSecurityCode;
import org.apache.ibatis.annotations.Param;

public interface AccSecurityCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccSecurityCode record);

    int insertSelective(AccSecurityCode record);

    AccSecurityCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccSecurityCode record);

    int updateByPrimaryKey(AccSecurityCode record);

    AccSecurityCode selectValidSecurityCode(@Param("mobileNo") String mobileNo, @Param("codeType") Byte codeType);
}