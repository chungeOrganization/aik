package com.aik.dao;

import com.aik.model.AikDoctorTips;

import java.util.List;
import java.util.Map;

public interface AikDoctorTipsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikDoctorTips record);

    int insertSelective(AikDoctorTips record);

    AikDoctorTips selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikDoctorTips record);

    int updateByPrimaryKey(AikDoctorTips record);

    List<AikDoctorTips> selectBySelective(AikDoctorTips record);

    List<Map<String, Object>> selectQuestionTipsByDoctorId(Map<String, Object> params);

    List<Map<String, Object>> selectHomeDoctorTips(Integer doctorId);

    int selectCountBySelective(AikDoctorTips record);

    int clearOrderTips(Integer orderId);
}