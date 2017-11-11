package com.aik.dto.response.doctor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 * Created by as on 2017/11/11.
 */
public class ApplyWithdrawRespDTO {

    private BigDecimal charge;

    private Date createDate;

    private Date expectAccountTime;

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpectAccountTime() {
        return expectAccountTime;
    }

    public void setExpectAccountTime(Date expectAccountTime) {
        this.expectAccountTime = expectAccountTime;
    }
}
