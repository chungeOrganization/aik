package com.aik.dao;

import com.aik.model.SysHospitalTree;

import java.util.List;

public interface SysHospitalTreeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysHospitalTree record);

    int insertSelective(SysHospitalTree record);

    SysHospitalTree selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysHospitalTree record);

    int updateByPrimaryKey(SysHospitalTree record);

    List<SysHospitalTree> selectByAreaId(Integer areaId);

    List<SysHospitalTree> selectByParent(Integer parent);

    SysHospitalTree selectByName(String name);
}