package com.aik.service.account;

import com.aik.dto.LoginDTO;
import com.aik.dto.RegisterDTO;
import com.aik.dto.request.ExternalLoginReqDTO;
import com.aik.dto.request.doctor.DoctorRegisterReqDTO;
import com.aik.dto.response.doctor.LoginRespDTO;
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
     * @param reqDTO 注册DTO
     * @throws ApiServiceException Api服务异常
     */
    AccDoctorAccount doctorRegister(DoctorRegisterReqDTO reqDTO) throws ApiServiceException;

    /**
     * 医生端登录
     *
     * @param loginDTO 登录DTO
     * @return respDTO
     * @throws ApiServiceException Api服务异常
     */
    LoginRespDTO doctorLogin(LoginDTO loginDTO) throws ApiServiceException;

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

    /**
     * 医生端第三方登录
     * @param reqDTO 请求
     * @return token
     * @throws ApiServiceException Api服务异常
     */
    LoginRespDTO doctorExternalLogin(ExternalLoginReqDTO reqDTO) throws ApiServiceException;

    /**
     * 用户端第三方登录
     * @param reqDTO 请求
     * @return token
     * @throws ApiServiceException Api服务异常
     */
    String userExternalLogin(ExternalLoginReqDTO reqDTO) throws ApiServiceException;
}
