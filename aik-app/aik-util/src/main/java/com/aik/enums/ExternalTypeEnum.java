package com.aik.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Desc:
 * Create by as on 2017/11/10
 */
public enum ExternalTypeEnum {
    TENCENT("tencent", "腾讯开放平台"),
    WECHAT("wechat", "微信开放平台"),;
    private String type;

    private String desc;

    ExternalTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ExternalTypeEnum getExternalEnum(String type) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }

        type = type.toLowerCase();
        ExternalTypeEnum targetEnum = null;
        for (ExternalTypeEnum externalEnum : ExternalTypeEnum.values()) {
            if (externalEnum.getType().equals(type)) {
                targetEnum = externalEnum;
                break;
            }
        }

        return targetEnum;
    }

    public String getType() {
        return type;
    }


    public String getDesc() {
        return desc;
    }
}
