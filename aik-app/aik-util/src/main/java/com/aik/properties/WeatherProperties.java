package com.aik.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Desc: 第三方获取天气参数
 * Create by as on 2017/12/13
 */
@Component
@ConfigurationProperties(prefix = "weather")
public class WeatherProperties {

    private String api;
    private String appCode;
    private String authSign;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAuthSign() {
        return authSign;
    }

    public void setAuthSign(String authSign) {
        this.authSign = authSign;
    }
}
