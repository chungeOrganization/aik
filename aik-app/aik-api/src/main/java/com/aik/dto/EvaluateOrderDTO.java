package com.aik.dto;

/**
 * Description:
 * Created by as on 2017/8/31.
 */
public class EvaluateOrderDTO {

    private Integer orderId;

    private String evaluation;

    private Byte serviceAttitude;

    private Byte answerQuality;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Byte getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(Byte serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public Byte getAnswerQuality() {
        return answerQuality;
    }

    public void setAnswerQuality(Byte answerQuality) {
        this.answerQuality = answerQuality;
    }
}
