package com.aik.dto.response.user;

/**
 * Description:
 * Created by as on 2017/10/22.
 */
public class GetAttentionDoctorRespDTO extends DoctorInfoRespDTO{

    private Byte relation;

    private Boolean isAttending;

    public Byte getRelation() {
        return relation;
    }

    public void setRelation(Byte relation) {
        this.relation = relation;
    }

    public Boolean getAttending() {
        return isAttending;
    }

    public void setAttending(Boolean attending) {
        isAttending = attending;
    }
}
