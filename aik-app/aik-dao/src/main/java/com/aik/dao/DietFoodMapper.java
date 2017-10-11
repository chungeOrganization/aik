package com.aik.dao;

import com.aik.model.DietFood;
import com.aik.vo.DietFoodVo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface DietFoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietFood record);

    int insertSelective(DietFood record);

    DietFood selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietFood record);

    int updateByPrimaryKey(DietFood record);

    List<Map<String, Object>> selectByParams(Map<String, Object> params);
    
    
    /**
     * 获取所有数据
     * @return
     */
    List<DietFood> findAll(DietFood record);

    /**
     * 分页查询数据
     * @return
     */
    Page<DietFoodVo> findByPage(DietFood record);
}