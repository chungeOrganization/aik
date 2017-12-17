package com.aik.dto.request.user;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2017/12/18.
 */
@Setter
@Getter
public class CreatePayOrderReqDTO {
    private Integer orderId;

    private Integer acceptAddressId;

    private String leaveMsg;

    private Byte payType;
}
