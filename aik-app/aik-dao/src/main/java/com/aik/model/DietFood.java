package com.aik.model;

import java.math.BigDecimal;
import java.util.Date;

public class DietFood {
    private Integer id;

    private String name;

    private Integer category;

    private String image;

    private String recommend;

    private Byte brightSpot;

    private Integer spotRank;

    private BigDecimal proteinRadio;

    private BigDecimal fatRadio;

    private BigDecimal carbsRadio;

    private Byte type;

    private String weightUnit;

    private Integer weight;

    private Date createDate;

    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend == null ? null : recommend.trim();
    }

    public Byte getBrightSpot() {
        return brightSpot;
    }

    public void setBrightSpot(Byte brightSpot) {
        this.brightSpot = brightSpot;
    }

    public Integer getSpotRank() {
        return spotRank;
    }

    public void setSpotRank(Integer spotRank) {
        this.spotRank = spotRank;
    }

    public BigDecimal getProteinRadio() {
        return proteinRadio;
    }

    public void setProteinRadio(BigDecimal proteinRadio) {
        this.proteinRadio = proteinRadio;
    }

    public BigDecimal getFatRadio() {
        return fatRadio;
    }

    public void setFatRadio(BigDecimal fatRadio) {
        this.fatRadio = fatRadio;
    }

    public BigDecimal getCarbsRadio() {
        return carbsRadio;
    }

    public void setCarbsRadio(BigDecimal carbsRadio) {
        this.carbsRadio = carbsRadio;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit == null ? null : weightUnit.trim();
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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