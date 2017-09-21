package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public enum DietTypeEnum {
    // 0：早餐 1：中餐 2：晚餐 3：营养补充 4：加餐
    BREAKFAST((byte) 0, "早餐"),
    LUNCH((byte) 1, "午餐"),
    DINNER((byte) 2, "晚餐"),
    NUTRITIONAL((byte) 3, "营养补充"),
    EXTRA_MEAL((byte) 4, "加餐");

    private byte code;
    private String desc;

    DietTypeEnum(byte code, String desc) {
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
