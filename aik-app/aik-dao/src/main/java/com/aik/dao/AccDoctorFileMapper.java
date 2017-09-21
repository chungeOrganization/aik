package com.aik.dao;

import com.aik.model.AccDoctorFile;

public interface AccDoctorFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccDoctorFile record);

    int insertSelective(AccDoctorFile record);

    AccDoctorFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccDoctorFile record);

    int updateByPrimaryKey(AccDoctorFile record);
}