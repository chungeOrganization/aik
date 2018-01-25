package com.aik.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created by as on 2018/1/25.
 */
@Component
@ConfigurationProperties(prefix = "bankcard.ocr")
@Setter
@Getter
public class BankCardOcrProperties {
    private String api;

    private String appCode;

    private Integer authSign;
}
