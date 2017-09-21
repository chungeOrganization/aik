package com.aik.dto;

/**
 * Description:
 * Created by as on 2017/8/14.
 */
public class RefuseAnswerDTO {

    private Integer orderId;

    private String refuseReason;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    @Override
    public String toString() {
        return "RefuseAnswerDTO{" +
                "orderId=" + orderId +
                ", refuseReason='" + refuseReason + '\'' +
                '}';
    }
}
