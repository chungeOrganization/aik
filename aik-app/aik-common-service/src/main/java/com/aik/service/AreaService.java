package com.aik.service;

import com.aik.model.SysAreaTree;
import com.aik.vo.AreaVO;

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

    /**
     * 获取地区
     *
     * @return 省市区map
     */
    List<AreaVO> getProvinceCityArea();

    /**
     * 获取省-医院
     *
     * @return 省-医院
     */
    Map<String, List<String>> getProvinceHospitals();

    SysAreaTree getAreaByName(String areaName);
}
