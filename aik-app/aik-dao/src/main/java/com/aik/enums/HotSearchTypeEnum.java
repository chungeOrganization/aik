package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public enum HotSearchTypeEnum {
    DOCTOR_MY_ANSWER_SEARCH(1, "医生端-我的回答-搜索");

    private Integer type;

    private String desc;

    HotSearchTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
