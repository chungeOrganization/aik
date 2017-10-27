package com.aik.service;

import com.aik.model.StoUserOrder;
import com.aik.util.PageUtils;
import com.aik.vo.StoUserOrderVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface GoodsOrderManageService {

    
    
    /**
     * 根据主键删除商品订单信息
     * @param id 商品订单id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增商品订单信息
     *
     * @param StoGoods 商品订单信息
     * @throws Exception 异常
     */
    void save(StoUserOrder stoUserOrder) throws Exception;
    
    /**
     * 修改商品订单信息
     *
     * @param StoGoods 商品订单信息
     * @throws Exception 异常
     */
    void update(StoUserOrder stoUserOrder) throws  Exception;
    
    /**
     * 查询商品订单信息根据主键
     *
     * @param id 商品订单id
     * @throws Exception 异常
     */
    StoUserOrder findById(Integer id) throws  Exception;
    
    /**
     * 查询所有商品订单信息
     * @param StoGoods
     * @return
     * @throws Exception
     */
    List<StoUserOrderVo> findAll(StoUserOrder stoUserOrder) throws Exception;
    
    /**
     * 商品订单信息分页查询
     */
    Page<StoUserOrderVo> findPage(StoUserOrder stoUserOrder, PageUtils pageUtils) throws  Exception;
    
    
    
}
