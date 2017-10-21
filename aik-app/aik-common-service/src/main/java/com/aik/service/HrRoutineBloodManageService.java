package com.aik.service;


import java.util.List;

import com.aik.model.AikHrRoutineBlood;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface HrRoutineBloodManageService {

    
    /**
     * 根据主键删除健康档案-血常规指标类
     * @param id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增健康档案-血常规指标类
     *
     * @param aikHrRoutineBlood 健康档案-血常规指标类
     * @throws Exception 异常
     */
    void save(AikHrRoutineBlood aikHrRoutineBlood) throws Exception;
    
    /**
     * 修改健康档案-血常规指标类
     *
     * @param StoGoods 健康档案-血常规指标类
     * @throws Exception 异常
     */
    void update(AikHrRoutineBlood aikHrRoutineBlood) throws  Exception;
    
    /**
     * 查询健康档案-血常规指标类根据主键
     *
     * @param id 商品订单id
     * @throws Exception 异常
     */
    AikHrRoutineBlood findById(Integer id) throws  Exception;
    
    /**
     * 查询所有健康档案-血常规指标类
     * @param aikQuestionOrder
     * @return
     * @throws Exception
     */
    List<AikHrRoutineBlood> findAll(AikHrRoutineBlood aikHrRoutineBlood) throws Exception;
    
}
