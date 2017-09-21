package com.aik.dao;

import com.aik.model.AccInviteCode;

public interface AccInviteCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccInviteCode record);

    int insertSelective(AccInviteCode record);

    AccInviteCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccInviteCode record);

    int updateByPrimaryKey(AccInviteCode record);

    AccInviteCode selectByMobileNo(String mobileNo);
}