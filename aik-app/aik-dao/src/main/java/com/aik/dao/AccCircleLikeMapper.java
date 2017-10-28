package com.aik.dao;

import com.aik.model.AccCircleLike;
import com.aik.model.AikHrBloodSugar;
import com.aik.vo.AccCircleLikeVo;

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
    
    /**
     * 获取所有数据
     * @return
     */
    List<AccCircleLikeVo> findAll(AccCircleLike record);
}