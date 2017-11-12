package com.aik.dto.response.doctor;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by as on 2017/11/13.
 */
@Setter
@Getter
public class QuestionTipsRespDTO {

    private String headImg;
    private String name;
    private String sex;
    private String message;
    private String createDate;
    private Integer redNum;
    private Integer sickId;
    private Integer orderId;
    private Integer tipsId;
}
