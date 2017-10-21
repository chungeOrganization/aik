package com.aik.dto.response.doctor;

/**
 * Description:
 * Created by as on 2017/10/20.
 */
public class SickListRespDTO extends UserInfoRespDTO {

    private Integer sickId;

    private String sickNickName;

    public Integer getSickId() {
        return sickId;
    }

    public void setSickId(Integer sickId) {
        this.sickId = sickId;
    }

    public String getSickNickName() {
        return sickNickName;
    }

    public void setSickNickName(String sickNickName) {
        this.sickNickName = sickNickName;
    }
}
