package com.aik.dao;

import java.util.List;

import com.aik.model.AikHrBloodSugar;
import com.aik.model.AikHrRoutineBlood;

public interface AikHrRoutineBloodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikHrRoutineBlood record);

    int insertSelective(AikHrRoutineBlood record);

    AikHrRoutineBlood selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikHrRoutineBlood record);

    int updateByPrimaryKey(AikHrRoutineBlood record);

    AikHrRoutineBlood selectByHRid(Integer hrId);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AikHrRoutineBlood> findAll(AikHrRoutineBlood record);
}