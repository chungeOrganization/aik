package com.aik.response;

import com.aik.bean.userside.DoctorWithAnswerInfo;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/10.
 */
public class MatchDoctorsResponse {

    private List<DoctorWithAnswerInfo> doctorInfoList;

    public List<DoctorWithAnswerInfo> getDoctorInfoList() {
        return doctorInfoList;
    }

    public void setDoctorInfoList(List<DoctorWithAnswerInfo> doctorInfoList) {
        this.doctorInfoList = doctorInfoList;
    }
}
