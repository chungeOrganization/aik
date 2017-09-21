package com.aik.dao;

import com.aik.model.AikDoctorFeedback;

public interface AikDoctorFeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikDoctorFeedback record);

    int insertSelective(AikDoctorFeedback record);

    AikDoctorFeedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikDoctorFeedback record);

    int updateByPrimaryKey(AikDoctorFeedback record);
}