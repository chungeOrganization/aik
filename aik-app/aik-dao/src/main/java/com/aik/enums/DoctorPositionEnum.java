package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/15.
 */
public enum DoctorPositionEnum {
    // 主治医生、副主任医生、主任医生、知名专家
    ATTENDING_DOCTOR((byte) 0, "主治医生"),
    DEPUTY_ATTENDING_DOCTOR((byte) 1, "副主任医生"),
    DIRECTOR_DOCTOR((byte) 2, "主任医生"),
    LEADING_DOCTOR((byte) 3, "知名专家");
    private byte code;
    private String desc;

    DoctorPositionEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescFromCode(Byte code) {
        if (null == code) {
            return "";
        }

        for (DoctorPositionEnum positionEnum : DoctorPositionEnum.values()) {
            if (positionEnum.getCode() == code) {
                return positionEnum.getDesc();
            }
        }

        return "";
    }
}
