package com.aik.service.account;

import com.aik.dto.LoginDTO;
import com.aik.dto.RegisterDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccDoctorAccount;
import com.aik.model.AccUserAccount;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
public interface AuthService {

    /**
     * 医生端注册
     *
     * @param registerDTO 注册DTO
     * @throws ApiServiceException Api服务异常
     */
    AccDoctorAccount doctorRegister(RegisterDTO registerDTO) throws ApiServiceException;

    /**
     * 医生端登录
     *
     * @param loginDTO 登录DTO
     * @return token
     * @throws ApiServiceException Api服务异常
     */
    String doctorLogin(LoginDTO loginDTO) throws ApiServiceException;

    /**
     * 医生端刷新token
     *
     * @param oldToken old token
     * @return new token
     * @throws ApiServiceException Api服务异常
     */
    String doctorRefreshToken(String oldToken) throws ApiServiceException;

    /**
     * 用户注册
     *
     * @param registerDTO 注册DTO
     * @throws ApiServiceException Api服务异常
     */
    AccUserAccount userRegister(RegisterDTO registerDTO) throws ApiServiceException;

    /**
     * 用户登录
     *
     * @param loginDTO 登录DTO
     * @return token
     * @throws ApiServiceException Api服务异常
     */
    String userLogin(LoginDTO loginDTO) throws ApiServiceException;

    /**
     * 用户刷新token
     *
     * @param oldToken old token
     * @return new token
     * @throws ApiServiceException Api服务异常
     */
    String userRefreshToken(String oldToken) throws ApiServiceException;
}
