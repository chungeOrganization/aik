package com.aik.dto.response.user;

import com.aik.vo.DailyNutritionGradeVO;
import com.aik.vo.NotQualifiedNutritionDetailVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public class BygoneDietRecordAnalyzeRespDTO {

    private BigDecimal healthMinGrade;

    private BigDecimal healthMaxGrade;

    private String nutritionName;

    private List<DailyNutritionGradeVO> dailyNutritionGrades;

    private BigDecimal analyzeResultGrade;

    private Boolean analyzeResult;

    private List<NotQualifiedNutritionDetailVO> notQualifiedNutritionDetail;

    private String notQualifiedTips;

    public BigDecimal getHealthMinGrade() {
        return healthMinGrade;
    }

    public void setHealthMinGrade(BigDecimal healthMinGrade) {
        this.healthMinGrade = healthMinGrade;
    }

    public BigDecimal getHealthMaxGrade() {
        return healthMaxGrade;
    }

    public void setHealthMaxGrade(BigDecimal healthMaxGrade) {
        this.healthMaxGrade = healthMaxGrade;
    }

    public String getNutritionName() {
        return nutritionName;
    }

    public void setNutritionName(String nutritionName) {
        this.nutritionName = nutritionName;
    }

    public List<DailyNutritionGradeVO> getDailyNutritionGrades() {
        return dailyNutritionGrades;
    }

    public void setDailyNutritionGrades(List<DailyNutritionGradeVO> dailyNutritionGrades) {
        this.dailyNutritionGrades = dailyNutritionGrades;
    }

    public BigDecimal getAnalyzeResultGrade() {
        return analyzeResultGrade;
    }

    public void setAnalyzeResultGrade(BigDecimal analyzeResultGrade) {
        this.analyzeResultGrade = analyzeResultGrade;
    }

    public Boolean getAnalyzeResult() {
        return analyzeResult;
    }

    public void setAnalyzeResult(Boolean analyzeResult) {
        this.analyzeResult = analyzeResult;
    }

    public List<NotQualifiedNutritionDetailVO> getNotQualifiedNutritionDetail() {
        return notQualifiedNutritionDetail;
    }

    public void setNotQualifiedNutritionDetail(List<NotQualifiedNutritionDetailVO> notQualifiedNutritionDetail) {
        this.notQualifiedNutritionDetail = notQualifiedNutritionDetail;
    }

    public String getNotQualifiedTips() {
        return notQualifiedTips;
    }

    public void setNotQualifiedTips(String notQualifiedTips) {
        this.notQualifiedTips = notQualifiedTips;
    }
}