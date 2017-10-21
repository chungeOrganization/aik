package com.aik.dao;

import com.aik.model.AikHrRoutineBlood;
import com.aik.model.AikHrTumorMarkers;

import java.util.List;
import java.util.Map;

public interface AikHrTumorMarkersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikHrTumorMarkers record);

    int insertSelective(AikHrTumorMarkers record);

    AikHrTumorMarkers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikHrTumorMarkers record);

    int updateByPrimaryKey(AikHrTumorMarkers record);

    AikHrTumorMarkers selectByHRid(Integer hrId);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AikHrTumorMarkers> findAll(AikHrTumorMarkers record);
}