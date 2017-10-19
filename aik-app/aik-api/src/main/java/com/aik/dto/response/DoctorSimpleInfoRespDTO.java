package com.aik.dto.response;

/**
 * Description:
 * Created by as on 2017/10/19.
 */
public class DoctorSimpleInfoRespDTO {

    private String headImg;

    private String realName;

    private String position;

    private String hosName;

    private String hosDepartment;

    public DoctorSimpleInfoRespDTO() {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
}
