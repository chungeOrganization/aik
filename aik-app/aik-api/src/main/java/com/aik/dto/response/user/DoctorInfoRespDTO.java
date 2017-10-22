package com.aik.dto.response.user;

/**
 * Description:
 * Created by as on 2017/10/22.
 */
public class DoctorInfoRespDTO {

    private Integer doctorId;

    private String headImg;

    private String realName;

    private String hosName;

    private String hosDepartment;

    private String position;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getHosDepartment() {
        return hosDepartment;
    }

    public void setHosDepartment(String hosDepartment) {
        this.hosDepartment = hosDepartment;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
