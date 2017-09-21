package com.aik.model;

import java.math.BigDecimal;
import java.util.Date;

public class DietDailyNutrition {
    private Integer id;

    private Integer userId;

    private Date recordDate;

    private BigDecimal protein;

    private BigDecimal lipid;

    private BigDecimal carbs;

    private BigDecimal vitamin;

    private BigDecimal minerals;

    private BigDecimal water;

    private BigDecimal dietaryFiber;

    private Date createDate;

    private Date updateDate;

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

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getLipid() {
        return lipid;
    }

    public void setLipid(BigDecimal lipid) {
        this.lipid = lipid;
    }

    public BigDecimal getCarbs() {
        return carbs;
    }

    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs;
    }

    public BigDecimal getVitamin() {
        return vitamin;
    }

    public void setVitamin(BigDecimal vitamin) {
        this.vitamin = vitamin;
    }

    public BigDecimal getMinerals() {
        return minerals;
    }

    public void setMinerals(BigDecimal minerals) {
        this.minerals = minerals;
    }

    public BigDecimal getWater() {
        return water;
    }

    public void setWater(BigDecimal water) {
        this.water = water;
    }

    public BigDecimal getDietaryFiber() {
        return dietaryFiber;
    }

    public void setDietaryFiber(BigDecimal dietaryFiber) {
        this.dietaryFiber = dietaryFiber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}