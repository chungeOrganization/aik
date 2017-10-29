package com.aik.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public class DailyNutritionGradeVO {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date recordDate;

    private String nutritionType;

    private String nutritionName;

    private BigDecimal nutritionGrade;

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getNutritionType() {
        return nutritionType;
    }

    public void setNutritionType(String nutritionType) {
        this.nutritionType = nutritionType;
    }

    public String getNutritionName() {
        return nutritionName;
    }

    public void setNutritionName(String nutritionName) {
        this.nutritionName = nutritionName;
    }

    public BigDecimal getNutritionGrade() {
        return nutritionGrade;
    }

    public void setNutritionGrade(BigDecimal nutritionGrade) {
        this.nutritionGrade = nutritionGrade;
    }
}
