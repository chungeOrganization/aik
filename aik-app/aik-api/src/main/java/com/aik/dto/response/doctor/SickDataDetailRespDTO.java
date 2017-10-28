package com.aik.dto.response.doctor;

import com.aik.dto.response.HealthRecordRespDTO;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public class SickDataDetailRespDTO {

    private SickDetailRespDTO  sickDetail;

    private HealthRecordRespDTO healthRecord;

    public SickDetailRespDTO getSickDetail() {
        return sickDetail;
    }

    public void setSickDetail(SickDetailRespDTO sickDetail) {
        this.sickDetail = sickDetail;
    }

    public HealthRecordRespDTO getHealthRecord() {
        return healthRecord;
    }

    public void setHealthRecord(HealthRecordRespDTO healthRecord) {
        this.healthRecord = healthRecord;
    }
}
