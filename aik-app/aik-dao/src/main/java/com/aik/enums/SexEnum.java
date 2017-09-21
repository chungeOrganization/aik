package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
public enum SexEnum {
    MALE((byte) 0, "男"),
    FEMALE((byte) 1, "女");

    private byte code;

    private String desc;

    SexEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescFromCode(byte code) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (code == sexEnum.getCode()) {
                return sexEnum.desc;
            }
        }
        return "";
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
