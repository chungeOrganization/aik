package com.aik.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: 发送语音验证码
 * Created by as on 2017/10/18.
 */
@Component
public class SendVoiceUtils {

    @Autowired
    private SendVoiceProperties sendVoiceProperties;

    public static void sendSecurityCodeVoice(String mobile, String msg) throws Exception {
        // TODO：发送手机语音验证码，可以做成异步发送方式
    }
}
