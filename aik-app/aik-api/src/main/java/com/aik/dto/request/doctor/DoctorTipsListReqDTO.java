package com.aik.dto.request.doctor;

import com.aik.dto.request.PageReqDTO;

/**
 * Description:
 * Created by as on 2017/12/1.
 */
public class DoctorTipsListReqDTO extends PageReqDTO{
    private Integer doctorId;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }
}
