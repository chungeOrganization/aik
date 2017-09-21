package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
public enum UserAccountUserTypeEnum {
    // 用户类型： 0健康的咨询者、1家属、2患者
    HEALTHY_CONSULTANT((byte) 0, "健康的咨询者"),
    RELATION((byte) 1, "家属"),
    SICK((byte) 2, "患者");

    private byte code;
    private String desc;

    UserAccountUserTypeEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescFromCode(byte code) {
        for (UserAccountUserTypeEnum typeEnum : UserAccountUserTypeEnum.values()) {
            if (typeEnum.getCode() == code) {
                return typeEnum.getDesc();
            }
        }

        return "";
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
