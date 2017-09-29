package com.aik.dao;

import com.aik.model.AikFeedbackFile;

public interface AikFeedbackFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikFeedbackFile record);

    int insertSelective(AikFeedbackFile record);

    AikFeedbackFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikFeedbackFile record);

    int updateByPrimaryKey(AikFeedbackFile record);
}