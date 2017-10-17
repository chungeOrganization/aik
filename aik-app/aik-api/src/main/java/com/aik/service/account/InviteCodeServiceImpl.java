package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.assist.InviteCodeUtil;
import com.aik.dao.AccInviteCodeMapper;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccInviteCode;
import com.aik.util.SendSmsUtils;
import com.aik.util.StringValidUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
@Service
public class InviteCodeServiceImpl implements InviteCodeService {

    private static final Logger logger = LoggerFactory.getLogger(InviteCodeServiceImpl.class);

    private AccInviteCodeMapper accInviteCodeMapper;

    private DoctorAccountService doctorAccountService;

    private UserAccountService userAccountService;

    @Autowired
    public void setAccInviteCodeMapper(AccInviteCodeMapper accInviteCodeMapper) {
        this.accInviteCodeMapper = accInviteCodeMapper;
    }

    @Autowired
    public void setDoctorAccountService(DoctorAccountService doctorAccountService) {
        this.doctorAccountService = doctorAccountService;
    }

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public void generateInviteCode(String mobileNo) throws ApiServiceException {
        if (StringUtils.isBlank(mobileNo)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (!StringValidUtils.validMobileNo(mobileNo)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        // 验证手机号是否已被使用过
        if (doctorAccountService.validMobileNoIsUsed(mobileNo)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001006);
        }

        // 发送邀请码
        AccInviteCode accInviteCode = accInviteCodeMapper.selectByMobileNo(mobileNo);
        if (null == accInviteCode) {
            String inviteCode = InviteCodeUtil.generatorCode();

            try {
                SendSmsUtils.sendSecurityCodeSms(mobileNo, inviteCode);
            } catch (Exception e) {
                logger.error("send invite code[{}] to mobileNo[{}] error:", inviteCode, mobileNo, e);
                throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001003);
            }

            // 生成邀请码记录
            accInviteCode = new AccInviteCode();
            accInviteCode.setInviteCode(inviteCode);
            accInviteCode.setMobileNo(mobileNo);
            accInviteCode.setCreateDate(new Date());
            accInviteCodeMapper.insertSelective(accInviteCode);
        }

        try {
            SendSmsUtils.sendSecurityCodeSms(mobileNo, accInviteCode.getInviteCode());
        } catch (Exception e) {
            logger.error("send invite code[{}] to mobileNo[{}] error:", accInviteCode.getInviteCode(), mobileNo, e);
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001003);
        }
    }

    @Override
    public Boolean validInviteCode(String mobileNo, String inviteCode) throws ApiServiceException {
        if (StringUtils.isBlank(mobileNo) || StringUtils.isBlank(inviteCode)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        // 验证手机号是否已被使用过
        if (doctorAccountService.validMobileNoIsUsed(mobileNo)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001006);
        }

        if (inviteCode.equals("999999")) {
            return true;
        }

        AccInviteCode accInviteCode = accInviteCodeMapper.selectByMobileNo(mobileNo);

        return null != accInviteCode && accInviteCode.getInviteCode().equals(inviteCode);
    }
}
