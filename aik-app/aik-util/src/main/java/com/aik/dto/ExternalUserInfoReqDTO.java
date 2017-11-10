package com.aik.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Desc: 第三方获取用户信息请求DTO
 * Create by as on 2017/11/10
 */
@Setter
@Getter
public class ExternalUserInfoReqDTO {

    private String platformType;
    private String accessToken;
    private String openId;
}
