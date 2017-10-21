package com.aik.service;

import com.aik.model.AikHealthRecord;
import com.aik.model.AikHrBloodSugar;
import com.aik.model.AikQuestionOrder;
import com.aik.model.StoUserOrder;
import com.aik.util.PageUtils;
import com.aik.vo.AikHealthRecordVo;
import com.aik.vo.StoUserOrderVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface HrBloodSugarManageService {

    
    /**
     * 根据主键删除健康档案-血糖类
     * @param id 商品订单id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增健康档案-血糖类
     *
     * @param aikHrBloodSugar 健康档案-血糖类
     * @throws Exception 异常
     */
    void save(AikHrBloodSugar aikHrBloodSugar) throws Exception;
    
    /**
     * 修改健康档案-血糖类
     *
     * @param StoGoods 健康档案-血糖类
     * @throws Exception 异常
     */
    void update(AikHrBloodSugar aikHrBloodSugar) throws  Exception;
    
    /**
     * 查询健康档案-血糖类根据主键
     *
     * @param id 商品订单id
     * @throws Exception 异常
     */
    AikHrBloodSugar findById(Integer id) throws  Exception;
    
    /**
     * 查询所有健康档案-血糖类
     * @param aikQuestionOrder
     * @return
     * @throws Exception
     */
    List<AikHrBloodSugar> findAll(AikHrBloodSugar aikQuestionOrder) throws Exception;
    
}
