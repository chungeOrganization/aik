package com.aik.dto.response.doctor;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: 患者列表
 * Created by as on 2017/10/20.
 */
@Setter
@Getter
public class SickListRespDTO extends UserInfoRespDTO {

    private Integer sickId;

    private String sickNickName;

    private String sickQuestion;
}
