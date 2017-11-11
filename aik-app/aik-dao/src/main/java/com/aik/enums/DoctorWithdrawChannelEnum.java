package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/11/11.
 */
public enum DoctorWithdrawChannelEnum {
    BANK((byte) 1, "银行卡提现"),
    WECHAT((byte) 2, "微信提现"),
    ALIPAY((byte) 3, "支付宝提现");

    private Byte channel;

    private String desc;

    DoctorWithdrawChannelEnum(Byte channel, String desc) {
        this.channel = channel;
        this.desc = desc;
    }

    public static DoctorWithdrawChannelEnum getChannelEnum(Byte channel) {
        if (null == channel) {
            return null;
        }

        DoctorWithdrawChannelEnum targetEnum = null;
        for (DoctorWithdrawChannelEnum channelEnum : DoctorWithdrawChannelEnum.values()) {
            if (channelEnum.getChannel().equals(channel)) {
                targetEnum = channelEnum;
                break;
            }
        }

        return targetEnum;
    }

    public Byte getChannel() {
        return channel;
    }

    public String getDesc() {
        return desc;
    }
}
