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
public class WeChatBaseRespXml {
    @XmlAttribute(name = "return_code")
    private String returnCode;
    @XmlAttribute(name = "return_msg")
    private String returnMsg;
}
