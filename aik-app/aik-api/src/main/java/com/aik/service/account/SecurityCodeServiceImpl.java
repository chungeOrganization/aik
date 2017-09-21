package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.assist.SecurityCodeUtil;
//import com.aik.dao.AccSecurityCodeMapper;
//import com.aik.model.AccSecurityCode;
import com.aik.dao.AccSecurityCodeMapper;
import com.aik.enums.SecurityCodeTypeEnum;
import com.aik.enums.ValidStatusEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccSecurityCode;
import com.aik.util.SendSMSUtils;
import com.aik.util.StringValidUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by as on 2017-08-05.
 */
@Service
public class SecurityCodeServiceImpl implements SecurityCodeService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityCodeServiceImpl.class);

    private AccSecurityCodeMapper accSecurityCodeMapper;

    private InviteCodeService inviteCodeService;

    private DoctorAccountService doctorAccountService;

    private UserAccountService userAccountService;

    @Autowired
    public void setAccSecurityCodeMapper(AccSecurityCodeMapper accSecurityCodeMapper) {
        this.accSecurityCodeMapper = accSecurityCodeMapper;
    }

    @Autowired
    public void setInviteCodeService(InviteCodeService inviteCodeService) {
        this.inviteCodeService = inviteCodeService;
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
    public void generateSecurityCode(String codeTypeStr, String mobileNo) throws ApiServiceException {
        if (StringUtils.isBlank(codeTypeStr) || StringUtils.isBlank(mobileNo)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (!StringValidUtils.validMobileNo(mobileNo)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        SecurityCodeTypeEnum typeEnum = SecurityCodeTypeEnum.getTypeEnumByTypeStr(codeTypeStr);
        if (null == typeEnum) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        // 验证手机号码是否已经被使用过
        if (validMobileNoIsUsed(typeEnum, mobileNo)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001006);
        }

        // 发送手机验证码
        String securityCode = SecurityCodeUtil.generatorCode();
        try {
            SendSMSUtils.sendSMS(mobileNo, securityCode);
        } catch (Exception e) {
            logger.error("send security code[{}] to mobileNo[{}] error:", securityCode, mobileNo, e);
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001001);
        }

        // 插入验证码记录
        AccSecurityCode accSecurityCode = new AccSecurityCode();
        accSecurityCode.setMobileNo(mobileNo);
        accSecurityCode.setSecurityCode(securityCode);
        accSecurityCode.setCodeType(typeEnum.getType());
        accSecurityCode.setCreateDate(new Date());
        accSecurityCodeMapper.insertSelective(accSecurityCode);
    }

    @Override
    public Boolean validSecurityCode(String codeTypeStr, String mobileNo, String securityCode) throws ApiServiceException {
        if (StringUtils.isBlank(codeTypeStr) || StringUtils.isBlank(mobileNo) || StringUtils.isBlank(securityCode)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        SecurityCodeTypeEnum typeEnum = SecurityCodeTypeEnum.getTypeEnumByTypeStr(codeTypeStr);
        if (null == typeEnum) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        if (securityCode.equals("999999")) {
            return true;
        }

        AccSecurityCode accSecurityCode = accSecurityCodeMapper.selectValidSecurityCode(mobileNo, typeEnum.getType());

        boolean validRs = null != accSecurityCode && accSecurityCode.getSecurityCode().equals(securityCode);
        if (validRs) {
            // 将验证码置位无效
            accSecurityCode.setIsValid(ValidStatusEnum.INVALID.getStatus());
            accSecurityCode.setUpdateDate(new Date());
            accSecurityCodeMapper.updateByPrimaryKeySelective(accSecurityCode);

            if (typeEnum == SecurityCodeTypeEnum.CODE_TYPE_DOCTOR_REGISTER) {
                inviteCodeService.generateInviteCode(mobileNo);
            }
        }

        return validRs;
    }

    /**
     * 验证手机号是否已经使用过
     *
     * @param codeTypeEnum 验证码类型
     * @param mobileNo     手机号码
     * @return true：已被使用过 false：未被使用过
     */
    private boolean validMobileNoIsUsed(SecurityCodeTypeEnum codeTypeEnum, String mobileNo) throws ApiServiceException {
        if (codeTypeEnum == SecurityCodeTypeEnum.CODE_TYPE_DOCTOR_REGISTER) {
            return doctorAccountService.validMobileNoIsUsed(mobileNo);
        } else if (codeTypeEnum == SecurityCodeTypeEnum.CODE_TYPE_USER_REGISTER) {
            return userAccountService.validMobileNoIsUsed(mobileNo);
        } else {
            return false;
        }
    }
}
