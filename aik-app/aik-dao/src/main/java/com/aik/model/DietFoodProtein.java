package com.aik.model;

import java.math.BigDecimal;
import java.util.Date;

public class DietFoodProtein {
    private Integer id;

    private Integer foodId;

    private BigDecimal aminoAcid1;

    private BigDecimal aminoAcid2;

    private BigDecimal aminoAcid3;

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

    public BigDecimal getAminoAcid1() {
        return aminoAcid1;
    }

    public void setAminoAcid1(BigDecimal aminoAcid1) {
        this.aminoAcid1 = aminoAcid1;
    }

    public BigDecimal getAminoAcid2() {
        return aminoAcid2;
    }

    public void setAminoAcid2(BigDecimal aminoAcid2) {
        this.aminoAcid2 = aminoAcid2;
    }

    public BigDecimal getAminoAcid3() {
        return aminoAcid3;
    }

    public void setAminoAcid3(BigDecimal aminoAcid3) {
        this.aminoAcid3 = aminoAcid3;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}