package com.aik.dto.response.user;

/**
 * Description:
 * Created by as on 2017/10/23.
 */
public class QuestionViewDetailRespDTO extends QuestionOrderDetailRespDTO {

    private Integer viewCount;

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
