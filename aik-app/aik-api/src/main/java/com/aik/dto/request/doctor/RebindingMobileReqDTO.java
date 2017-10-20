package com.aik.dto.request.doctor;

/**
 * Description:
 * Created by as on 2017/10/20.
 */
public class RebindingMobileReqDTO {

    private Integer accountId;

    private String mobileNo;

    private String securityCode;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

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
}
