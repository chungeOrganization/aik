package com.aik.xml.response;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Description:
 * Created by as on 2017/12/18.
 */
@Setter
@Getter
@XmlRootElement
public class WeChatCreatePayOrderRespXml extends WeChatBaseRespXml {
    private String appid;
    @XmlAttribute(name = "mch_id")
    private String mchId;
    @XmlAttribute(name = "device_info")
    private String deviceInfo;
    @XmlAttribute(name = "nonce_str")
    private String nonceStr;
    private String sign;
    @XmlAttribute(name = "result_code")
    private String resultCode;
    @XmlAttribute(name = "err_code")
    private String errCode;
    @XmlAttribute(name = "err_code_des")
    private String errCodeDes;
    @XmlAttribute(name = "trade_type")
    private String tradeType;
    @XmlAttribute(name = "prepay_id")
    private String prepayId;
}
