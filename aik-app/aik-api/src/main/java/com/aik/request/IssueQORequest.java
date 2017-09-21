package com.aik.request;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/10.
 */
public class IssueQORequest {

    private String description;

    private List<String> orderFiles;

    private Byte illType;

    private String illName;

    private Byte type;

    private Integer doctorId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOrderFiles() {
        return orderFiles;
    }

    public void setOrderFiles(List<String> orderFiles) {
        this.orderFiles = orderFiles;
    }

    public Byte getIllType() {
        return illType;
    }

    public void setIllType(Byte illType) {
        this.illType = illType;
    }

    public String getIllName() {
        return illName;
    }

    public void setIllName(String illName) {
        this.illName = illName;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }
}
