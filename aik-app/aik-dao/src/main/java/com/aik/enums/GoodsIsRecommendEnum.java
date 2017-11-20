package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public enum GoodsIsRecommendEnum {
    // 0：未推荐 1：推荐
    NOT_RECOMMEND((byte) 0, "未推荐"),
    NEW_RECOMMEND((byte) 1, "新品推荐"),
    MANAGER_RECOMMEND((byte) 2, "掌柜推荐");

    private byte code;

    private String desc;

    GoodsIsRecommendEnum(byte code, String desc) {
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
