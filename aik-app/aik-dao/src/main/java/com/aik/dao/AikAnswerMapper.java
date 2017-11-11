package com.aik.dao;

import com.aik.model.AikAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AikAnswerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikAnswer record);

    int insertSelective(AikAnswer record);

    AikAnswer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikAnswer record);

    int updateByPrimaryKey(AikAnswer record);

    int selectCountBySelective(AikAnswer record);

    List<Map<String, Object>> selectMyAnswerPage(Map<String, Object> params);

    AikAnswer selectByQuestionId(Integer questionId);

    List<AikAnswer> selectBySelective(AikAnswer record);
}