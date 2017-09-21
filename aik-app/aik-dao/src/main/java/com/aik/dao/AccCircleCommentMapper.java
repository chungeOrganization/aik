package com.aik.dao;

import com.aik.model.AccCircleComment;

public interface AccCircleCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccCircleComment record);

    int insertSelective(AccCircleComment record);

    AccCircleComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccCircleComment record);

    int updateByPrimaryKey(AccCircleComment record);

    int selectCountBySelective(AccCircleComment record);
}