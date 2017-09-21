package com.aik.dao;

import com.aik.model.AccUserAttention;

import java.util.List;
import java.util.Map;

public interface AccUserAttentionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccUserAttention record);

    int insertSelective(AccUserAttention record);

    AccUserAttention selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccUserAttention record);

    int updateByPrimaryKey(AccUserAttention record);

    List<AccUserAttention> selectBySelective(AccUserAttention record);

    int selectCountBySelective(AccUserAttention record);

    List<Map<String, Object>> selectDoctorFansPage(Map<String, Object> params);

    List<AccUserAttention> selectAttentionsPage(Map<String, Object> params);
}