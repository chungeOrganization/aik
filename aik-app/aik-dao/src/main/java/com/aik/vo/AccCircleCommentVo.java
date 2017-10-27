package com.aik.vo;

import java.util.Date;

public class AccCircleCommentVo {
    private Integer id;

    private Integer circleId;
    
    private String commenterName;

    private Integer commenterId;

    private String content;

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

    public Integer getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(Integer commenterId) {
        this.commenterId = commenterId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public String getCommenterName() {
		return commenterName;
	}

	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}

}