package com.aik.dao;

import com.aik.model.AikHrRoutineBlood;

public interface AikHrRoutineBloodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikHrRoutineBlood record);

    int insertSelective(AikHrRoutineBlood record);

    AikHrRoutineBlood selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikHrRoutineBlood record);

    int updateByPrimaryKey(AikHrRoutineBlood record);

    AikHrRoutineBlood selectByHRid(Integer hrId);
}