package com.aik.dao;

import com.aik.model.DietUserCollectFood;
import com.github.pagehelper.Page;


import java.util.List;
import java.util.Map;

public interface DietUserCollectFoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietUserCollectFood record);

    int insertSelective(DietUserCollectFood record);

    DietUserCollectFood selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietUserCollectFood record);

    int updateByPrimaryKey(DietUserCollectFood record);

    List<Map<String, Object>> selectUserCollectFoods(Integer userId);

    int selectCountBySelective(DietUserCollectFood record);

    List<DietUserCollectFood> selectBySelective(DietUserCollectFood record);
    
    /**
     * 获取所有数据
     * @return
     */
    List<DietUserCollectFood> findAll(DietUserCollectFood record);

    /**
     * 分页查询数据
     * @return
     */
    Page<DietUserCollectFood> findByPage(DietUserCollectFood record);
}