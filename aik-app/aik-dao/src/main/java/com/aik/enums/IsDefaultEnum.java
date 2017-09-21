package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/5.
 */
public enum IsDefaultEnum {
    NOT_DEFAULT((byte) 0, "非默认地址"),
    DEFAULT((byte) 1, "默认地址");

    private byte code;

    private String desc;

    IsDefaultEnum(byte code, String desc) {
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
