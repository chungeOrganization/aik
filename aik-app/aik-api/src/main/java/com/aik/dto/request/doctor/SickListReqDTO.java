package com.aik.dto.request.doctor;

import com.aik.dto.request.PageReqDTO;

/**
 * Description:
 * Created by as on 2017/10/20.
 */
public class SickListReqDTO extends PageReqDTO{

    private Integer doctorId;

    private Byte groupId;

    private String nickName;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Byte getGroupId() {
        return groupId;
    }

    public void setGroupId(Byte groupId) {
        this.groupId = groupId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
