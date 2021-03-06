package com.aik.util;

import com.aik.dto.request.ExternalUserInfoReqDTO;
import com.aik.dto.response.ExternalUserInfoRespDTO;
import com.aik.enums.ExternalTypeEnum;
import com.aik.external.TencentService;
import com.aik.external.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Desc: 第三方工具类
 * Create by as on 2017/11/10
 */
@Component
public class ExternalUtils {

    @Autowired
    private TencentService tencentService;

    @Autowired
    private WeChatService weChatService;

    /**
     * 获取第三方平台
     * @param reqDTO
     * @return
     */
    public ExternalUserInfoRespDTO getExternalUserInfo(ExternalUserInfoReqDTO reqDTO) {
        if (null == reqDTO) {
            throw new RuntimeException("请求参数为空");
        }

        ExternalUserInfoRespDTO respDTO = new ExternalUserInfoRespDTO();

        // 获取第三方类型
        ExternalTypeEnum typeEnum = ExternalTypeEnum.getExternalEnum(reqDTO.getPlatformType().toLowerCase());
        if (null == typeEnum) {
            throw new RuntimeException("未获取到第三方枚举类型");
        }

        if (typeEnum.equals(ExternalTypeEnum.TENCENT)) {
            respDTO = tencentService.getTencentUserInfo(reqDTO);
        } else if (typeEnum.equals(ExternalTypeEnum.WECHAT)) {
            respDTO = weChatService.getWeChatUserInfo(reqDTO);
        } else {
            throw new RuntimeException("第三方枚举类型不正确");
        }

        return respDTO;
    }
}
