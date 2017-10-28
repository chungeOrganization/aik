package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public enum DietTypeEnum {
    // 0：早餐 1：中餐 2：晚餐 3：加油-营养补充 4：加油-膳食补充
    BREAKFAST((byte) 0, "早餐"),
    LUNCH((byte) 1, "午餐"),
    DINNER((byte) 2, "晚餐"),
    ADD_NUTRITION((byte) 3, "加油-营养补充"),
    ADD_FOOD((byte) 4, "加油-膳食补充");

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
