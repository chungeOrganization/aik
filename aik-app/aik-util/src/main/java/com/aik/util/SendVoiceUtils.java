package com.aik.util;

import com.aik.dto.response.SendVoiceRespDTO;
import com.aik.properties.SendVoiceProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 发送语音验证码
 * Created by as on 2017/10/18.
 */
@Component
public class SendVoiceUtils {

    private static final Logger logger = LoggerFactory.getLogger(SendVoiceUtils.class);

    private final String DEFAULT_ENCODE_CHARSET = "UTF-8";
    private final int SEND_VOICE_OK_CODE = 0;
    private final int PLAY_TIMES = 2;

    @Autowired
    private SendVoiceProperties sendVoiceProperties;

    public boolean sendSecurityCodeVoice(String mobile, String securityCode) throws Exception {
        // http://op.juhe.cn/yuntongxun/voice?key=您申请的KEY&valicode=12345678&to=18912312312&playtimes=3
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("valicode", securityCode);
            params.put("to", mobile);
            params.put("key", sendVoiceProperties.getAppKey());
            params.put("playtimes", PLAY_TIMES);

            String response = HttpClientUtils.doGet(sendVoiceProperties.getApi(), params, DEFAULT_ENCODE_CHARSET);
            logger.debug("send voice:[{}]", response);
            ObjectMapper objectMapper = new ObjectMapper();
            SendVoiceRespDTO sendVoiceResp = objectMapper.convertValue(response, SendVoiceRespDTO.class);
            if (SEND_VOICE_OK_CODE == sendVoiceResp.getError_code()) {
                return true;
            }
        } catch (Exception e) {
            logger.error("send voice error:", e);
        }

        return false;
    }
}
