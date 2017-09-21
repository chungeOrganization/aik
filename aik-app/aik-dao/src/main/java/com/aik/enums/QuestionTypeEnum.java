package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/14.
 */
public enum QuestionTypeEnum {
    // 0：初始提问 1：追问 2：评分
    INITIAL((byte) 0, "初始提问"),
    ADDTION((byte) 1, "追问"),
    GRADE((byte) 2, "评分");

    private byte code;

    private String desc;

    QuestionTypeEnum(byte code, String desc) {
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
