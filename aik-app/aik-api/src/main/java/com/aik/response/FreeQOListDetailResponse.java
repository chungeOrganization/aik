package com.aik.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

/**
 * Description:
 * Created by as on 2017/9/9.
 */
public class FreeQOListDetailResponse {

    private Integer doctorId;

    private String doctorHeadImg;

    private String sickName;

    private Integer freeOrderId;

    private String questionDescription;

    private BigDecimal questionAmount;

    private Integer viewCount;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorHeadImg() {
        return doctorHeadImg;
    }

    public void setDoctorHeadImg(String doctorHeadImg) {
        this.doctorHeadImg = doctorHeadImg;
    }

    public String getSickName() {
        return sickName;
    }

    public void setSickName(String sickName) {
        this.sickName = sickName;
    }

    public Integer getFreeOrderId() {
        return freeOrderId;
    }

    public void setFreeOrderId(Integer freeOrderId) {
        this.freeOrderId = freeOrderId;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public BigDecimal getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(BigDecimal questionAmount) {
        this.questionAmount = questionAmount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
