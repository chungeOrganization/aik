package com.aik.dto.response.doctor;

/**
 * Description:
 * Created by as on 2017/10/20.
 */
public class UserInfoRespDTO {

    private Integer sickId;

    private String sickRealName;

    private Byte sickSex;

    public Integer getSickId() {
        return sickId;
    }

    public void setSickId(Integer sickId) {
        this.sickId = sickId;
    }

    public String getSickRealName() {
        return sickRealName;
    }

    public void setSickRealName(String sickRealName) {
        this.sickRealName = sickRealName;
    }

    public Byte getSickSex() {
        return sickSex;
    }

    public void setSickSex(Byte sickSex) {
        this.sickSex = sickSex;
    }

}
