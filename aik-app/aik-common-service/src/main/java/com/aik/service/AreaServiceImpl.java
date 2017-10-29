package com.aik.service;

import com.aik.dao.SysAreaTreeMapper;
import com.aik.dao.SysHospitalTreeMapper;
import com.aik.model.SysAreaTree;
import com.aik.model.SysHospitalTree;
import com.aik.vo.AreaVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
@Service
public class AreaServiceImpl implements AreaService {

    private static final Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    private SysAreaTreeMapper sysAreaTreeMapper;

    private SysHospitalTreeMapper sysHospitalTreeMapper;

    @Autowired
    public void setSysAreaTreeMapper(SysAreaTreeMapper sysAreaTreeMapper) {
        this.sysAreaTreeMapper = sysAreaTreeMapper;
    }

    @Autowired
    public void setSysHospitalTreeMapper(SysHospitalTreeMapper sysHospitalTreeMapper) {
        this.sysHospitalTreeMapper = sysHospitalTreeMapper;
    }

    @Override
    public Map<String, List<String>> getProvinceCity() {
        Map<String, List<String>> areaTree = new HashMap<>();

        List<SysAreaTree> provinces = sysAreaTreeMapper.selectByParent(0);
        for (SysAreaTree province : provinces) {
            List<SysAreaTree> cities = sysAreaTreeMapper.selectByParent(province.getId());
            List<String> cityNames = new ArrayList<>();
            for (SysAreaTree city : cities) {
                cityNames.add(city.getName());
            }

            areaTree.put(province.getName(), cityNames);
        }

        return areaTree;
    }

    @Override
    public List<AreaVO> getProvinceCityArea() {
        List<AreaVO> areas = new ArrayList<>();

        List<SysAreaTree> provinces = sysAreaTreeMapper.selectByParent(0);
        for (SysAreaTree area : provinces) {
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaId(area.getId());
            areaVO.setAreaName(area.getName());

            deepFindChildren(areaVO);
            areas.add(areaVO);
        }

        return areas;
    }

    private void deepFindChildren(AreaVO parentArea) {
        if (null == parentArea) {
            return;
        }

        List<SysAreaTree> areas = sysAreaTreeMapper.selectByParent(parentArea.getAreaId());
        if (null == areas || areas.size() == 0) {
            return;
        }

        for (SysAreaTree area : areas) {
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaId(area.getId());
            areaVO.setAreaName(area.getName());

            deepFindChildren(areaVO);
            parentArea.addChild(areaVO);
        }
    }

    @Override
    public Map<String, List<String>> getProvinceHospitals() {
        Map<String, List<String>> areaHosTree = new HashMap<>();

        List<SysAreaTree> provinces = sysAreaTreeMapper.selectByParent(0);
        for (SysAreaTree province : provinces) {
            List<SysHospitalTree> hospitals = sysHospitalTreeMapper.selectByAreaId(province.getId());
            List<String> hosNames = new ArrayList<>();
            for (SysHospitalTree hos : hospitals) {
                hosNames.add(hos.getName());
            }

            areaHosTree.put(province.getName(), hosNames);
        }

        return areaHosTree;
    }

    @Override
    public SysAreaTree getAreaByName(String areaName) {
        if (StringUtils.isBlank(areaName)) {
            return null;
        }

        return sysAreaTreeMapper.selectByName(areaName);
    }

}
