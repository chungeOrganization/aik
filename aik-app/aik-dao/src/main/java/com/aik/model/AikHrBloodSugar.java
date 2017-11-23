package com.aik.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class AikHrBloodSugar {
    private Integer id;

    private Integer userId;

    private Integer hrId;

    private BigDecimal bloodSugar;

    private BigDecimal tch;

    private BigDecimal tg;

    private BigDecimal hdlC;

    private BigDecimal ldlC;

    private BigDecimal cr;

    private BigDecimal bun;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date recordDate;

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

    public Integer getHrId() {
        return hrId;
    }

    public void setHrId(Integer hrId) {
        this.hrId = hrId;
    }

    public BigDecimal getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(BigDecimal bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public BigDecimal getTch() {
        return tch;
    }

    public void setTch(BigDecimal tch) {
        this.tch = tch;
    }

    public BigDecimal getTg() {
        return tg;
    }

    public void setTg(BigDecimal tg) {
        this.tg = tg;
    }

    public BigDecimal getHdlC() {
        return hdlC;
    }

    public void setHdlC(BigDecimal hdlC) {
        this.hdlC = hdlC;
    }

    public BigDecimal getLdlC() {
        return ldlC;
    }

    public void setLdlC(BigDecimal ldlC) {
        this.ldlC = ldlC;
    }

    public BigDecimal getCr() {
        return cr;
    }

    public void setCr(BigDecimal cr) {
        this.cr = cr;
    }

    public BigDecimal getBun() {
        return bun;
    }

    public void setBun(BigDecimal bun) {
        this.bun = bun;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}