package com.aik.dto.response.user;

import com.aik.dto.response.doctor.QuestionAnswerRespDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/10/22.
 */
public class QuestionOrderDetailRespDTO {
    
    private OrderDoctorInfoRespDTO doctorInfo;

    private BigDecimal orderPrice;

    private String auditTips;

    private String illName;

    private Byte orderStatus;

    private Byte failType;

    private List<QuestionAnswerRespDTO> questionAnswerList;

    public OrderDoctorInfoRespDTO getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(OrderDoctorInfoRespDTO doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getAuditTips() {
        return auditTips;
    }

    public void setAuditTips(String auditTips) {
        this.auditTips = auditTips;
    }

    public String getIllName() {
        return illName;
    }

    public void setIllName(String illName) {
        this.illName = illName;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Byte getFailType() {
        return failType;
    }

    public void setFailType(Byte failType) {
        this.failType = failType;
    }

    public List<QuestionAnswerRespDTO> getQuestionAnswerList() {
        return questionAnswerList;
    }

    public void setQuestionAnswerList(List<QuestionAnswerRespDTO> questionAnswerList) {
        this.questionAnswerList = questionAnswerList;
    }
}
