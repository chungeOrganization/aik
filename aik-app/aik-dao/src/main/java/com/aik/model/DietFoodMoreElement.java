package com.aik.model;

import java.math.BigDecimal;
import java.util.Date;

public class DietFoodMoreElement {
    private Integer id;

    private Integer foodId;

    private String nutrientElem;

    private BigDecimal content;

    private String unit;

    private String remark;

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

    public String getNutrientElem() {
        return nutrientElem;
    }

    public void setNutrientElem(String nutrientElem) {
        this.nutrientElem = nutrientElem == null ? null : nutrientElem.trim();
    }

    public BigDecimal getContent() {
        return content;
    }

    public void setContent(BigDecimal content) {
        this.content = content;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}