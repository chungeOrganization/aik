package com.aik.dao;

import com.aik.model.StoUserOrder;
import com.aik.vo.StoUserOrderVo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface StoUserOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoUserOrder record);

    int insertSelective(StoUserOrder record);

    StoUserOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoUserOrder record);

    int updateByPrimaryKey(StoUserOrder record);

    List<StoUserOrder> selectByParams(Map<String, Object> params);
    
    /**
     * 获取所有数据
     * @return
     */
    List<StoUserOrder> findAll(StoUserOrder record);

    /**
     * 分页查询数据
     * @return
     */
    Page<StoUserOrderVo> findByPage(StoUserOrder record);
}