package com.aik.dto.response.user;

import com.aik.dto.response.doctor.QuestionAnswerRespDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/10/22.
 */
public class QuestionOrderDetailRespDTO {
    
    private OrderDoctorInfoRespDTO doctorInfo;

    private Integer orderId;

    private BigDecimal orderPrice;

    private String orderTips;

    private String illName;

    private Byte orderStatus;

    private Byte failType;

    private List<QuestionAnswerRespDTO> questionAnswerList = new ArrayList<>();

    public OrderDoctorInfoRespDTO getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(OrderDoctorInfoRespDTO doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderTips() {
        return orderTips;
    }

    public void setOrderTips(String orderTips) {
        this.orderTips = orderTips;
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
