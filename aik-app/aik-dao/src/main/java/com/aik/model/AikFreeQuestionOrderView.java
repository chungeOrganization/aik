package com.aik.model;

import java.util.Date;

public class AikFreeQuestionOrderView {
    private Integer id;

    private Integer freeOrderId;

    private Integer userId;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFreeOrderId() {
        return freeOrderId;
    }

    public void setFreeOrderId(Integer freeOrderId) {
        this.freeOrderId = freeOrderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}