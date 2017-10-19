package com.aik.service;

import java.util.List;

import com.aik.model.*;
import com.aik.util.PageUtils;
import com.aik.vo.AccDoctorAccountVo;
import com.github.pagehelper.Page;


/**
 * Description: 医生管理服务
 * Created by daixiangning
 */
public interface DoctorManageService {

	/**
     * 获取医生账户信息
     *
     * @param id 用户id
     * @return 医生账户
     * @throws Exception 异常
     */
	AccDoctorAccount findByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 根据主键删除医生信息
     * @param id 用户id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增医生信息
     * @param accDoctorAccount 用户信息
     * @throws Exception 异常
     */
    void save(AccDoctorAccount accDoctorAccount) throws Exception;
    
    /**
     * 修改医生信息
     * @param accDoctorAccount 医生信息
     * @throws Exception 异常
     */
    void update(AccDoctorAccount accDoctorAccount) throws  Exception;
    
    /**
     * 查询医生信息
     * @param accDoctorAccount
     * @return
     * @throws Exception
     */
    List<AccDoctorAccount> findAll(AccDoctorAccount accDoctorAccount) throws Exception;
    
    /**
     *  医生信息分页查询
     * @param accDoctorAccount
     * @param pageUtils
     * @return
     * @throws Exception
     */
    Page<AccDoctorAccount> findPage(AccDoctorAccountVo accDoctorAccountVo, PageUtils pageUtils) throws  Exception;
    
}
