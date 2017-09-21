package com.aik.model;

import java.util.Date;

public class AikExpertsAnswerView {
    private Integer id;

    private Integer userId;

    private Integer expertsAnswerId;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getExpertsAnswerId() {
        return expertsAnswerId;
    }

    public void setExpertsAnswerId(Integer expertsAnswerId) {
        this.expertsAnswerId = expertsAnswerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}