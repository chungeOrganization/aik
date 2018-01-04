package com.aik.dto.request.user;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 * Created by as on 2018/1/4.
 */
@Data
public class ReturnOrderReqDTO {
    private Integer orderId;
    private Byte handleType;
    private String reason;
    private String returnDesc;
    private BigDecimal amount;
    private List<String> files;
}
