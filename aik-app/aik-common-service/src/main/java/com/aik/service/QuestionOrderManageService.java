package com.aik.service;

import com.aik.model.AikQuestionOrder;
import com.aik.model.StoUserOrder;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface QuestionOrderManageService {

    
    
    /**
     * 根据主键删除问题订单信息
     * @param id 问题订单id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增问题订单信息
     *
     * @param StoGoods 问题订单信息
     * @throws Exception 异常
     */
    void save(AikQuestionOrder aikQuestionOrder) throws Exception;
    
    /**
     * 修改问题订单信息
     *
     * @param StoGoods 问题订单信息
     * @throws Exception 异常
     */
    void update(AikQuestionOrder aikQuestionOrder) throws  Exception;
    
    /**
     * 查询问题订单信息根据主键
     *
     * @param id 问题订单id
     * @throws Exception 异常
     */
    AikQuestionOrder findById(Integer id) throws  Exception;
    
    /**
     * 查询所有问题订单信息
     * @param StoGoods
     * @return
     * @throws Exception
     */
    List<AikQuestionOrder> findAll(AikQuestionOrder aikQuestionOrder) throws Exception;
    
    /**
     * 问题订单信息分页查询
     */
    Page<AikQuestionOrder> findPage(AikQuestionOrder aikQuestionOrder, PageUtils pageUtils) throws  Exception;
    
    
    
}
