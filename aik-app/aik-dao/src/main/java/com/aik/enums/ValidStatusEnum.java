package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
public enum ValidStatusEnum {
    VALID((byte) 1, "有效"),
    INVALID((byte) 0, "无效");
    private byte status;
    private String desc;

    ValidStatusEnum(byte status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public byte getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
