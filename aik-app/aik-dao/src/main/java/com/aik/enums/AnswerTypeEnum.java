package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
public enum AnswerTypeEnum {
    // 0：初始回答 1：追问回答 2：拒绝回答
    INITIAL((byte) 0, "初始回答"),
    ADDITION((byte) 1, "追问回答"),
    REFUSE((byte) 2, "拒绝回答");

    private byte code;

    private String desc;

    AnswerTypeEnum(byte code, String desc) {
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
