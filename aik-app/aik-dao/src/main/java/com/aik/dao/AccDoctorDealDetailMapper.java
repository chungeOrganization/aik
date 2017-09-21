package com.aik.dao;

import com.aik.model.AccDoctorDealDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccDoctorDealDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccDoctorDealDetail record);

    int insertSelective(AccDoctorDealDetail record);

    AccDoctorDealDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccDoctorDealDetail record);

    int updateByPrimaryKey(AccDoctorDealDetail record);

    List<AccDoctorDealDetail> selectByDoctorId(Integer doctorId);

    int selectCountBySelective(AccDoctorDealDetail record);

    BigDecimal selectSumAmountByParams(Map<String, Object> params);

    List<Map<String, Object>> selectDetailsByParams(Map<String, Object> params);
}