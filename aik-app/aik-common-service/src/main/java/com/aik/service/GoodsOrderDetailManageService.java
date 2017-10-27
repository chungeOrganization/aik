package com.aik.service;

import com.aik.model.StoUserOrderDetail;
import com.aik.util.PageUtils;
import com.aik.vo.StoUserOrderDetailVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface GoodsOrderDetailManageService {

    
    
    /**
     * 根据主键删除商品订单明细信息
     * @param id 商品订单明细id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增商品订单明细信息
     *
     * @param StoGoods 商品订单明细信息
     * @throws Exception 异常
     */
    void save(StoUserOrderDetail stoUserOrderDetail) throws Exception;
    
    /**
     * 修改商品订单明细信息
     *
     * @param StoGoods 商品订单明细信息
     * @throws Exception 异常
     */
    void update(StoUserOrderDetail stoUserOrderDetail) throws  Exception;
    
    /**
     * 查询商品订单明细信息根据主键
     *
     * @param id 商品订单明细id
     * @throws Exception 异常
     */
    StoUserOrderDetail findById(Integer id) throws  Exception;
    
    /**
     * 查询所有商品订单明细信息
     * @param StoGoods
     * @return
     * @throws Exception
     */
    List<StoUserOrderDetailVo> findAll(StoUserOrderDetail stoUserOrderDetail) throws Exception;
    
    /**
     * 商品订单明细信息分页查询
     */
    Page<StoUserOrderDetail> findPage(StoUserOrderDetail stoUserOrderDetail, PageUtils pageUtils) throws  Exception;
    
    
    
}
