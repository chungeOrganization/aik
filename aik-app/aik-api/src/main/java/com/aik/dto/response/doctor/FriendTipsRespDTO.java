package com.aik.dto.response.doctor;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Description:
 * Created by as on 2017/11/13.
 */
@Getter
@Setter
public class FriendTipsRespDTO {
    private Integer redNum;
    private String headImg;
    private String message;
    private String tipsTime;
}
