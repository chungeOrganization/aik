package com.aik.service;

import com.aik.model.SysAreaTree;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
public interface AreaService {

    /**
     * 获取省市
     *
     * @return 省市map
     */
    Map<String, List<String>> getProvinceCity();

    SysAreaTree getAreaByName(String areaName);
}
