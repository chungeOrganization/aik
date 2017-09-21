package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public enum FoodBrightSpotEnum {
    NO_SPOT((byte) -1, "无"),
    GI_SPOT((byte) 0, "GI值最低的常见主食");

    private byte code;
    private String desc;

    FoodBrightSpotEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescFromCode(Byte code) {
        if (null == code) {
            return "";
        }

        for (FoodBrightSpotEnum spotEnum : FoodBrightSpotEnum.values()) {
            if (spotEnum.code == code) {
                return spotEnum.desc;
            }
        }

        return "";
    }
}
