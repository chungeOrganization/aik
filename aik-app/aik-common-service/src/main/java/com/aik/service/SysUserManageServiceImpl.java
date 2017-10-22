package com.aik.service;

import com.aik.dao.AccUserAccountMapper;
import com.aik.dao.SysUserMapper;
import com.aik.model.AccUserAccount;
import com.aik.model.SysUser;
import com.aik.util.MD5Utils;
import com.aik.util.PageUtils;
import com.aik.vo.AccUserAccountVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description: 用户管理服务
 * Created by as on 2017/8/6.
 */
@Service
public class SysUserManageServiceImpl implements SysUserManageService {

    private static final Logger logger = LoggerFactory.getLogger(SysUserManageServiceImpl.class);

    private SysUserMapper sysUserMapper;

    @Autowired
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

    @Override
	public SysUser findByPrimaryKey(Long id) throws Exception {
    	if (null == id) {
    		logger.error("用户查询,根据主键查询,主键为空");
            throw new Exception("用户查询,根据主键查询,主键为空");
        }

        return sysUserMapper.selectByPrimaryKey(id);
	}
    
   

	@Override
    public void deleteByPrimaryKey(Long id) throws Exception {
        if (null == id) {
            logger.error("用户删除,根据主键删除,主键为空");
            throw new Exception("用户删除,根据主键删除,主键为空");
        }
        sysUserMapper.deleteByPrimaryKey(id);
    }

	@Override
	public void save(SysUser sysUser) throws Exception {
		if (null == sysUser) {
			logger.error("用户保存,根据对象保存,对象为空");
            throw new Exception("用户保存,根据对象保存,对象为空");
        }
		//密码MD5加密
		String passWord = sysUser.getPassword();
		sysUser.setPassword(MD5Utils.md5(passWord));
		sysUser.setCreateDate(new Date());
		sysUserMapper.insert(sysUser);
	}
	
	
	
	@Override
    public void update(SysUser sysUser) throws Exception {
        if (null == sysUser || null == sysUser.getId()) {
        	logger.error("用户修改,根据对象修改,对象为空");
            throw new Exception("用户修改,根据对象修改,对象为空");
        }

        SysUser sysUserOld = new SysUser();
        sysUserOld = sysUserMapper.selectByPrimaryKey(sysUser.getId());
        //userAccount.setId(userInfoDTO.getAccountId());
        sysUserOld.setDeleteStatus(sysUser.getDeleteStatus());
        sysUserMapper.updateByPrimaryKeySelective(sysUserOld);
    }

	@Override
	public Page<SysUser> findPage(SysUser sysUser, PageUtils pgeUtils) throws  Exception {
		 PageHelper.startPage(pgeUtils.getPage(), pgeUtils.getSize());
		 return sysUserMapper.findByPage(sysUser);
	}

	@Override
	public List<SysUser> findAll(SysUser sysUser)
			throws Exception {
		return sysUserMapper.findAll(sysUser);
	}

	@Override
	public SysUser selectByUserName(String userName) throws Exception {
		if (null == userName || "".equals(userName)) {
        	logger.error("用户查询,根据用户名,用户名为空");
            throw new Exception("用户查询,根据用户名,用户为空");
        }
		SysUser sysUser = sysUserMapper.selectByUserName(userName);
		return sysUser;
	}



	

	
	
}
