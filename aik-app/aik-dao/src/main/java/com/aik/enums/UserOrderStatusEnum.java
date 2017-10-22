package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/2.
 */
public enum UserOrderStatusEnum {
    // 订单状态： 0待付款、1待发货、2待收货、3已完成、4退款中
    WAITING_PAY((byte) 0, "待付款"),
    WAITING_DELIVERY((byte) 1, "待发货"),
    DELIVERED((byte) 2, "待收货"),
    COMPLETE((byte) 3, "已完成"),
    RETURN((byte) 4, "退款中");
    private byte status;
    private String desc;

    UserOrderStatusEnum(byte status, String desc) {
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
