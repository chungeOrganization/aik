package com.aik.bean.userside;

/**
 * Description:
 * Created by as on 2017/9/10.
 */
public class DoctorWithAnswerInfo extends DoctorInfo {

    private Integer doctorId;

    private Integer answerCount;

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }
}
