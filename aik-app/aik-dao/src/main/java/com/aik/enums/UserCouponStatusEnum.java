package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/5.
 */
public enum UserCouponStatusEnum {
    NOT_USE((byte) 0, "未使用"),
    USED((byte) 1, "已使用"),
    OVERDUE((byte) 2, "已过期");

    private Byte status;
    private String desc;

    UserCouponStatusEnum(Byte status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Byte getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
