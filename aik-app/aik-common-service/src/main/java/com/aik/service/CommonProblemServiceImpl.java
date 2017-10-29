package com.aik.service;

import com.aik.dao.AikCommonProblemMapper;
import com.aik.model.AikCommonProblem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
@Service
public class CommonProblemServiceImpl implements CommonProblemService {

    private static final Logger logger = LoggerFactory.getLogger(CommonProblemServiceImpl.class);

    private AikCommonProblemMapper aikCommonProblemMapper;

    @Autowired
    public void setAikCommonProblemMapper(AikCommonProblemMapper aikCommonProblemMapper) {
        this.aikCommonProblemMapper = aikCommonProblemMapper;
    }

    @Override
    public List<Map<String, Object>> getCommonProblems(Map<String, Object> params) {
        return aikCommonProblemMapper.selectCommonProblemsByParams(params);
    }

    @Override
    public AikCommonProblem getCommonProblemDetail(Integer problemId) {
        return aikCommonProblemMapper.selectByPrimaryKey(problemId);
    }
}
