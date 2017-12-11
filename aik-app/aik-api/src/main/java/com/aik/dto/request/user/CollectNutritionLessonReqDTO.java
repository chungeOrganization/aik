package com.aik.dto.request.user;

/**
 * Description:
 * Created by as on 2017/12/7.
 */
public class CollectNutritionLessonReqDTO {
    private Integer id;
    private Boolean collect;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
