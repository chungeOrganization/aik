package com.aik.dao;

import java.util.List;

import com.aik.model.AccDoctorAccount;
import com.aik.model.AikHrBloodSugar;

public interface AikHrBloodSugarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikHrBloodSugar record);

    int insertSelective(AikHrBloodSugar record);

    AikHrBloodSugar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikHrBloodSugar record);

    int updateByPrimaryKey(AikHrBloodSugar record);

    AikHrBloodSugar selectByHRid(Integer hrId);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AikHrBloodSugar> findAll(AikHrBloodSugar record);
}