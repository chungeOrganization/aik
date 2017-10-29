package com.aik.service.account;

import com.aik.dto.UserInfoDTO;
import com.aik.dto.request.user.ResetPwdReqDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccUserAccount;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
public interface UserAccountService {
    /**
     * 校验手机号是否已被使用
     *
     * @param mobileNo 手机号码
     * @return true：已被使用 false：未被使用
     * @throws ApiServiceException Api服务异常
     */
    Boolean validMobileNoIsUsed(String mobileNo) throws ApiServiceException;

    /**
     * 校验用户名是否已被使用
     *
     * @param userName 用户名
     * @return true：已被使用 false：未被使用
     * @throws ApiServiceException Api服务异常
     */
    Boolean validUserNameIsUsed(String userName) throws ApiServiceException;

    /**
     * 填充用户信息
     *
     * @param userInfoDTO 用户信息
     * @throws ApiServiceException Api服务异常
     */
    void fillUserInfo(UserInfoDTO userInfoDTO) throws ApiServiceException;

    /**
     * 获取用户账户信息
     *
     * @param userId 用户id
     * @return 用户账户
     * @throws ApiServiceException Api服务异常
     */
    AccUserAccount getUserAccount(Integer userId) throws ApiServiceException;

    /**
     * 修改用户信息
     *
     * @param userInfoDTO 用户信息
     * @throws ApiServiceException Api服务异常
     */
    void updateUserInfo(UserInfoDTO userInfoDTO) throws ApiServiceException;

    /**
     * 修改用户密码
     *
     * @param reqDTO DTO
     * @throws ApiServiceException
     */
    void resetPassword(ResetPwdReqDTO reqDTO) throws ApiServiceException;
}
