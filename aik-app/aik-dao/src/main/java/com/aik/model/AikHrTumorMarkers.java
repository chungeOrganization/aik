package com.aik.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class AikHrTumorMarkers {
    private Integer id;

    private Integer userId;

    private Integer hrId;

    private BigDecimal afp;

    private BigDecimal cea;

    private BigDecimal ca19;

    private BigDecimal pg;

    private BigDecimal ca72;

    private BigDecimal scc;

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

    public BigDecimal getAfp() {
        return afp;
    }

    public void setAfp(BigDecimal afp) {
        this.afp = afp;
    }

    public BigDecimal getCea() {
        return cea;
    }

    public void setCea(BigDecimal cea) {
        this.cea = cea;
    }

    public BigDecimal getCa19() {
        return ca19;
    }

    public void setCa19(BigDecimal ca19) {
        this.ca19 = ca19;
    }

    public BigDecimal getPg() {
        return pg;
    }

    public void setPg(BigDecimal pg) {
        this.pg = pg;
    }

    public BigDecimal getCa72() {
        return ca72;
    }

    public void setCa72(BigDecimal ca72) {
        this.ca72 = ca72;
    }

    public BigDecimal getScc() {
        return scc;
    }

    public void setScc(BigDecimal scc) {
        this.scc = scc;
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