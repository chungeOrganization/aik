package com.aik.vo;

import java.math.BigDecimal;
import java.util.Date;

public class AikQuestionOrderVo {
    private Integer id;

    private Integer userId;
    
    private String userName;

    private String description;

    private Byte illType;

    private String illName;

    private Byte type;

    private Integer doctorId;
    
    private String doctorName;

    private BigDecimal amount;

    private Byte status;

    private Byte failType;

    private String refuseReason;

    private Byte isPayDoctor;

    private Byte auditStatus;

    private Integer auditorId;

    private String auditReason;

    private Date auditDate;

    private Date payDate;

    private Byte refundStatus;

    private Date refundDate;

    private Integer refundAuditorId;

    private Date refundAuditDate;

    private String evaluation;

    private Byte serviceAttitude;

    private Byte answerQuality;

    private Date createDate;

    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Byte getIllType() {
        return illType;
    }

    public void setIllType(Byte illType) {
        this.illType = illType;
    }

    public String getIllName() {
        return illName;
    }

    public void setIllName(String illName) {
        this.illName = illName == null ? null : illName.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getFailType() {
        return failType;
    }

    public void setFailType(Byte failType) {
        this.failType = failType;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason == null ? null : refuseReason.trim();
    }

    public Byte getIsPayDoctor() {
        return isPayDoctor;
    }

    public void setIsPayDoctor(Byte isPayDoctor) {
        this.isPayDoctor = isPayDoctor;
    }

    public Byte getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Byte auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason == null ? null : auditReason.trim();
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Byte getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Byte refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public Integer getRefundAuditorId() {
        return refundAuditorId;
    }

    public void setRefundAuditorId(Integer refundAuditorId) {
        this.refundAuditorId = refundAuditorId;
    }

    public Date getRefundAuditDate() {
        return refundAuditDate;
    }

    public void setRefundAuditDate(Date refundAuditDate) {
        this.refundAuditDate = refundAuditDate;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation == null ? null : evaluation.trim();
    }

    public Byte getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(Byte serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public Byte getAnswerQuality() {
        return answerQuality;
    }

    public void setAnswerQuality(Byte answerQuality) {
        this.answerQuality = answerQuality;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
    
    
    
}