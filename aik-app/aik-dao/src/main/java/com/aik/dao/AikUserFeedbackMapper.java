package com.aik.dao;

import com.aik.model.AikUserFeedback;

public interface AikUserFeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikUserFeedback record);

    int insertSelective(AikUserFeedback record);

    AikUserFeedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikUserFeedback record);

    int updateByPrimaryKey(AikUserFeedback record);
}