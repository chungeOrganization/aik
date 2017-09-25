package com.aik.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
public class DoctorInfoDTO {

    private Integer accountId;

    private String headImg;

    private String realName;

    private Byte sex;

    private String areaProvince;

    private String areaCity;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    private String identityCard;

    private String email;

    private String hosName;

    private String hosDepartment;

    private String skill;

    private String departmentPhone;

    private String devType;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getAreaProvince() {
        return areaProvince;
    }

    public void setAreaProvince(String areaProvince) {
        this.areaProvince = areaProvince;
    }

    public String getAreaCity() {
        return areaCity;
    }

    public void setAreaCity(String areaCity) {
        this.areaCity = areaCity;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getDepartmentPhone() {
        return departmentPhone;
    }

    public void setDepartmentPhone(String departmentPhone) {
        this.departmentPhone = departmentPhone;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    @Override
    public String toString() {
        return "DoctorInfoDTO{" +
                "accountId=" + accountId +
                ", headImg='" + headImg + '\'' +
                ", realName='" + realName + '\'' +
                ", sex=" + sex +
                ", areaProvince='" + areaProvince + '\'' +
                ", areaCity='" + areaCity + '\'' +
                ", birthday=" + birthday +
                ", identityCard='" + identityCard + '\'' +
                ", email='" + email + '\'' +
                ", hosName='" + hosName + '\'' +
                ", hosDepartment='" + hosDepartment + '\'' +
                ", skill='" + skill + '\'' +
                ", departmentPhone='" + departmentPhone + '\'' +
                ", devType='" + devType + '\'' +
                '}';
    }
}
