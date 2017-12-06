package com.aik.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DietDailyDietRecord {
    private Integer id;

    private Integer userId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date recordDate;

    private Integer foodId;

    private Integer weight;

    private Byte dietType;

    private Date createDate;

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

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Byte getDietType() {
        return dietType;
    }

    public void setDietType(Byte dietType) {
        this.dietType = dietType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}