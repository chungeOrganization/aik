package com.aik.dao;

import com.aik.model.DietFood;
import com.aik.model.StoGoods;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface StoGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoGoods record);

    int insertSelective(StoGoods record);

    StoGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoGoods record);

    int updateByPrimaryKey(StoGoods record);

    List<Map<String, Object>> selectGoodsBySelective(StoGoods record);

    List<Map<String, Object>> selectRecommendGoods(StoGoods record);

    List<Map<String, Object>> selectByParams(Map<String, Object> params);
    
    /**
     * 获取所有数据
     * @return
     */
    List<StoGoods> findAll(StoGoods record);

    /**
     * 分页查询数据
     * @return
     */
    Page<StoGoods> findByPage(StoGoods record);
}