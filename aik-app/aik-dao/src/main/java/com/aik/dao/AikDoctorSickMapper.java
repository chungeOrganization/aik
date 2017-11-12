package com.aik.dao;

import com.aik.model.AikDoctorSick;
import org.apache.ibatis.annotations.Param;

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

    AikDoctorSick selectByDoctorIdAndUserId(@Param("doctorId") Integer doctorId, @Param("userId") Integer userId);

    int clearDoctorSickGroup(@Param("doctorId") Integer doctorId, @Param("groupId") Integer groupId);
}