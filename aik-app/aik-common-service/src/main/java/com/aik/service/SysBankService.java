package com.aik.service;

import com.aik.model.SysBank;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
public interface SysBankService {

    /**
     * 获取银行信息
     *
     * @return 银行信息列表
     */
    List<SysBank> getSysbankList();
}
