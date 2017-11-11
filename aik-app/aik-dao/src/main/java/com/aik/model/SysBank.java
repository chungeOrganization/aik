package com.aik.model;

import java.math.BigDecimal;
import java.util.Date;

public class SysBank {
    private Integer id;

    private String bankName;

    private String bankIcon;

    private String bankColor;

    private String bankType;

    private BigDecimal chargeFee;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankIcon() {
        return bankIcon;
    }

    public void setBankIcon(String bankIcon) {
        this.bankIcon = bankIcon == null ? null : bankIcon.trim();
    }

    public String getBankColor() {
        return bankColor;
    }

    public void setBankColor(String bankColor) {
        this.bankColor = bankColor == null ? null : bankColor.trim();
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType == null ? null : bankType.trim();
    }

    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}