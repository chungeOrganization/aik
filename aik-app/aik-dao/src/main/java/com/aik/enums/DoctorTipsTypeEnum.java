package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/14.
 */
public enum DoctorTipsTypeEnum {
    // 提示类型 0、新用户关注 1、新的提问
    NEW_FRIEND((byte) 0, "新用户关注"),
    NEW_QUESTION((byte) 1, "新的提问");
    private byte code;

    private String desc;

    DoctorTipsTypeEnum(byte code, String desc) {
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
