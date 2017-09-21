package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public enum MutualCircleEnum {
    // 是否精选（0：不是 1：是）
    NOT_CHOICENESS((byte) 0, "不是"),
    CHOICENESS((byte) 1, "是");

    private byte code;

    private String desc;

    MutualCircleEnum(byte code, String desc) {
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
