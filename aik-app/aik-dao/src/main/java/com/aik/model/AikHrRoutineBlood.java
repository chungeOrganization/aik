package com.aik.model;

import java.math.BigDecimal;
import java.util.Date;

public class AikHrRoutineBlood {
    private Integer id;

    private Integer userId;

    private Integer hrId;

    private BigDecimal hb;

    private BigDecimal rbc;

    private BigDecimal wbc;

    private BigDecimal plt;

    private BigDecimal ret;

    private BigDecimal bn;

    private BigDecimal sn;

    private BigDecimal eos;

    private BigDecimal baso;

    private BigDecimal lym;

    private BigDecimal mnc;

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

    public BigDecimal getHb() {
        return hb;
    }

    public void setHb(BigDecimal hb) {
        this.hb = hb;
    }

    public BigDecimal getRbc() {
        return rbc;
    }

    public void setRbc(BigDecimal rbc) {
        this.rbc = rbc;
    }

    public BigDecimal getWbc() {
        return wbc;
    }

    public void setWbc(BigDecimal wbc) {
        this.wbc = wbc;
    }

    public BigDecimal getPlt() {
        return plt;
    }

    public void setPlt(BigDecimal plt) {
        this.plt = plt;
    }

    public BigDecimal getRet() {
        return ret;
    }

    public void setRet(BigDecimal ret) {
        this.ret = ret;
    }

    public BigDecimal getBn() {
        return bn;
    }

    public void setBn(BigDecimal bn) {
        this.bn = bn;
    }

    public BigDecimal getSn() {
        return sn;
    }

    public void setSn(BigDecimal sn) {
        this.sn = sn;
    }

    public BigDecimal getEos() {
        return eos;
    }

    public void setEos(BigDecimal eos) {
        this.eos = eos;
    }

    public BigDecimal getBaso() {
        return baso;
    }

    public void setBaso(BigDecimal baso) {
        this.baso = baso;
    }

    public BigDecimal getLym() {
        return lym;
    }

    public void setLym(BigDecimal lym) {
        this.lym = lym;
    }

    public BigDecimal getMnc() {
        return mnc;
    }

    public void setMnc(BigDecimal mnc) {
        this.mnc = mnc;
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