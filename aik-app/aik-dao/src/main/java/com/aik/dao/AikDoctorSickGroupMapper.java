package com.aik.dao;

import com.aik.model.AikDoctorSickGroup;

import java.util.List;

public interface AikDoctorSickGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikDoctorSickGroup record);

    int insertSelective(AikDoctorSickGroup record);

    AikDoctorSickGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikDoctorSickGroup record);

    int updateByPrimaryKey(AikDoctorSickGroup record);

    List<AikDoctorSickGroup> selectByDoctorId(Integer doctorId);

    int selectCountBySelective(AikDoctorSickGroup record);
}