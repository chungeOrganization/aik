package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/10/29.
 */
public enum DateStepEnum {
    // 1:一周前 2：一月前 3：半年前
    WEEK((byte) 1, "一周前"),
    MONTH((byte) 2, "一月前"),
    HALF_YEAR((byte) 3, "半年前");

    private Byte type;
    private String desc;

    DateStepEnum(Byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
