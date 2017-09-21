package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/8/14.
 */
public enum UserFileTypeEnum {
    QUESTION_FILE((byte) 0, "问题订单图片"),
    ORDER_REFUND_FILE((byte) 1, "订单退回图片"),
    CIRCLE_FILE((byte) 2, "互助圈文件");
    private byte code;
    private String desc;

    UserFileTypeEnum(byte code, String desc) {
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
