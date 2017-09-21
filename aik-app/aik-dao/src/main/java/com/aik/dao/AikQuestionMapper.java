package com.aik.dao;

import com.aik.model.AikQuestion;

import java.util.List;

public interface AikQuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikQuestion record);

    int insertSelective(AikQuestion record);

    AikQuestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikQuestion record);

    int updateByPrimaryKey(AikQuestion record);

    AikQuestion selectOriginalQuestionByOrderId(Integer orderId);

    List<AikQuestion> selectBySelective(AikQuestion record);

    AikQuestion selectByAnswerId(Integer answerId);
}