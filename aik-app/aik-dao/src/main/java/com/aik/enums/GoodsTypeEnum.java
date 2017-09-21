package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public enum GoodsTypeEnum {
    // 0：特医食品 1：保健食品 2：智能硬件 3：产品套餐
    FSMP((byte) 0, "特医食品"),
    HEALTH((byte) 1, "保健食品"),
    HARDWARE((byte) 2, "智能硬件"),
    COMBO((byte) 3, "产品套餐");

    private byte code;

    private String desc;

    GoodsTypeEnum(byte code, String desc) {
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
