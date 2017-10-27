package com.aik.dao;

import java.util.List;

import com.aik.model.AccCircleComment;
import com.aik.model.AikHrBloodSugar;
import com.aik.vo.AccCircleCommentVo;

public interface AccCircleCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccCircleComment record);

    int insertSelective(AccCircleComment record);

    AccCircleComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccCircleComment record);

    int updateByPrimaryKey(AccCircleComment record);

    int selectCountBySelective(AccCircleComment record);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AccCircleCommentVo> findAll(AccCircleComment record);
}