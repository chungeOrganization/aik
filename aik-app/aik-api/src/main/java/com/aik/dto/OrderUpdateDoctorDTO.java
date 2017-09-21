package com.aik.dto;

/**
 * Description:
 * Created by as on 2017/8/31.
 */
public class OrderUpdateDoctorDTO {

    private Integer orderId;

    private Integer doctorId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }
}
