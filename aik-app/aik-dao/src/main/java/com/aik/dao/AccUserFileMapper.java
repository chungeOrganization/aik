package com.aik.dao;

import com.aik.model.AccUserFile;

import java.util.List;

public interface AccUserFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccUserFile record);

    int insertSelective(AccUserFile record);

    AccUserFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccUserFile record);

    int updateByPrimaryKey(AccUserFile record);

    List<String> selectFilesBySelective(AccUserFile record);

    int deleteOrderFiles(Integer orderId);
}