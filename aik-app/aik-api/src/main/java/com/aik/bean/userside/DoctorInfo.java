package com.aik.bean.userside;

import java.math.BigDecimal;

/**
 * Description:
 * Created by as on 2017/9/10.
 */
public class DoctorInfo {

    private Integer id;

    private String headImg;

    private String realName;

    private String hosDepartment;

    private String position;

    private String hosName;

    private BigDecimal starLevel;

    private BigDecimal price;

    private Integer answerCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public BigDecimal getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(BigDecimal starLevel) {
        this.starLevel = starLevel;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }
}
