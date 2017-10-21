package com.aik.dto.response.doctor;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/10/21.
 */
public class QuestionRespDTO {

    private String description;

    private Date createDate;

    private List<String> files;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
