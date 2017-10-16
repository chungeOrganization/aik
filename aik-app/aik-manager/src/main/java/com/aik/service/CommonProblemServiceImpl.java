package com.aik.service;

import com.aik.dao.AikCommonProblemMapper;
import com.aik.model.AikCommonProblem;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description:
 * Created by as on 2017/10/15.
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
    public Page<AikCommonProblem> findPage(AikCommonProblem commonProblem) {
        return aikCommonProblemMapper.selectByPage(commonProblem);
    }

    @Override
    public AikCommonProblem findCommonProblem(Integer id) {
        return aikCommonProblemMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean editCommonProblem(AikCommonProblem commonProblem) {
        if (null == commonProblem || null == commonProblem.getId()) {
            return false;
        }

        commonProblem.setUpdateDate(new Date());

        return aikCommonProblemMapper.updateByPrimaryKeySelective(commonProblem) > 0;
    }

    @Override
    public boolean addCommonProblem(AikCommonProblem commonProblem) {
        if (null == commonProblem) {
            return false;
        }

        commonProblem.setCreateDate(new Date());

        return aikCommonProblemMapper.insertSelective(commonProblem) > 0;
    }

    @Override
    public boolean deleteCommonProblem(Integer id) {
        if (null == id) {
            return false;
        }

        return aikCommonProblemMapper.deleteByPrimaryKey(id) > 0;
    }
}
