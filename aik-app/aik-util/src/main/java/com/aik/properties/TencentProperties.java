package com.aik.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Desc: 腾讯开放平台配置
 * Create by as on 2017/11/10
 */
@Component
@ConfigurationProperties(prefix = "tencent")
public class TencentProperties {

    private String userInfoApi;

    private String appId;

    private String appSecret;

    public String getUserInfoApi() {
        return userInfoApi;
    }

    public void setUserInfoApi(String userInfoApi) {
        this.userInfoApi = userInfoApi;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
