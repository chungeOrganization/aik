package com.aik.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created by as on 2017/10/18.
 */
@Component
@ConfigurationProperties(prefix = "juhe.sms")
public class SendSmsProperties {

    private String api;

    private String appKey;

    private Integer securityCodeTID;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Integer getSecurityCodeTID() {
        return securityCodeTID;
    }

    public void setSecurityCodeTID(Integer securityCodeTID) {
        this.securityCodeTID = securityCodeTID;
    }
}
