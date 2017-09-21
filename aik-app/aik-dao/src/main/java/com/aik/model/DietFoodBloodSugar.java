package com.aik.model;

import java.math.BigDecimal;
import java.util.Date;

public class DietFoodBloodSugar {
    private Integer id;

    private Integer foodId;

    private BigDecimal gi;

    private BigDecimal gl;

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

    public BigDecimal getGi() {
        return gi;
    }

    public void setGi(BigDecimal gi) {
        this.gi = gi;
    }

    public BigDecimal getGl() {
        return gl;
    }

    public void setGl(BigDecimal gl) {
        this.gl = gl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}