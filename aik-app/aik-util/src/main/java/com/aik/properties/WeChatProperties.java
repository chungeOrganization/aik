package com.aik.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Desc: 微信开放平台配置
 * Create by as on 2017/11/10
 */
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatProperties {

    private String userInfoApi;

    private String appId;

    private String appKey;

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

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
