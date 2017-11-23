package com.aik.dao;

import com.aik.model.AikExpertsAnswerCollect;

public interface AikExpertsAnswerCollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikExpertsAnswerCollect record);

    int insertSelective(AikExpertsAnswerCollect record);

    AikExpertsAnswerCollect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikExpertsAnswerCollect record);

    int updateByPrimaryKey(AikExpertsAnswerCollect record);
}