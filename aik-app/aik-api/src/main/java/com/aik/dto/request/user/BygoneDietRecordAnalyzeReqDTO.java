package com.aik.dto.request.user;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public class BygoneDietRecordAnalyzeReqDTO {

    private Integer userId;

    private Byte dateStep;

    private String nutritionType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getDateStep() {
        return dateStep;
    }

    public void setDateStep(Byte dateStep) {
        this.dateStep = dateStep;
    }

    public String getNutritionType() {
        return nutritionType;
    }

    public void setNutritionType(String nutritionType) {
        this.nutritionType = nutritionType;
    }
}
