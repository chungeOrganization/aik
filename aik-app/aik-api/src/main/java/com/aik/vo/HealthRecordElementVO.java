package com.aik.vo;

import java.math.BigDecimal;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public class HealthRecordElementVO {

    private String elemName;

    private BigDecimal elemContent;

    private String elemUnit;

    private Byte elemRemark;

    public String getElemName() {
        return elemName;
    }

    public void setElemName(String elemName) {
        this.elemName = elemName;
    }

    public BigDecimal getElemContent() {
        return elemContent;
    }

    public void setElemContent(BigDecimal elemContent) {
        this.elemContent = elemContent;
    }

    public String getElemUnit() {
        return elemUnit;
    }

    public void setElemUnit(String elemUnit) {
        this.elemUnit = elemUnit;
    }

    public Byte getElemRemark() {
        return elemRemark;
    }

    public void setElemRemark(Byte elemRemark) {
        this.elemRemark = elemRemark;
    }
}
