package com.aik.dto.response.doctor;

/**
 * Description:
 * Created by as on 2017/10/20.
 */
public class SickListRespDTO extends UserInfoRespDTO {

    private Integer sickId;

    @Override
    public Integer getSickId() {
        return sickId;
    }

    @Override
    public void setSickId(Integer sickId) {
        this.sickId = sickId;
    }
}
