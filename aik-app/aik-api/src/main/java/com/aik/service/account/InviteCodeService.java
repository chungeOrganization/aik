package com.aik.service.account;

import com.aik.exception.ApiServiceException;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
public interface InviteCodeService {
    /**
     * 生成邀请码
     *
     * @param mobileNo 手机号码
     * @throws ApiServiceException Api服务异常
     */
    void generateInviteCode(String mobileNo) throws ApiServiceException;

    /**
     * 验证邀请码
     *
     * @param mobileNo   手机号码
     * @param inviteCode 邀请码
     * @return true：验证通过 false：验证不通过
     * @throws ApiServiceException Api服务异常
     */
    Boolean validInviteCode(String mobileNo, String inviteCode) throws ApiServiceException;
}
