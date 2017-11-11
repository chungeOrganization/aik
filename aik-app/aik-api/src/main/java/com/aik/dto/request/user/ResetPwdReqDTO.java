package com.aik.dto.request.user;

/**
 * Description:
 * Created by as on 2017/10/22.
 */
public class ResetPwdReqDTO {

    private String mobileNo;

    private String securityCode;

    private String password;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
