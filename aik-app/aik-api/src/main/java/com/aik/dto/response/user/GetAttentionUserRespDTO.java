package com.aik.dto.response.user;

/**
 * Description:
 * Created by as on 2017/10/22.
 */
public class GetAttentionUserRespDTO extends UserInfoRespDTO{

    private Byte relation;

    private String content;

    public Byte getRelation() {
        return relation;
    }

    public void setRelation(Byte relation) {
        this.relation = relation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
