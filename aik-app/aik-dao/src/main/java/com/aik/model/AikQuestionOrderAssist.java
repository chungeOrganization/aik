package com.aik.model;

import java.util.Date;

public class AikQuestionOrderAssist {
    private Integer id;

    private Integer orderId;

    private String latelyReplyContent;

    private Date latelyReplyDate;

    private Date createDate;

    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getLatelyReplyContent() {
        return latelyReplyContent;
    }

    public void setLatelyReplyContent(String latelyReplyContent) {
        this.latelyReplyContent = latelyReplyContent == null ? null : latelyReplyContent.trim();
    }

    public Date getLatelyReplyDate() {
        return latelyReplyDate;
    }

    public void setLatelyReplyDate(Date latelyReplyDate) {
        this.latelyReplyDate = latelyReplyDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}