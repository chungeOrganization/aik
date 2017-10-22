package com.aik.dto.response.doctor;

import java.util.Date;

/**
 * Description:
 * Created by as on 2017/10/21.
 */
public class AnswerRespDTO {

    private Integer answerId;

    private String answer;

    private Date createDate;

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
