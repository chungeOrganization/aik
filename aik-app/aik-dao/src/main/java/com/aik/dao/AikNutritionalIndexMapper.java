package com.aik.dao;

import java.util.List;

import com.aik.model.AikNutritionalIndex;
import com.aik.vo.AikNutritionalIndexVo;
import com.github.pagehelper.Page;

public interface AikNutritionalIndexMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikNutritionalIndex record);

    int insertSelective(AikNutritionalIndex record);

    AikNutritionalIndex selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikNutritionalIndex record);

    int updateByPrimaryKey(AikNutritionalIndex record);

    AikNutritionalIndex selectByUserId(Integer userId);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AikNutritionalIndex> findAll(AikNutritionalIndex record);

    /**
     * 分页查询数据
     * @return
     */
    Page<AikNutritionalIndexVo> findByPage(AikNutritionalIndexVo record);
    
    
    
}