package com.aik.dto.response.doctor;

/**
 * Description:
 * Created by as on 2017/10/19.
 */
public class LoginRespDTO {

    private String token;

    private Byte auditStatus;

    private Byte isCompleteInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Byte getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Byte auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Byte getIsCompleteInfo() {
        return isCompleteInfo;
    }

    public void setIsCompleteInfo(Byte isCompleteInfo) {
        this.isCompleteInfo = isCompleteInfo;
    }
}
