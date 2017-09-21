package com.aik.bean.userside;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 * Created by as on 2017/9/10.
 */
public class QuestionOrderDetail {

    private String questionDescription;

    private String illName;

    private Date questionDate;

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getIllName() {
        return illName;
    }

    public void setIllName(String illName) {
        this.illName = illName;
    }

    public Date getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Date questionDate) {
        this.questionDate = questionDate;
    }
}
