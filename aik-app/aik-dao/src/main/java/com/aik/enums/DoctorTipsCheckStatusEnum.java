package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/14.
 */
public enum DoctorTipsCheckStatusEnum {
    NOT_CHECK((byte) 0, "未查看"),
    IS_CHECKED((byte) 1, "已查看");
    private byte code;

    private String desc;

    DoctorTipsCheckStatusEnum(byte code, String desc) {
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
