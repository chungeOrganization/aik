package com.aik.service.relation;

import com.aik.dto.request.user.GetAttentionListReqDTO;
import com.aik.dto.response.user.GetAttentionDoctorRespDTO;
import com.aik.dto.response.user.GetAttentionUserRespDTO;
import com.aik.exception.ApiServiceException;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/28.
 */
public interface UserAttentionService {

    /**
     * 获取用户关注总数
     *
     * @param userId 用户id
     * @return 返回关注总数
     * @throws ApiServiceException Api服务异常
     */
    Integer getUserAttentionCount(Integer userId) throws ApiServiceException;

    /**
     * 获取用户关注医生列表
     *
     * @param reqDTO request DTO
     * @return 关注列表
     * @throws ApiServiceException Api服务异常
     */
    List<GetAttentionDoctorRespDTO> getAttentionDoctorList(GetAttentionListReqDTO reqDTO) throws ApiServiceException;

    /**
     * 获取用户关注用户列表
     *
     * @param reqDTO request DTO
     * @return 关注列表
     * @throws ApiServiceException Api服务异常
     */
    List<GetAttentionUserRespDTO> getAttentionUserList(GetAttentionListReqDTO reqDTO) throws ApiServiceException;

    /**
     * 获取医生简介
     *
     * @param doctorId 医生id
     * @param userId   用户id
     * @return 医生简介
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getDoctorIntroduction(Integer doctorId, Integer userId) throws ApiServiceException;

    /**
     * 取消关注医生
     *
     * @param doctorId 医生id
     * @param userId   用户id
     * @throws ApiServiceException Api服务异常
     */
    void cancelAttentionDoctor(Integer doctorId, Integer userId) throws ApiServiceException;

    /**
     * 关注医生
     *
     * @param doctorId 医生id
     * @param userId   用户id
     * @throws ApiServiceException Api服务异常
     */
    void attentionDoctor(Integer doctorId, Integer userId) throws ApiServiceException;

    /**
     * 取消关注用户
     *
     * @param cancelUserId 取关用户id
     * @param userId       用户id
     * @throws ApiServiceException Api服务异常
     */
    void cancelAttentionUser(Integer cancelUserId, Integer userId) throws ApiServiceException;

    /**
     * 关注用户
     *
     * @param attentionUserId 取关用户id
     * @param userId       用户id
     * @throws ApiServiceException Api服务异常
     */
    void attentionUser(Integer attentionUserId, Integer userId) throws ApiServiceException;
}
