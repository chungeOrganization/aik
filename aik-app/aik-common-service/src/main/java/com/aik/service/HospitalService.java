package com.aik.service;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
public interface HospitalService {

    List<String> getHospitalByArea(String areaName);

    List<String> getDepartmentByHospital(String hospitalName);
}
