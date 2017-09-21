package com.aik.dto;

import com.aik.model.AikHealthRecord;
import com.aik.model.AikHrBloodSugar;
import com.aik.model.AikHrRoutineBlood;
import com.aik.model.AikHrTumorMarkers;

/**
 * Description:
 * Created by as on 2017/9/6.
 */
public class AddHealthRecordDTO {

    private AikHealthRecord aikHealthRecord;

    private AikHrBloodSugar aikHrBloodSugar;

    private AikHrRoutineBlood aikHrRoutineBlood;

    private AikHrTumorMarkers aikHrTumorMarkers;

    public AikHealthRecord getAikHealthRecord() {
        return aikHealthRecord;
    }

    public void setAikHealthRecord(AikHealthRecord aikHealthRecord) {
        this.aikHealthRecord = aikHealthRecord;
    }

    public AikHrBloodSugar getAikHrBloodSugar() {
        return aikHrBloodSugar;
    }

    public void setAikHrBloodSugar(AikHrBloodSugar aikHrBloodSugar) {
        this.aikHrBloodSugar = aikHrBloodSugar;
    }

    public AikHrRoutineBlood getAikHrRoutineBlood() {
        return aikHrRoutineBlood;
    }

    public void setAikHrRoutineBlood(AikHrRoutineBlood aikHrRoutineBlood) {
        this.aikHrRoutineBlood = aikHrRoutineBlood;
    }

    public AikHrTumorMarkers getAikHrTumorMarkers() {
        return aikHrTumorMarkers;
    }

    public void setAikHrTumorMarkers(AikHrTumorMarkers aikHrTumorMarkers) {
        this.aikHrTumorMarkers = aikHrTumorMarkers;
    }
}
