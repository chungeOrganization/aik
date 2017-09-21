package com.aik.model;

import java.math.BigDecimal;
import java.util.Date;

public class DietFoodFat {
    private Integer id;

    private Integer foodId;

    private BigDecimal saturatedFattyAcid;

    private BigDecimal unsaturatedFattyAcid;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public BigDecimal getSaturatedFattyAcid() {
        return saturatedFattyAcid;
    }

    public void setSaturatedFattyAcid(BigDecimal saturatedFattyAcid) {
        this.saturatedFattyAcid = saturatedFattyAcid;
    }

    public BigDecimal getUnsaturatedFattyAcid() {
        return unsaturatedFattyAcid;
    }

    public void setUnsaturatedFattyAcid(BigDecimal unsaturatedFattyAcid) {
        this.unsaturatedFattyAcid = unsaturatedFattyAcid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}