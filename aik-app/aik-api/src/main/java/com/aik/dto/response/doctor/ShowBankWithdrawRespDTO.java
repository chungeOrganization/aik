package com.aik.dto.response.doctor;

import java.math.BigDecimal;

/**
 * Description: 显示银行卡提现
 * Created by as on 2017/11/11.
 */
public class ShowBankWithdrawRespDTO {

    private Integer bankId;

    private String bankName;

    private String bankType;

    private BigDecimal balance;

    private BigDecimal chargeFee;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }
}
