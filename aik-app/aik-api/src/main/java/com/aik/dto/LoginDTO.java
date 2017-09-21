package com.aik.dto;

/**
 * Description:
 * Created by as on 2017/8/7.
 */
public class LoginDTO {

    private String userName;

    private String password;

    private String devType;

    private String ip;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", devType='" + devType + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
