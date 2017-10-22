package com.aik.dto.request.user;

import com.aik.dto.request.PageReqDTO;

/**
 * Description:
 * Created by as on 2017/10/22.
 */
public class GetAttentionListReqDTO extends PageReqDTO{

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
