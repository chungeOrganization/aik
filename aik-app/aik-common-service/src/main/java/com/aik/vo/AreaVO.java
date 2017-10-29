package com.aik.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/10/29.
 */
public class AreaVO {

    private Integer areaId;

    private String areaName;

    private List<AreaVO> childAreas;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<AreaVO> getChildAreas() {
        return childAreas;
    }

    public void setChildAreas(List<AreaVO> childAreas) {
        this.childAreas = childAreas;
    }

    public void addChild(AreaVO child) {
        if (null == this.childAreas) {
            this.childAreas = new ArrayList<>();
        }

        this.childAreas.add(child);
    }
}
