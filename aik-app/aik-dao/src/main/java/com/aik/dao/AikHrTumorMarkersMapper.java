package com.aik.dao;

import com.aik.model.AikHrTumorMarkers;

import java.util.Map;

public interface AikHrTumorMarkersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikHrTumorMarkers record);

    int insertSelective(AikHrTumorMarkers record);

    AikHrTumorMarkers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikHrTumorMarkers record);

    int updateByPrimaryKey(AikHrTumorMarkers record);

    AikHrTumorMarkers selectByHRid(Integer hrId);
}