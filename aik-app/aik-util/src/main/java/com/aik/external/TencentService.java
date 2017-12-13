package com.aik.external;

import com.aik.dto.ExternalUserInfoReqDTO;
import com.aik.dto.ExternalUserInfoRespDTO;
import com.aik.dto.TencentUserInfoDTO;
import com.aik.properties.TencentProperties;
import com.aik.util.HttpClientUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc: 腾讯开放平台服务
 * Create by as on 2017/11/10
 */
@Component
public class TencentService {

    @Autowired
    TencentProperties tencentProperties;

    /**
     * 获取腾讯用户信息
     *
     * @param reqDTO 请求
     * @return 用户信息
     */
    public ExternalUserInfoRespDTO getTencentUserInfo(ExternalUserInfoReqDTO reqDTO) {
        if (StringUtils.isEmpty(reqDTO.getAccessToken()) || StringUtils.isEmpty(reqDTO.getOpenId())) {
            throw new RuntimeException("获取腾讯用户信息accessToken与openId不能为空");
        }

        ExternalUserInfoRespDTO respDTO = new ExternalUserInfoRespDTO();

        try {
//            https://graph.qq.com/user/get_user_info?
//            access_token=*************&
//            oauth_consumer_key=12345&
//            openid=****************&
//            format=json

            Map<String, Object> params = new HashMap<>();
            params.put("access_token", reqDTO.getAccessToken());
            params.put("oauth_consumer_key", tencentProperties.getAppId());
            params.put("openid", reqDTO.getOpenId());
            params.put("format", "json");
            String response = HttpClientUtils.doGet(tencentProperties.getUserInfoApi(), params, "utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            TencentUserInfoDTO userInfoDTO = objectMapper.readValue(response, TencentUserInfoDTO.class);
            if (userInfoDTO.getRet() != 0) {
                throw new RuntimeException("调用腾讯获取用户信息接口异常：" + userInfoDTO.getRet() + " " + userInfoDTO.getMsg());
            } else {
                respDTO.setOpenId(reqDTO.getOpenId());
                respDTO.setPlatform(reqDTO.getPlatformType());
                respDTO.setHeadImg(userInfoDTO.getFigureurl_qq_1());
                respDTO.setNickName(userInfoDTO.getNickname());
                if ("男".equals(userInfoDTO.getGender())) {
                    respDTO.setSex((byte) 0);
                } else {
                    respDTO.setSex((byte) 1);
                }
            }

//            Content-type: text/html; charset=utf-8
//            {
//                    "ret":0,
//                    "msg":"",
//                    "nickname":"Peter",
//                    "figureurl":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/30",
//                    "figureurl_1":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/50",
//                    "figureurl_2":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/100",
//                    "figureurl_qq_1":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/40",
//                    "figureurl_qq_2":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/100",
//                    "gender":"男",
//                    "is_yellow_vip":"1",
//                    "vip":"1",
//                    "yellow_vip_level":"7",
//                    "level":"7",
//                    "is_yellow_year_vip":"1"
//            }


        } catch (Exception e) {
            throw new RuntimeException("调用腾讯获取用户信息接口异常：" + e.getMessage());
        }

        return respDTO;
    }
}
