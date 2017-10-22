package com.aik.dto.response.doctor;

/**
 * Description:
 * Created by as on 2017/10/23.
 */
public class QuestionAnswerRespDTO {

    private QuestionRespDTO question;

    private AnswerRespDTO answer;

    public QuestionRespDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionRespDTO question) {
        this.question = question;
    }

    public AnswerRespDTO getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerRespDTO answer) {
        this.answer = answer;
    }
}
