package com.aik.service;


import java.util.List;

import com.aik.model.AikHrRoutineBlood;
import com.aik.model.AikHrTumorMarkers;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface HrTumorMarkersManageService {

    
    /**
     * 根据主键删除健康档案-肿瘤标志物指标
     * @param id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增健康档案-肿瘤标志物指标
     *
     * @param aikHrRoutineBlood 健康档案-肿瘤标志物指标
     * @throws Exception 异常
     */
    void save(AikHrTumorMarkers aikHrTumorMarkers) throws Exception;
    
    /**
     * 修改健康档案-肿瘤标志物指标
     *
     * @param StoGoods 健康档案-肿瘤标志物指标
     * @throws Exception 异常
     */
    void update(AikHrTumorMarkers aikHrTumorMarkers) throws  Exception;
    
    /**
     * 查询健康档案-肿瘤标志物指标根据主键
     *
     * @param id 商品订单id
     * @throws Exception 异常
     */
    AikHrTumorMarkers findById(Integer id) throws  Exception;
    
    /**
     * 查询所有健康档案-肿瘤标志物指标
     * @param aikQuestionOrder
     * @return
     * @throws Exception
     */
    List<AikHrTumorMarkers> findAll(AikHrTumorMarkers aikHrTumorMarkers) throws Exception;
    
}
