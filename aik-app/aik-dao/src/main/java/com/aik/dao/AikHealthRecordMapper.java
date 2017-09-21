package com.aik.dao;

import com.aik.model.AikHealthRecord;

import java.util.List;
import java.util.Map;

public interface AikHealthRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikHealthRecord record);

    int insertSelective(AikHealthRecord record);

    AikHealthRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikHealthRecord record);

    int updateByPrimaryKey(AikHealthRecord record);

    AikHealthRecord selectLastRecordByUserId(Integer userId);

    List<Map<String, Object>> selectByParams(Map<String, Object> params);
}