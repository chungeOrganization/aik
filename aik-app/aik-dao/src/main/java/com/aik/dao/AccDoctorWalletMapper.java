package com.aik.dao;

import com.aik.model.AccDoctorWallet;

public interface AccDoctorWalletMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccDoctorWallet record);

    int insertSelective(AccDoctorWallet record);

    AccDoctorWallet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccDoctorWallet record);

    int updateByPrimaryKey(AccDoctorWallet record);
}