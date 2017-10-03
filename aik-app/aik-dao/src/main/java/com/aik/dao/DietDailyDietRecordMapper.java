package com.aik.dao;

import com.aik.model.DietDailyDietRecord;
import com.aik.vo.DietDailyDietRecordVo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface DietDailyDietRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DietDailyDietRecord record);

    int insertSelective(DietDailyDietRecord record);

    DietDailyDietRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DietDailyDietRecord record);

    int updateByPrimaryKey(DietDailyDietRecord record);

    List<DietDailyDietRecord> selectBySelective(DietDailyDietRecord record);

    List<Map<String, Object>> selectUserDietRecord(Map<String, Object> params);
    
    /**
     * 获取所有数据
     * @return
     */
    List<DietDailyDietRecord> findAll(DietDailyDietRecord record);

    /**
     * 分页查询数据
     * @return
     */
    Page<DietDailyDietRecordVo> findByPage(DietDailyDietRecordVo record);
}