package com.aik.dto.request;

import com.aik.enums.WeChatPayTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2017/12/18.
 */
@Setter
@Getter
public class WeChatCreatePayOrderReqDTO {
    private WeChatPayTypeEnum typeEnum;
    private String outTradeNo;
    private Integer totalFee;
    private String spbillCreateIp;
}
