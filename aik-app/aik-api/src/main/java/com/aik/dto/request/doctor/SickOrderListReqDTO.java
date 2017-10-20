package com.aik.dto.request.doctor;

import com.aik.dto.request.PageReqDTO;

/**
 * Description:
 * Created by as on 2017/10/20.
 */
public class SickOrderListReqDTO extends PageReqDTO{

    private Integer sickId;

    public Integer getSickId() {
        return sickId;
    }

    public void setSickId(Integer sickId) {
        this.sickId = sickId;
    }
}
