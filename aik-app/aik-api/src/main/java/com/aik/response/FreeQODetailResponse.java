package com.aik.response;

import com.aik.bean.userside.AnswerWithQuestion;
import com.aik.bean.userside.DoctorInfo;
import com.aik.bean.userside.QuestionOrderDetail;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/10.
 */
public class FreeQODetailResponse {

    // 是否已分享
    private Boolean isShare;

    // 免费结束时间
    private Date freeEndTime;

    // 医生信息
    private DoctorInfo doctorInfo;

    // 问题订单详情
    private QuestionOrderDetail questionOrderDetail;

    // 免费问题查看量
    private Integer viewCount;

    // 回答-追问列表（如果未分享，该内容为空）
    private List<AnswerWithQuestion> answerWithQuestionList;

    public Boolean getShare() {
        return isShare;
    }

    public void setShare(Boolean share) {
        isShare = share;
    }

    public Date getFreeEndTime() {
        return freeEndTime;
    }

    public void setFreeEndTime(Date freeEndTime) {
        this.freeEndTime = freeEndTime;
    }

    public DoctorInfo getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfo doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public QuestionOrderDetail getQuestionOrderDetail() {
        return questionOrderDetail;
    }

    public void setQuestionOrderDetail(QuestionOrderDetail questionOrderDetail) {
        this.questionOrderDetail = questionOrderDetail;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public List<AnswerWithQuestion> getAnswerWithQuestionList() {
        return answerWithQuestionList;
    }

    public void setAnswerWithQuestionList(List<AnswerWithQuestion> answerWithQuestionList) {
        this.answerWithQuestionList = answerWithQuestionList;
    }
}
