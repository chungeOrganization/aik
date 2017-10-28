package com.aik.dao;

import com.aik.model.AccDoctorAttention;
import org.apache.ibatis.annotations.Param;

public interface AccDoctorAttentionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccDoctorAttention record);

    int insertSelective(AccDoctorAttention record);

    AccDoctorAttention selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccDoctorAttention record);

    int updateByPrimaryKey(AccDoctorAttention record);

    int selectCountBySelective(AccDoctorAttention record);

    AccDoctorAttention selectByDoctorIdAndUserId(@Param("doctorId") Integer doctorId,@Param("userId") Integer userId);
}