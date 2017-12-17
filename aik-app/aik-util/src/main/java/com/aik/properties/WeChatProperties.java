package com.aik.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Desc: 微信开放平台配置
 * Create by as on 2017/11/10
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatProperties {

    private String userInfoApi;

    private String appId;

    private String appKey;

    private String merchantId;

    private String merchantKey;

    private String unifiedOrderApi;

}
