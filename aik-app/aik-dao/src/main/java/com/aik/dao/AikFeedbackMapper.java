package com.aik.dao;

import com.aik.model.AikFeedback;
import com.github.pagehelper.Page;

public interface AikFeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikFeedback record);

    int insertSelective(AikFeedback record);

    AikFeedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikFeedback record);

    int updateByPrimaryKey(AikFeedback record);

    Page<AikFeedback> selectByPage(AikFeedback record);
}