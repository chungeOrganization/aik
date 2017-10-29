package com.aik.util;

import com.aik.dto.SendSmsRespDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: send sms util
 * Created by as on 2017/8/5.
 */
@Component
public class SendSmsUtils {

    private static final Logger logger = LoggerFactory.getLogger(SendSmsUtils.class);

    private final String DEFAULT_ENCODE_CHARSET = "UTF-8";
    private final int SEND_SMS_OK_CODE = 0;

    @Autowired
    private SendSmsProperties sendSmsProperties;

    public boolean sendInviteCodeSms(String mobileNo, String securityCode) {
        // http://v.juhe.cn/sms/send?mobile=手机号码&tpl_id=短信模板ID&tpl_value=%23code%23%3D654654&key=
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("mobile", mobileNo);
            params.put("tpl_id", sendSmsProperties.getInviteCodeTID());
            params.put("tpl_value", URLEncoder.encode("#code#=" + securityCode, DEFAULT_ENCODE_CHARSET));
            params.put("key", sendSmsProperties.getAppKey());

            sendSms(params);
        } catch (Exception e) {
            logger.error("send sms error:", e);
        }

        return false;
    }

    public boolean sendSecurityCodeSms(String mobileNo, String securityCode) {
        // http://v.juhe.cn/sms/send?mobile=手机号码&tpl_id=短信模板ID&tpl_value=%23code%23%3D654654&key=
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("mobile", mobileNo);
            params.put("tpl_id", sendSmsProperties.getSecurityCodeTID());
            params.put("tpl_value", URLEncoder.encode("#code#=" + securityCode, DEFAULT_ENCODE_CHARSET));
            params.put("key", sendSmsProperties.getAppKey());

            sendSms(params);
        } catch (Exception e) {
            logger.error("send sms error:", e);
        }

        return false;
    }

    public boolean sendSms(Map<String, Object> params) {
        try {
            String response = HttpClientUtils.doGet(sendSmsProperties.getApi(), params, DEFAULT_ENCODE_CHARSET);
            logger.debug("send sms response:[{}]", response);
            response = "{" + response + "}";
            ObjectMapper objectMapper = new ObjectMapper();
            SendSmsRespDTO sendSmsResp = objectMapper.convertValue(response, SendSmsRespDTO.class);
            if (SEND_SMS_OK_CODE == sendSmsResp.getError_code()) {
                return true;
            }
        } catch (Exception e) {
            logger.error("send sms error:", e);
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        String response = "{\"reason\":\"操作成功\",\"result\":{\"sid\":\"172910525209012167\",\"fee\":1,\"count\":1},\"error_code\":0}";
        ObjectMapper objectMapper = new ObjectMapper();
        SendSmsRespDTO sendSmsResp2 = objectMapper.readValue(response, SendSmsRespDTO.class);
        System.out.println(sendSmsResp2);
    }
}
