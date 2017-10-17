package com.aik.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created by as on 2017/10/18.
 */
@Component
public class HttpClientUtils {

    @Autowired
    private SendSmsProperties sendSmsProperties;

    public static void sendSecurityCodeSms(String mobileNo, String securityCode) throws Exception {
        // TODO：发送手机验证码，可以做成异步发送方式
    }
}
