package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/30.
 */
public enum DelFlagEnum {
    NOT_DELETED((byte) 0, "未删除"),
    DELETED((byte) 1, "已删除");

    private byte code;
    private String desc;

    DelFlagEnum(byte code, String desc) {
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
