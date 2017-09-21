package com.aik.service.question;

import com.aik.dao.SysIllTypeTreeMapper;
import com.aik.exception.ApiServiceException;
import com.aik.model.SysIllTypeTree;
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
 * Created by as on 2017/9/9.
 */
@Service
public class IllTypeServiceImpl implements IllTypeService {

    private static final Logger logger = LoggerFactory.getLogger(IllTypeServiceImpl.class);

    private static final int DEFAULT_PARENT_ID = 0;

    private SysIllTypeTreeMapper sysIllTypeTreeMapper;

    @Autowired
    public void setSysIllTypeTreeMapper(SysIllTypeTreeMapper sysIllTypeTreeMapper) {
        this.sysIllTypeTreeMapper = sysIllTypeTreeMapper;
    }

    @Override
    public List<Map<String, Object>> getIllTypeTree() throws ApiServiceException {
        List<Map<String, Object>> illTypeTree = new ArrayList<>();

        List<SysIllTypeTree> illTypes = sysIllTypeTreeMapper.selectByParentId(DEFAULT_PARENT_ID);
        for (SysIllTypeTree illType : illTypes) {
            Map<String, Object> illTypeMap = new HashMap<>();
            illTypeMap.put("illTypeId", illType.getId());
            illTypeMap.put("illTypeName", illType.getTypeName());

            List<Map<String, Object>> childIllTypeList = new ArrayList<>();
            List<SysIllTypeTree> childIllTypes = sysIllTypeTreeMapper.selectByParentId(illType.getId());
            for (SysIllTypeTree childIllType : childIllTypes) {
                Map<String, Object> childIllTypeMap = new HashMap<>();
                childIllTypeMap.put("illTypeId", childIllType.getId());
                childIllTypeMap.put("illTypeName", childIllType.getTypeName());

                childIllTypeList.add(childIllTypeMap);
            }
            illTypeMap.put("childIllTypes", childIllTypeList);

            illTypeTree.add(illTypeMap);
        }

        return illTypeTree;
    }
}
