package com.aik.dao;

import com.aik.model.DietDailyDietRecord;
import com.aik.vo.DietDailyDietRecordVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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
     *
     * @return
     */
    List<DietDailyDietRecord> findAll(DietDailyDietRecord record);

    /**
     * 分页查询数据
     *
     * @return
     */
    Page<DietDailyDietRecordVo> findByPage(DietDailyDietRecordVo record);

    /**
     * 获取传入时间当月有饮食记录日期
     *
     * @param userId 用户id
     * @param date   日期
     * @return 有饮食记录日期
     */
    List<Date> selectUserRecordDate(@Param("userId") Integer userId, @Param("date") Date date);
}