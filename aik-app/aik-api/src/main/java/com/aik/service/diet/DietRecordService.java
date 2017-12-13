package com.aik.service.diet;

import com.aik.dto.request.user.BygoneDietRecordAnalyzeReqDTO;
import com.aik.dto.response.user.BygoneDietRecordAnalyzeRespDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.DietDailyDietRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/9.
 */
public interface DietRecordService {

    /**
     * 获取用户饮食记录
     *
     * @param userId     用户id
     * @param recordDate 记录日期
     * @return 用户饮食记录
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getUserDietRecord(Integer userId, Date recordDate) throws ApiServiceException;

    /**
     * 修改用户饮食记录
     *
     * @param dailyDietRecord 饮食记录
     * @throws ApiServiceException Api服务异常
     */
    void updateUserDietRecord(DietDailyDietRecord dailyDietRecord) throws ApiServiceException;

    /**
     * 删除用户饮食记录
     *
     * @param dietRecordId 饮食记录id
     * @throws ApiServiceException Api服务异常
     */
    void delUserDietRecord(Integer dietRecordId) throws ApiServiceException;

    /**
     * 添加用户饮食记录
     *
     * @param dailyDietRecord 饮食记录
     * @throws ApiServiceException Api服务异常
     */
    Integer addUserDietRecord(DietDailyDietRecord dailyDietRecord) throws ApiServiceException;

    /**
     * 获取用户饮食记录分析
     *
     * @param userId     用户id
     * @param recordDate 记录日期
     * @return 用户今日饮食计划分析
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getDietRecordAnalyze(Integer userId, Date recordDate) throws ApiServiceException;

    /**
     * 获取用户往日饮食记录分析
     *
     * @param reqDTO request DTO
     * @return response DTO
     * @throws ApiServiceException
     */
    BygoneDietRecordAnalyzeRespDTO getBygoneDietRecordAnalyze(BygoneDietRecordAnalyzeReqDTO reqDTO) throws ApiServiceException;

    /**
     * 获取当月有饮食记录日期
     *
     * @param userId 用户id
     * @return 饮食记录日期
     * @throws ApiServiceException
     */
    List<String> getDietRecordDates(Date date, Integer userId) throws ApiServiceException;
}
