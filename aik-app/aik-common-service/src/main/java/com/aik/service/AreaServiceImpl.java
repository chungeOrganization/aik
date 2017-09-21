package com.aik.service;

import com.aik.dao.SysAreaTreeMapper;
import com.aik.model.SysAreaTree;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public void setSysAreaTreeMapper(SysAreaTreeMapper sysAreaTreeMapper) {
        this.sysAreaTreeMapper = sysAreaTreeMapper;
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
    public SysAreaTree getAreaByName(String areaName) {
        if (StringUtils.isBlank(areaName)) {
            return null;
        }

        return sysAreaTreeMapper.selectByName(areaName);
    }

}
