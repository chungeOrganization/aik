package com.aik.dao;

import com.aik.model.AccDoctorWithdraw;

public interface AccDoctorWithdrawMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccDoctorWithdraw record);

    int insertSelective(AccDoctorWithdraw record);

    AccDoctorWithdraw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccDoctorWithdraw record);

    int updateByPrimaryKey(AccDoctorWithdraw record);

    AccDoctorWithdraw selectLastBankWithdraw(Integer doctorId);
}