package com.aik.service;

import com.aik.model.StoGoods;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface GoodsManageService {

    
    
    /**
     * 根据主键删除商品信息
     * @param id 商品id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增商品信息
     *
     * @param StoGoods 商品信息
     * @throws Exception 异常
     */
    void save(StoGoods StoGoods) throws Exception;
    
    /**
     * 修改商品信息
     *
     * @param StoGoods 商品信息
     * @throws Exception 异常
     */
    void update(StoGoods StoGoods) throws  Exception;
    
    /**
     * 查询商品信息根据主键
     *
     * @param id 商品id
     * @throws Exception 异常
     */
    StoGoods findById(Integer id) throws  Exception;
    
    /**
     * 查询所有商品信息
     * @param StoGoods
     * @return
     * @throws Exception
     */
    List<StoGoods> findAll(StoGoods stoGoods) throws Exception;
    
    /**
     * 商品信息分页查询
     */
    Page<StoGoods> findPage(StoGoods stoGoods, PageUtils pageUtils) throws  Exception;
    
    
    
}
