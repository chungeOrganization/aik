package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/10/29.
 */
public enum DoctorIsCompleteInfoEnum {
    TRUE((byte) 1, "已完善资料"),
    FALSE((byte) 0, "未完善资料");
    private byte code;
    private String desc;

    DoctorIsCompleteInfoEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
