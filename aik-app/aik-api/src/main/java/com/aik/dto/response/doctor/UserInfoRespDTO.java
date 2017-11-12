package com.aik.dto.response.doctor;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2017/10/20.
 */
@Setter
@Getter
public class UserInfoRespDTO {

    private Integer userId;

    private String userRealName;

    private String userHeadImg;

    private Integer userSex;
}
