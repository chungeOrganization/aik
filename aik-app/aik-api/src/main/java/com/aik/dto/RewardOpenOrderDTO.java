package com.aik.dto;

import java.math.BigDecimal;

/**
 * Description:
 * Created by as on 2017/8/31.
 */
public class RewardOpenOrderDTO {
    private Integer orderId;

    private Integer answerId;

    private BigDecimal rewardAmount;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }
}
