package com.aik.service.account;

import com.aik.exception.ApiServiceException;

/**
 * @Description
 * @Author as
 * @Date 2017/8/3.
 */
public interface SecurityCodeService {

    /**
     * 生成手机验证码
     *
     * @param codeTypeStr 验证码类型
     * @param mobileNo    手机号码
     * @throws ApiServiceException Api服务异常
     */
    void generateSecurityCode(String codeTypeStr, String mobileNo) throws ApiServiceException;

    /**
     * 生成手机语音验证码
     * @param codeTypeStr
     * @param mobileNo
     * @throws ApiServiceException
     */
    void generateVoiceSecurityCode(String codeTypeStr, String mobileNo) throws ApiServiceException;

    /**
     * 校验手机验证码
     *
     * @param codeTypeStr  验证码类型
     * @param mobileNo     手机号码
     * @param securityCode 验证码
     * @return true：验证通过 false：验证不通过
     * @throws ApiServiceException Api服务异常
     */
    Boolean validSecurityCode(String codeTypeStr, String mobileNo, String securityCode) throws ApiServiceException;
}
