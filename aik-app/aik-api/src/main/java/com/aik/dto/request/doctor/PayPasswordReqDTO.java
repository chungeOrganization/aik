package com.aik.dto.request.doctor;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public class PayPasswordReqDTO {
    private Integer accountId;

    private String payPassword;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
}
