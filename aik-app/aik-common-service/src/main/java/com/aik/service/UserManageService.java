package com.aik.service;


import java.util.List;
import java.util.Map;

import com.aik.model.AccUserAccount;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;

/**
 * Description:
 * Created by daixiangning
 */
public interface UserManageService {
	
	/**
     * 获取用户账户信息
     * @param id 用户id
     * @return 用户账户
     * @throws ApiServiceException Api服务异常
     */
    AccUserAccount findByPrimaryKey(Integer id) throws Exception;
   
    
    /**
     * 根据主键删除用户信息
     * @param id 用户id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增用户信息
     * @param userInfoDTO 用户信息
     * @throws Exception 异常
     */
    void save(AccUserAccount accUserAccount) throws Exception;
    
    /**
     * 修改用户信息
     * @param userInfoDTO 用户信息
     * @throws Exception 异常
     */
    void update(AccUserAccount accUserAccount) throws  Exception;
    
    /**
     * 根据用户对象查询所有记录
     * @param accUserAccount
     * @return
     * @throws Exception
     */
    List<AccUserAccount> findAll(AccUserAccount accUserAccount) throws  Exception;
    
   /**
    * 用户信息分页查询
    * @param accUserAccount
    * @param pageParam
    * @return
    * @throws Exception
    */
    Page<AccUserAccount> findPage(AccUserAccount accUserAccount, PageUtils pageParam) throws  Exception;
    
    /**
     * 根据用户名查找用户
     * @param userName
     * @return
     * @throws Exception
     */
    AccUserAccount selectByUserName(String userName)throws  Exception;
    
}
