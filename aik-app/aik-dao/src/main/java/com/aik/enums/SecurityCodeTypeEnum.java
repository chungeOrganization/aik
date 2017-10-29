package com.aik.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Description: security code type enum
 * Created by as on 2017/8/5.
 */
public enum SecurityCodeTypeEnum {
    CODE_TYPE_DOCTOR_REGISTER((byte) 1, "医生注册申请邀请码获取验证码"),
    CODE_TYPE_USER_REGISTER((byte) 2, "用户注册获取验证码"),
    CODE_TYPE_DOCTOR_MOBILE_RELIEVE((byte) 3, "医生解绑手机号获取验证码"),
    CODE_TYPE_DOCTOR_MOBILE_BINDING((byte) 4, "医生绑定手机号获取验证码"),
    CODE_TYPE_USER_FIND_PASSWORD((byte) 5, "用户找回密码获取验证码"),
    CODE_TYPE_DOCTOR_RESET_PAY_PASSWORD((byte) 6, "医生重置支付密码获取验证码"),
    CODE_TYPE_DOCTOR_FIND_PASSWORD((byte) 7, "医生找回密码获取验证码");
    private byte type;
    private String desc;

    SecurityCodeTypeEnum(Byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static SecurityCodeTypeEnum getTypeEnumByTypeStr(String typeStr) {
        if (StringUtils.isBlank(typeStr)) {
            return null;
        }

        byte type;
        try {
            type = Byte.valueOf(typeStr);
        } catch (NumberFormatException e) {
            return null;
        }

        SecurityCodeTypeEnum securityCodeTypeEnum = null;
        for (SecurityCodeTypeEnum typeEnum : SecurityCodeTypeEnum.values()) {
            if (typeEnum.type == type) {
                securityCodeTypeEnum = typeEnum;
                break;
            }
        }

        return securityCodeTypeEnum;
    }
}
