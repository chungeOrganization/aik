package com.aik.external;

import com.aik.dto.ExternalUserInfoReqDTO;
import com.aik.dto.ExternalUserInfoRespDTO;
import com.aik.properties.WeChatProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Desc: 微信开放平台服务
 * Create by as on 2017/11/10
 */
@Component
public class WeChatService {

    @Autowired
    private WeChatProperties weChatProperties;

    public ExternalUserInfoRespDTO getWeChatUserInfo(ExternalUserInfoReqDTO reqDTO) {
        ExternalUserInfoRespDTO respDTO = new ExternalUserInfoRespDTO();

        return respDTO;
    }
}
