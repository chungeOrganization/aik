package com.aik.external;

import com.aik.dto.request.ExternalUserInfoReqDTO;
import com.aik.dto.request.WeChatCreatePayOrderReqDTO;
import com.aik.dto.response.WeChatCreatePayOrderRespDTO;
import com.aik.util.ScrawlUtils;
import com.aik.xml.request.WeChatCreatePayOrderReqXml;
import com.aik.dto.response.ExternalUserInfoRespDTO;
import com.aik.dto.response.WeChatUserInfoRespDTO;
import com.aik.properties.WeChatProperties;
import com.aik.util.HttpClientUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc: 微信开放平台服务
 * Create by as on 2017/11/10
 */
@Component
public class WeChatService {

    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 获取微信登录信息
     *
     * @param reqDTO request
     * @return 第三方登录信息
     */
    public ExternalUserInfoRespDTO getWeChatUserInfo(ExternalUserInfoReqDTO reqDTO) {
        if (StringUtils.isEmpty(reqDTO.getAccessToken()) || StringUtils.isEmpty(reqDTO.getOpenId())) {
            throw new RuntimeException("获取微信用户信息accessToken与openId不能为空");
        }

        ExternalUserInfoRespDTO respDTO = new ExternalUserInfoRespDTO();

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("access_token", reqDTO.getAccessToken());
            params.put("openid", reqDTO.getOpenId());
            String response = HttpClientUtils.doGet(weChatProperties.getUserInfoApi(), params, "utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            WeChatUserInfoRespDTO userInfoDTO = objectMapper.readValue(response, WeChatUserInfoRespDTO.class);
            if (StringUtils.isNotEmpty(userInfoDTO.getErrcode())) {
                throw new RuntimeException("调用微信获取用户信息接口异常：" + userInfoDTO.getErrcode() + " " + userInfoDTO.getErrmsg());
            } else {
                respDTO.setOpenId(userInfoDTO.getOpenid());
                respDTO.setPlatform(reqDTO.getPlatformType());
                respDTO.setHeadImg(userInfoDTO.getHeadimgurl());
                respDTO.setNickName(userInfoDTO.getNickname());
                respDTO.setSex((byte) (userInfoDTO.getSex() - 1));
            }
        } catch (Exception e) {
            throw new RuntimeException("调用微信获取用户信息接口异常：" + e.getMessage());
        }


        return respDTO;
    }

    /**
     * 创建支付订单
     */
    public WeChatCreatePayOrderRespDTO createPayOrder(WeChatCreatePayOrderReqDTO reqDTO) {
        WeChatCreatePayOrderReqXml reqXml = new WeChatCreatePayOrderReqXml();
        reqXml.setAppid(weChatProperties.getAppId());
        reqXml.setMchId(weChatProperties.getMerchantId());
        reqXml.setNonceStr(ScrawlUtils.generateNonceStr(32));
//        reqXml.setSign();
        throw new RuntimeException("创建支付订单异常");
    }

    /**
     * 支付回调
     */
    public void payCallback() {

    }

    /**
     * 查询支付结果
     */
    public void checkPayResult() {

    }

    public static void main(String[] args) {
        try {
//        https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
            Map<String, Object> params = new HashMap<>();
            params.put("appid", "wx01a226153351b6e3");
            params.put("grant_type", "refresh_token");
            params.put("refresh_token", "4_-l1958lbkM9hr5bIlpVC4NFoMW9VnAUImvlk2eSlTbgABCTgTs7-dvUewkEPB8KKsNaeIhJiZtZzXii1QmTFGbPLbYjCy9v4oJWYqbDxpgo");
            String response = HttpClientUtils.doGet("https://api.weixin.qq.com/sns/oauth2/refresh_token", params, "utf-8");
            System.out.println(response);
        } catch (Exception e) {

        }
    }
}
