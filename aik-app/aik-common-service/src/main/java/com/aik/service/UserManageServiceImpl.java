package com.aik.service;

import com.aik.dao.AccUserAccountMapper;
import com.aik.model.AccUserAccount;
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
public class UserManageServiceImpl implements UserManageService {

    private static final Logger logger = LoggerFactory.getLogger(UserManageServiceImpl.class);

    private AccUserAccountMapper accUserAccountMapper;

    @Autowired
    public void setAccUserAccountMapper(AccUserAccountMapper accUserAccountMapper) {
        this.accUserAccountMapper = accUserAccountMapper;
    }

    @Override
	public AccUserAccount findByPrimaryKey(Integer id) throws Exception {
    	if (null == id) {
    		logger.error("用户查询,根据主键查询,主键为空");
            throw new Exception("用户查询,根据主键查询,主键为空");
        }

        return accUserAccountMapper.selectByPrimaryKey(id);
	}
    
    @Override
    public void deleteByPrimaryKey(Integer id) throws Exception {
        if (null == id) {
            logger.error("用户删除,根据主键删除,主键为空");
            throw new Exception("用户删除,根据主键删除,主键为空");
        }
        accUserAccountMapper.deleteByPrimaryKey(id);
    }

	@Override
	public void save(AccUserAccount accUserAccount) throws Exception {
		if (null == accUserAccount) {
			logger.error("用户保存,根据对象保存,对象为空");
            throw new Exception("用户保存,根据对象保存,对象为空");
        }
		//密码MD5加密
		String passWord = accUserAccount.getPassword();
		accUserAccount.setPassword(MD5Utils.md5(passWord));
		accUserAccount.setCreateDate(new Date());
		accUserAccountMapper.insert(accUserAccount);
	}
	
	
	
	@Override
    public void update(AccUserAccount accUserAccount) throws Exception {
        if (null == accUserAccount || null == accUserAccount.getId()) {
        	logger.error("用户修改,根据对象修改,对象为空");
            throw new Exception("用户修改,根据对象修改,对象为空");
        }

        AccUserAccount userAccountOld = new AccUserAccount();
        userAccountOld = accUserAccountMapper.selectByPrimaryKey(accUserAccount.getId());
        //userAccount.setId(userInfoDTO.getAccountId());
        userAccountOld.setHeadImg(accUserAccount.getHeadImg());
        userAccountOld.setNickName(accUserAccount.getNickName());
        userAccountOld.setSex(accUserAccount.getSex());
        userAccountOld.setBirthday(accUserAccount.getBirthday());
        userAccountOld.setAreaProvince(accUserAccount.getAreaProvince());
        userAccountOld.setAreaCity(accUserAccount.getAreaCity());
        userAccountOld.setIsElseIllness(accUserAccount.getIsElseIllness());
        userAccountOld.setUpdateDate(new Date());
        userAccountOld.setDeleteStatus(accUserAccount.getDeleteStatus());
        accUserAccountMapper.updateByPrimaryKeySelective(userAccountOld);
    }

	@Override
	public Page<AccUserAccount> findPage(AccUserAccountVo accUserAccountVo, PageUtils pgeUtils) throws  Exception {
		 PageHelper.startPage(pgeUtils.getPage(), pgeUtils.getSize());
		 return accUserAccountMapper.findByPage(accUserAccountVo);
	}

	@Override
	public List<AccUserAccount> findAll(AccUserAccount accUserAccount)
			throws Exception {
		return accUserAccountMapper.findAll(accUserAccount);
	}

	@Override
	public AccUserAccount selectByUserName(String userName) throws Exception {
		if (null == userName || "".equals(userName)) {
        	logger.error("用户查询,根据用户名,用户名为空");
            throw new Exception("用户查询,根据用户名,用户为空");
        }
		AccUserAccount accUserAccount = accUserAccountMapper.selectByUserName(userName);
		return accUserAccount;
	}



	

	
	
}
