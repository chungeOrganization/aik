package com.aik.dto.response;

import com.aik.vo.HealthRecordElementVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public class HealthRecordRespDTO {

    private BigDecimal height;

    private BigDecimal weight;

    private String medicalRecord;

    private List<HealthRecordElementVO> hrBloodSugar;

    private List<HealthRecordElementVO> hrRoutineBlood;

    private List<HealthRecordElementVO> hrTumorMarkers;

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public List<HealthRecordElementVO> getHrBloodSugar() {
        return hrBloodSugar;
    }

    public void setHrBloodSugar(List<HealthRecordElementVO> hrBloodSugar) {
        this.hrBloodSugar = hrBloodSugar;
    }

    public List<HealthRecordElementVO> getHrRoutineBlood() {
        return hrRoutineBlood;
    }

    public void setHrRoutineBlood(List<HealthRecordElementVO> hrRoutineBlood) {
        this.hrRoutineBlood = hrRoutineBlood;
    }

    public List<HealthRecordElementVO> getHrTumorMarkers() {
        return hrTumorMarkers;
    }

    public void setHrTumorMarkers(List<HealthRecordElementVO> hrTumorMarkers) {
        this.hrTumorMarkers = hrTumorMarkers;
    }
}
