package com.aik.dto.response.doctor;

/**
 * Description:
 * Created by as on 2017/10/20.
 */
public class UserInfoRespDTO {

    private Integer userId;

    private String userRealName;

    private String userHeadImg;

    private Integer userSex;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }
}
