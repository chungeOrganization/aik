package com.aik.vo;

import java.util.Date;

public class AccCircleLikeVo {
	
    private Integer id;

    private Integer circleId;

    private Integer likerId;
    
    private String likerName;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }

    public Integer getLikerId() {
        return likerId;
    }

    public void setLikerId(Integer likerId) {
        this.likerId = likerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public String getLikerName() {
		return likerName;
	}

	public void setLikerName(String likerName) {
		this.likerName = likerName;
	}
    
}