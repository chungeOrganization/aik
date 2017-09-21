package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
public enum DoctorDealTypeEnum {
    // 交易类型 0：提成 1：回答问题 2：提现
    SELL_COMMISION((byte) 0, "提成"),
    ANSWER_QUESTION((byte) 1, "回答问题"),
    WITHDRAW((byte) 2, "提现");

    private byte code;
    private String desc;

    DoctorDealTypeEnum(byte code, String desc) {
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
