package com.aik.service;

import com.aik.model.AccMutualCircle;
import com.aik.model.AikHealthRecord;
import com.aik.model.StoUserOrder;
import com.aik.util.PageUtils;
import com.aik.vo.AccMutualCircleVo;
import com.aik.vo.AikHealthRecordVo;
import com.aik.vo.StoUserOrderVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface HealthRecordManageService {

    
    /**
     * 根据主键删除健康档案
     * @param id 商品订单id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增健康档案
     *
     * @param aikHealthRecord 健康档案
     * @throws Exception 异常
     */
    void save(AikHealthRecord aikHealthRecord) throws Exception;
    
    /**
     * 修改健康档案
     *
     * @param StoGoods 健康档案
     * @throws Exception 异常
     */
    void update(AikHealthRecord aikHealthRecord) throws  Exception;
    
    /**
     * 查询健康档案根据主键
     *
     * @param id 商品订单id
     * @throws Exception 异常
     */
    AikHealthRecord findById(Integer id) throws  Exception;
    
    
    /**
     * 查询所有互助圈-评论
     * @param aikQuestionOrder
     * @return
     * @throws Exception
     */
    List<AikHealthRecordVo> findAll(AikHealthRecord aikHealthRecord) throws Exception;
    
    /**
     * 健康档案分页查询
     */
    Page<AikHealthRecordVo> findPage(AikHealthRecordVo aikHealthRecordVo, PageUtils pageUtils) throws  Exception;
    
    
    
}
