package com.aik.dao;

import com.aik.model.AccDoctorBankCard;

import java.util.List;
import java.util.Map;

public interface AccDoctorBankCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccDoctorBankCard record);

    int insertSelective(AccDoctorBankCard record);

    AccDoctorBankCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccDoctorBankCard record);

    int updateByPrimaryKey(AccDoctorBankCard record);

    List<AccDoctorBankCard> selectByDoctorId(Integer doctorId);

    List<Map<String, Object>> selectDoctorBankCards(Integer doctorId);
}