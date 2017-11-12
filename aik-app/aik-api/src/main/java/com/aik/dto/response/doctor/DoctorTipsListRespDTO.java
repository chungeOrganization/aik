package com.aik.dto.response.doctor;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/11/13.
 */
@Getter
@Setter
public class DoctorTipsListRespDTO {
    private FriendTipsRespDTO friendTips;
    private List<QuestionTipsRespDTO> questionTipsList;
}
