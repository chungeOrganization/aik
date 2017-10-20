package com.aik.dto.request.doctor;

/**
 * Description:
 * Created by as on 2017/10/20.
 */
public class UpdatePwdReqDTO {

    private Integer accountId;

    private String oldPassword;

    private String newPassword;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
