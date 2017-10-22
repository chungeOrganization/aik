package com.aik.dao;

import com.aik.model.SysLogisticsCompany;

public interface SysLogisticsCompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLogisticsCompany record);

    int insertSelective(SysLogisticsCompany record);

    SysLogisticsCompany selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLogisticsCompany record);

    int updateByPrimaryKey(SysLogisticsCompany record);
}