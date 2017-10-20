package com.aik.dto.request;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/10/20.
 */
public class FeedbackReqDTO {

    private Integer userId;

    private Byte type;

    private String description;

    private List<String> files;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
