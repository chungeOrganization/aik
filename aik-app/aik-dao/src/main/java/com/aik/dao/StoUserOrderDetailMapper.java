package com.aik.dao;

import com.aik.model.StoUserOrder;
import com.aik.model.StoUserOrderDetail;
import com.aik.vo.StoUserOrderDetailVo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface StoUserOrderDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoUserOrderDetail record);

    int insertSelective(StoUserOrderDetail record);

    StoUserOrderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoUserOrderDetail record);

    int updateByPrimaryKey(StoUserOrderDetail record);

    List<Map<String, Object>> selectDetailsBySelective(StoUserOrderDetail record);

    int selectGoodsCountBySelective(StoUserOrderDetail record);
    
    /**
     * 获取所有数据
     * @return
     */
    List<StoUserOrderDetailVo> findAll(StoUserOrderDetail record);

    /**
     * 分页查询数据
     * @return
     */
    Page<StoUserOrderDetail> findByPage(StoUserOrderDetail record);
}