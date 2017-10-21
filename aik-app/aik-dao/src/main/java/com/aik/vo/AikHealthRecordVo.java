package com.aik.vo;

import java.math.BigDecimal;
import java.util.Date;

public class AikHealthRecordVo {
    private Integer id;

    private Integer userId;
    
    private String userName;

    private BigDecimal height;

    private BigDecimal weight;

    private String medicalRecord;

    private String remark;

    private Date hwRecordDate;

    private Date mrRecordDate;

    private Date createDate;

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

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord == null ? null : medicalRecord.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getHwRecordDate() {
        return hwRecordDate;
    }

    public void setHwRecordDate(Date hwRecordDate) {
        this.hwRecordDate = hwRecordDate;
    }

    public Date getMrRecordDate() {
        return mrRecordDate;
    }

    public void setMrRecordDate(Date mrRecordDate) {
        this.mrRecordDate = mrRecordDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
    
}