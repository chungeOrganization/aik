package com.aik.dao;

import com.aik.model.AikExpertsAnswerView;

public interface AikExpertsAnswerViewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikExpertsAnswerView record);

    int insertSelective(AikExpertsAnswerView record);

    AikExpertsAnswerView selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikExpertsAnswerView record);

    int updateByPrimaryKey(AikExpertsAnswerView record);

    int selectCountBySelective(AikExpertsAnswerView record);
}