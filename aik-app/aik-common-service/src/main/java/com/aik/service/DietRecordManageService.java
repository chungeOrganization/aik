package com.aik.service;

import com.aik.model.DietDailyDietRecord;
import com.aik.util.PageUtils;
import com.aik.vo.DietDailyDietRecordVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface DietRecordManageService {

    
    
    /**
     * 根据主键删除饮食实际记录信息
     * @param id 食物id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增饮食实际记录信息
     *
     * @param DietDailyDietRecord 饮食实际记录信息
     * @throws Exception 异常
     */
    void save(DietDailyDietRecord dietDailyDietRecord) throws Exception;
    
    /**
     * 修改饮食实际记录信息
     *
     * @param DietDailyDietRecord 饮食实际记录信息
     * @throws Exception 异常
     */
    void update(DietDailyDietRecord dietDailyDietRecord) throws  Exception;
    
    /**
     * 查询饮食实际记录信息根据主键
     *
     * @param id 记录id
     * @throws Exception 异常
     */
    DietDailyDietRecord findById(Integer id) throws  Exception;
    
    /**
     * 查询所有饮食实际记录信息
     * @param dietFood
     * @return
     * @throws Exception
     */
    List<DietDailyDietRecord> findAll(DietDailyDietRecord dietDailyDietRecord) throws Exception;
    
    /**
     * 饮食实际记录信息分页查询
     */
    Page<DietDailyDietRecordVo> findPage(DietDailyDietRecordVo dietDailyDietRecord, PageUtils pageUtils) throws  Exception;
    
    
    
}
