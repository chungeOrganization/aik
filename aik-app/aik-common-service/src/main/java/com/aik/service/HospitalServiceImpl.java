package com.aik.service;

import com.aik.dao.SysHospitalTreeMapper;
import com.aik.model.SysAreaTree;
import com.aik.model.SysHospitalTree;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    private static final Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

    private SysHospitalTreeMapper sysHospitalTreeMapper;

    private AreaService areaService;

    @Autowired
    public void setSysHospitalTreeMapper(SysHospitalTreeMapper sysHospitalTreeMapper) {
        this.sysHospitalTreeMapper = sysHospitalTreeMapper;
    }

    @Autowired
    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
    }

    @Override
    public List<String> getHospitalByArea(String areaName) {
        List<String> hospitals = new ArrayList<>();
        if (StringUtils.isBlank(areaName)) {
            return hospitals;
        }

        SysAreaTree area = areaService.getAreaByName(areaName);
        if (null == area) {
            return hospitals;
        }

        List<SysHospitalTree> hospitalTrees = sysHospitalTreeMapper.selectByAreaId(area.getId());
        for (SysHospitalTree hospitalTree : hospitalTrees) {
            hospitals.add(hospitalTree.getName());
        }

        return hospitals;
    }

    @Override
    public List<String> getDepartmentByHospital(String hospitalName) {
        List<String> departmentNames = new ArrayList<>();
        if (StringUtils.isBlank(hospitalName)) {
            return departmentNames;
        }

        SysHospitalTree hospital = sysHospitalTreeMapper.selectByName(hospitalName);
        if (null == hospital) {
            return departmentNames;
        }

        List<SysHospitalTree> departments = sysHospitalTreeMapper.selectByParent(hospital.getId());
        for (SysHospitalTree department : departments) {
            departmentNames.add(department.getName());
        }

        return departmentNames;
    }
}
