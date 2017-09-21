package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
public enum UserAttentionTypeEnum {
    ATTENTION_DOCTOR((byte) 0, "医生"),
    ATTENTION_USER((byte) 1, "用户");

    private byte code;
    private String desc;

    UserAttentionTypeEnum(byte code, String desc) {
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
