package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/2.
 */
public enum UserStoOrderStatusEnum {
    // 订单状态： 0待付款、1待发货、2已发货、3已完成
    WAITING_PAY((byte) 0, "待付款"),
    WAITING_DELIVERY((byte) 1, "待发货"),
    DELIVERED((byte) 2, "已发货"),
    COMPLETE((byte) 3, "已完成");
    private byte status;
    private String desc;

    UserStoOrderStatusEnum(byte status, String desc) {
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
