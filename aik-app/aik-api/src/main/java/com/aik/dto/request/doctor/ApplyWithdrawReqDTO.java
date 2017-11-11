package com.aik.dto.request.doctor;

import java.math.BigDecimal;

/**
 * Description: 申请提现
 * Created by as on 2017/11/11.
 */
public class ApplyWithdrawReqDTO {

    private Integer doctorId;

    private Byte channel;

    private String channelAccount;

    private Integer bankId;

    private BigDecimal amount;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public String getChannelAccount() {
        return channelAccount;
    }

    public void setChannelAccount(String channelAccount) {
        this.channelAccount = channelAccount;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
