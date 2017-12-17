package com.aik.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description: 微信支付类型枚举
 * Created by as on 2017/12/18.
 */
@Getter
@AllArgsConstructor
public enum WeChatPayTypeEnum {
    SHOPPING((byte) 1, "癌康之家-商店购物"),
    QUESTION((byte) 2, "癌康之家-问诊"),;
    private Byte type;
    private String desc;

    public static WeChatPayTypeEnum enumOf(Byte type) {
        for (WeChatPayTypeEnum typeEnum : values()) {
            if (typeEnum.type.equals(type)) {
                return typeEnum;
            }
        }

        throw new RuntimeException("WeChatPayTypeEnum find enum exception:" + type);
    }
}
