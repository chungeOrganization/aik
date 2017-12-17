package com.aik.xml.request;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Description:
 * Created by as on 2017/12/17.
 */
@Getter
@Setter
@XmlRootElement
public class WeChatCreatePayOrderReqXml implements Serializable {
    // 应用id
    private String appid;
    // 商户号
    @XmlAttribute(name = "mch_id")
    private String mchId;
    // 设备号
    @XmlAttribute(name = "device_info")
    private String deviceInfo = "WEB";
    // 随机字符串
    @XmlAttribute(name = "nonce_str")
    private String nonceStr;
    // 签名
    private String sign;
    // 商品描述
    private String body;
    // 商品详情
    private String detail;
    // 商户订单号
    @XmlAttribute(name = "out_trade_no")
    private String outTradeNo;
    // 总金额单位分
    @XmlAttribute(name = "total_fee")
    private Integer totalFee;
    // 终端ip
    @XmlAttribute(name = "spbill_create_ip")
    private String spbillCreateIp;
    // 通知地址
    @XmlAttribute(name = "notify_url")
    private String notifyUrl;
    // 交易类型
    @XmlAttribute(name = "trade_type")
    private String tradeType = "APP";
}
