package com.aik.external;

import com.aik.dto.ExternalUserInfoReqDTO;
import com.aik.dto.ExternalUserInfoRespDTO;
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
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            WeChatUserInfoDTO userInfoDTO = objectMapper.readValue(response, WeChatUserInfoDTO.class);
            if (StringUtils.isNotEmpty(userInfoDTO.getErrcode())) {
                throw new RuntimeException("调用微信获取用户信息接口异常：" + userInfoDTO.getErrcode() + " " + userInfoDTO.getErrmsg());
            } else {
                respDTO.setOpenId(userInfoDTO.getOpenid());
                respDTO.setPlatform(reqDTO.getPlatformType());
                respDTO.setHeadImg(userInfoDTO.getHeadimgurl());
                respDTO.setNickName(userInfoDTO.getNickname());
                respDTO.setSex((byte) (userInfoDTO.getSex() - 1));
            }
//            {
//                "openid":"OPENID",
//                    "nickname":"NICKNAME",
//                    "sex":1,
//                    "province":"PROVINCE",
//                    "city":"CITY",
//                    "country":"COUNTRY",
//                    "headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
//                    "privilege":[
//                "PRIVILEGE1",
//                        "PRIVILEGE2"
//],
//                "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
//
//            }

        } catch (Exception e) {
            throw new RuntimeException("调用微信获取用户信息接口异常：" + e.getMessage());
        }


        return respDTO;
    }

    public static class WeChatUserInfoDTO {
        private String errcode;
        private String errmsg;

        private String openid;
        private String nickname;
        private String headimgurl;
        private Integer sex;

        public String getErrcode() {
            return errcode;
        }

        public void setErrcode(String errcode) {
            this.errcode = errcode;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }
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
