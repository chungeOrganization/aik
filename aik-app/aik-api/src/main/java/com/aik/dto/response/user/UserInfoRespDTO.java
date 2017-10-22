package com.aik.dto.response.user;

/**
 * Description:
 * Created by as on 2017/10/22.
 */
public class UserInfoRespDTO {

    private Integer userId;

    private String headImg;

    private String realName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
