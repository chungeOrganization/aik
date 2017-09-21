package com.aik.dao;

import com.aik.model.AikDoctorSick;

import java.util.List;
import java.util.Map;

public interface AikDoctorSickMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikDoctorSick record);

    int insertSelective(AikDoctorSick record);

    AikDoctorSick selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikDoctorSick record);

    int updateByPrimaryKey(AikDoctorSick record);

    List<Map<String, Object>> selectListByParams(Map<String, Object> params);

    Map<String, Object> selectSickDetailBySickId(Integer sickId);
}