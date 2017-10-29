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
import com.aik.util.SendSmsUtils;
import com.aik.util.SendVoiceUtils;
import com.aik.util.StringValidUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private SendSmsUtils sendSmsUtils;

    private SendVoiceUtils sendVoiceUtils;

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

    @Autowired
    public void setSendSmsUtils(SendSmsUtils sendSmsUtils) {
        this.sendSmsUtils = sendSmsUtils;
    }

    @Autowired
    public void setSendVoiceUtils(SendVoiceUtils sendVoiceUtils) {
        this.sendVoiceUtils = sendVoiceUtils;
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
        validMobileNoIsUsed(typeEnum, mobileNo);

        // 发送手机验证码
        String securityCode = SecurityCodeUtil.generatorCode();
        try {
            sendSmsUtils.sendSecurityCodeSms(mobileNo, securityCode);
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
    public void generateVoiceSecurityCode(String codeTypeStr, String mobileNo) throws ApiServiceException {
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

        // 验证手机号码
        validMobileNoIsUsed(typeEnum, mobileNo);

        // 发送手机验证码
        String securityCode = SecurityCodeUtil.generatorCode();
        try {
            sendVoiceUtils.sendSecurityCodeVoice(mobileNo, securityCode);
        } catch (Exception e) {
            logger.error("send voice security code[{}] to mobileNo[{}] error:", securityCode, mobileNo, e);
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean validSecurityCode(String codeTypeStr, String mobileNo, String securityCode) throws ApiServiceException {
        if (StringUtils.isBlank(codeTypeStr) || StringUtils.isBlank(mobileNo) || StringUtils.isBlank(securityCode)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        SecurityCodeTypeEnum typeEnum = SecurityCodeTypeEnum.getTypeEnumByTypeStr(codeTypeStr);
        if (null == typeEnum) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
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
     */
    private void validMobileNoIsUsed(SecurityCodeTypeEnum codeTypeEnum, String mobileNo) throws ApiServiceException {
        if (codeTypeEnum == SecurityCodeTypeEnum.CODE_TYPE_DOCTOR_REGISTER ||
                codeTypeEnum == SecurityCodeTypeEnum.CODE_TYPE_DOCTOR_MOBILE_BINDING) {
            if (doctorAccountService.validMobileNoIsUsed(mobileNo)) {
                throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001006);
            }
        } else if (codeTypeEnum == SecurityCodeTypeEnum.CODE_TYPE_USER_REGISTER) {
            if (userAccountService.validMobileNoIsUsed(mobileNo)) {
                throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001006);
            }
        } else if (codeTypeEnum == SecurityCodeTypeEnum.CODE_TYPE_USER_FIND_PASSWORD) {
            if (!userAccountService.validMobileNoIsUsed(mobileNo)) {
                throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001009);
            }
        } else if (codeTypeEnum == SecurityCodeTypeEnum.CODE_TYPE_DOCTOR_FIND_PASSWORD) {
            if (!doctorAccountService.validMobileNoIsUsed(mobileNo)) {
                throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001009);
            }
        }
    }
}
