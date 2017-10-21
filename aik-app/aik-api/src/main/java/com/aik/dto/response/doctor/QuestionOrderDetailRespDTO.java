package com.aik.dto.response.doctor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/10/21.
 */
public class QuestionOrderDetailRespDTO {

    private Byte orderStatus;

    private String answerStatus;

    private String userHeaderImg;

    private String doctorHeaderImg;

    private Byte serviceAttitude;

    private Byte answerQuality;

    private List<QuestionAnswer> questionAnswerList;

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        this.answerStatus = answerStatus;
    }

    public String getUserHeaderImg() {
        return userHeaderImg;
    }

    public void setUserHeaderImg(String userHeaderImg) {
        this.userHeaderImg = userHeaderImg;
    }

    public String getDoctorHeaderImg() {
        return doctorHeaderImg;
    }

    public void setDoctorHeaderImg(String doctorHeaderImg) {
        this.doctorHeaderImg = doctorHeaderImg;
    }

    public Byte getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(Byte serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public Byte getAnswerQuality() {
        return answerQuality;
    }

    public void setAnswerQuality(Byte answerQuality) {
        this.answerQuality = answerQuality;
    }

    public List<QuestionAnswer> getQuestionAnswerList() {
        return questionAnswerList;
    }

    public void setQuestionAnswerList(List<QuestionAnswer> questionAnswerList) {
        this.questionAnswerList = questionAnswerList;
    }

    public static class QuestionAnswer{

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
}
