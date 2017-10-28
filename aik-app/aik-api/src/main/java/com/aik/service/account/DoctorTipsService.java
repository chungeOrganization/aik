package com.aik.service.account;

import com.aik.exception.ApiServiceException;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/14.
 */
public interface DoctorTipsService {

    /**
     * 获取医聊列表
     *
     * @param doctorId 医生id
     * @return data
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getDoctorTipsList(Integer doctorId) throws ApiServiceException;

    /**
     * 查看新的朋友
     *
     * @param doctorId 医生id
     * @return 返回新的py列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> checkNewFriendTips(Integer doctorId) throws ApiServiceException;

    /**
     * 查看新的问题
     *
     * @param tipsId 医聊id
     * @throws ApiServiceException Api服务异常
     */
    void checkQuestionTips(Integer tipsId) throws ApiServiceException;

    /**
     * 删除医聊
     *
     * @param tipsId 医聊id
     * @throws ApiServiceException Api服务异常
     */
    void deleteTips(Integer tipsId) throws ApiServiceException;

    /**
     * 获取首页待办
     *
     * @param doctorId 医生id
     * @return 返回待办列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getHomeDoctorTips(Integer doctorId) throws ApiServiceException;

    /**
     * 获取医聊列表
     *
     * @param doctorId 医生id
     * @return data
     * @throws ApiServiceException Api服务异常
     */
    Integer getDoctorTipsCount(Integer doctorId) throws ApiServiceException;
}
