package com.aik.vo;

import java.util.Date;

public class AccMutualCircleVo {
    private Integer id;

    private Integer userId;
    
    private String userName;

    private String content;

    private Byte isChoiceness;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Byte getIsChoiceness() {
        return isChoiceness;
    }

    public void setIsChoiceness(Byte isChoiceness) {
        this.isChoiceness = isChoiceness;
    }
    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}