package com.aik.dto;

import java.util.Date;

/**
 * Description:
 * Created by as on 2017/8/21.
 */
public class UserInfoDTO {

    private Integer accountId;

    private String headImg;

    private Byte userType;

    private String realName;

    private String nickName;

    private Byte sex;

    private String areaProvince;

    private String areaCity;

    private Date birthday;

    private Byte isElseIllness;

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

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public Byte getIsElseIllness() {
        return isElseIllness;
    }

    public void setIsElseIllness(Byte isElseIllness) {
        this.isElseIllness = isElseIllness;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "accountId=" + accountId +
                ", headImg='" + headImg + '\'' +
                ", userType=" + userType +
                ", realName='" + realName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", areaProvince='" + areaProvince + '\'' +
                ", areaCity='" + areaCity + '\'' +
                ", birthday=" + birthday +
                ", isElseIllness=" + isElseIllness +
                ", devType='" + devType + '\'' +
                '}';
    }
}
