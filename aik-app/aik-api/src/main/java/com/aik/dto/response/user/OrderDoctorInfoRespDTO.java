package com.aik.dto.response.user;

import java.math.BigDecimal;

/**
 * Description:
 * Created by as on 2017/10/23.
 */
public class OrderDoctorInfoRespDTO {

    private String headImg;

    private String realName;

    private String hosName;

    private String hosDepartment;

    private String position;

    private Integer answerCount;

    private BigDecimal startLevel;

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

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public BigDecimal getStartLevel() {
        return startLevel;
    }

    public void setStartLevel(BigDecimal startLevel) {
        this.startLevel = startLevel;
    }
}
