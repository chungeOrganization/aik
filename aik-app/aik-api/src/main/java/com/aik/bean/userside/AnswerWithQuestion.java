package com.aik.bean.userside;

import java.util.Date;

/**
 * Description:回答与追问
 * Created by as on 2017/9/10.
 */
public class AnswerWithQuestion {

    private String answer;

    private Date answerTime;

    private String question;

    private Date questionTime;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(Date questionTime) {
        this.questionTime = questionTime;
    }
}
