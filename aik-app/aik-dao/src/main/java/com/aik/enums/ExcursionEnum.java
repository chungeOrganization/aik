package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public enum ExcursionEnum {
    LOW((byte) -1, "偏低"),
    NORMAL((byte) 0, "正常"),
    HIGH((byte) 1, "偏高");

    private byte code;
    private String desc;

    ExcursionEnum(byte code, String desc) {
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
