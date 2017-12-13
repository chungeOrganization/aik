package com.aik.dto;

/**
 * Desc:
 * Create by as on 2017/12/13
 */
public class TencentUserInfoDTO {
    private Integer ret;
    private String msg;
    private String nickname;
    private String figureurl_qq_1;
    private String gender;

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }

    public void setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
