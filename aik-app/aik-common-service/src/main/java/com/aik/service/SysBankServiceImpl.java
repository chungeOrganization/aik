package com.aik.service;

import com.aik.dao.SysBankMapper;
import com.aik.model.SysBank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
@Service
public class SysBankServiceImpl implements SysBankService {

    private static final Logger logger = LoggerFactory.getLogger(SysBankServiceImpl.class);

    private SysBankMapper sysBankMapper;

    @Autowired
    public void setSysBankMapper(SysBankMapper sysBankMapper) {
        this.sysBankMapper = sysBankMapper;
    }

    @Override
    public List<SysBank> getSysbankList() {
        return sysBankMapper.selectAllBank();
    }
}
