package com.aik.dto;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public class IssueCircleDTO {

    private Integer userId;

    private String content;

    private List<String> circleFiles;

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
        this.content = content;
    }

    public List<String> getCircleFiles() {
        return circleFiles;
    }

    public void setCircleFiles(List<String> circleFiles) {
        this.circleFiles = circleFiles;
    }
}
