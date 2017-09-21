package com.aik.dao;

import com.aik.model.AikHrBloodSugar;

public interface AikHrBloodSugarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikHrBloodSugar record);

    int insertSelective(AikHrBloodSugar record);

    AikHrBloodSugar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikHrBloodSugar record);

    int updateByPrimaryKey(AikHrBloodSugar record);

    AikHrBloodSugar selectByHRid(Integer hrId);
}