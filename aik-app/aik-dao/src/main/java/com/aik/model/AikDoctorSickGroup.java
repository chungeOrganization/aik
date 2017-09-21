package com.aik.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class AikDoctorSickGroup {
    private Integer id;

    private Integer doctorId;

    private String groupName;

    @JsonIgnore
    private Date createDate;

    public AikDoctorSickGroup() {
    }

    public AikDoctorSickGroup(Integer id, Integer doctorId, String groupName) {
        this.id = id;
        this.doctorId = doctorId;
        this.groupName = groupName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}