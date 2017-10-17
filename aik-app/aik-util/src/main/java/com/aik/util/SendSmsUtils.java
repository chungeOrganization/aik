package com.aik.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: send sms util
 * Created by as on 2017/8/5.
 */
@Component
public class SendSmsUtils {

    @Autowired
    private SendSmsProperties sendSmsProperties;

    public static void sendSecurityCodeSms(String mobileNo, String securityCode) throws Exception {
        // TODO：发送手机验证码，可以做成异步发送方式
    }
}
