package com.aik.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Desc: 第三方登录
 * Create by as on 2017/11/10
 */
@Setter
@Getter
public class ExternalLoginReqDTO {

    // 第三方api请求校验token
    private String accessToken;

    // 第三方用户openId（APP唯一）
    private String openId;

    // 第三方平台（QQ、WeChat）
    private String platform;

    // 设备类型（IOS、Android）
    private String devType;
}
