package com.aik.dao;

import com.aik.model.AccDoctorAccount;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AccDoctorAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccDoctorAccount record);

    int insertSelective(AccDoctorAccount record);

    AccDoctorAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccDoctorAccount record);

    int updateByPrimaryKey(AccDoctorAccount record);

    List<AccDoctorAccount> selectBySelective(AccDoctorAccount record);

    AccDoctorAccount selectByMobileNo(String mobileNo);

    AccDoctorAccount selectByUserNameAndPwd(@Param("userName") String userName, @Param("password") String password);

    AccDoctorAccount selectByUserName(@Param("userName") String userName);

    List<AccDoctorAccount> selectByParams(Map<String, Object> params);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AccDoctorAccount> findAll(AccDoctorAccount record);

    /**
     * 分页查询数据
     * @return
     */
    Page<AccDoctorAccount> findByPage(AccDoctorAccount record);
    
}