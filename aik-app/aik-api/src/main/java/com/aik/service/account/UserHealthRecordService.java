package com.aik.service.account;

import com.aik.dto.AddHealthRecordDTO;
import com.aik.dto.response.HealthRecordRespDTO;
import com.aik.exception.ApiServiceException;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
public interface UserHealthRecordService {

    /**
     * 获取用户健康档案详情（最近一份）
     *
     * @param userId 用户id
     * @return 用户健康档案详情（最近一份）
     * @throws ApiServiceException Api服务异常
     */
    HealthRecordRespDTO getLastHealthRecordDetail(Integer userId) throws ApiServiceException;

    /**
     * 获取用户健康档案列表
     *
     * @param params 参数
     * @return 用户健康档案列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getUserHealthRecords(Map<String, Object> params) throws ApiServiceException;

    /**
     * 健康档案详情
     *
     * @param healthRecordId 健康档案id
     * @return 健康档案详情
     * @throws ApiServiceException Api服务异常
     */
    HealthRecordRespDTO getHealthRecordDetail(Integer healthRecordId) throws ApiServiceException;

    /**
     * 新增用户健康档案详情
     *
     * @param addHealthRecordDTO 健康档案
     * @param userId 用户id
     * @throws ApiServiceException Api服务异常
     */
    void addHealthRecord(AddHealthRecordDTO addHealthRecordDTO, Integer userId) throws ApiServiceException;
}
