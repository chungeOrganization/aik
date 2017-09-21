package com.aik.dao;

import com.aik.model.AccCircleLike;

import java.util.List;

public interface AccCircleLikeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccCircleLike record);

    int insertSelective(AccCircleLike record);

    AccCircleLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccCircleLike record);

    int updateByPrimaryKey(AccCircleLike record);

    int selectCountBySelective(AccCircleLike record);

    List<AccCircleLike> selectBySelective(AccCircleLike record);
}