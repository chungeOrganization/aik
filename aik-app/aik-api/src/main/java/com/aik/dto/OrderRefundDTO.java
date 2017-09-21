package com.aik.dto;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/8/31.
 */
public class OrderRefundDTO {

    private Integer orderId;

    private String evaluation;

    private List<String> questionFiles;

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

    public List<String> getQuestionFiles() {
        return questionFiles;
    }

    public void setQuestionFiles(List<String> questionFiles) {
        this.questionFiles = questionFiles;
    }
}
