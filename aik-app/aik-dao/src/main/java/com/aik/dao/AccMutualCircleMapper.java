package com.aik.dao;

import com.aik.model.AccCircleLike;
import com.aik.model.AccMutualCircle;
import com.aik.vo.AccCircleLikeVo;
import com.aik.vo.AccMutualCircleVo;
import com.aik.vo.AikHealthRecordVo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface AccMutualCircleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccMutualCircle record);

    int insertSelective(AccMutualCircle record);

    AccMutualCircle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccMutualCircle record);

    int updateByPrimaryKey(AccMutualCircle record);

    List<Map<String, Object>> selectByParams(Map<String, Object> params);

    /**
     * 获取所有数据
     * @return
     */
    List<AccMutualCircleVo> findAll(AccMutualCircle record);
    
    /**
     * 分页查询数据
     * @return
     */
    Page<AccMutualCircleVo> findByPage(AccMutualCircleVo record);

    AccMutualCircle selectUserLastCircle(Integer userId);
}