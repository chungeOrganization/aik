package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public enum GoodsStatusEnum {
    // 0：未下架（上架中） 1：已下架
    NOT_SOLD_OUT((byte) 0, "未下架（上架中）"),
    SOLD_OUT((byte) 1, "已下架");

    private byte status;
    private String desc;

    GoodsStatusEnum(byte status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public byte getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
