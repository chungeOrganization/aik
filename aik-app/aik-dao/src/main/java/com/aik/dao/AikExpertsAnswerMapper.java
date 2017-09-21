package com.aik.dao;

import com.aik.model.AikExpertsAnswer;

import java.util.List;
import java.util.Map;

public interface AikExpertsAnswerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikExpertsAnswer record);

    int insertSelective(AikExpertsAnswer record);

    AikExpertsAnswer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikExpertsAnswer record);

    int updateByPrimaryKey(AikExpertsAnswer record);

    List<AikExpertsAnswer> selectByParams(Map<String, Object> params);
}