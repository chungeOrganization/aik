package com.aik.model;

import java.util.Date;

public class DietPlanTemplate {
    private Integer id;

    private Integer templateId;

    private Integer foodId;

    private Integer weight;

    private Byte dietType;

    private Date createTime;

    private Integer createManager;

    private Date updateTime;

    private Integer updateManager;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateManager() {
        return createManager;
    }

    public void setCreateManager(Integer createManager) {
        this.createManager = createManager;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateManager() {
        return updateManager;
    }

    public void setUpdateManager(Integer updateManager) {
        this.updateManager = updateManager;
    }
}