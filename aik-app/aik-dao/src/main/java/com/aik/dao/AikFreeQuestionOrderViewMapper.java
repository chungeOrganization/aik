package com.aik.dao;

import com.aik.model.AikFreeQuestionOrderView;
import org.apache.ibatis.annotations.Param;

public interface AikFreeQuestionOrderViewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikFreeQuestionOrderView record);

    int insertSelective(AikFreeQuestionOrderView record);

    AikFreeQuestionOrderView selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikFreeQuestionOrderView record);

    int updateByPrimaryKey(AikFreeQuestionOrderView record);

    int selectCountBySelective(AikFreeQuestionOrderView record);
}