package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.MD5Utils;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
@Service
public class DoctorManageServiceImpl implements DoctorManageService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorManageServiceImpl.class);

    private AccDoctorAccountMapper accDoctorAccountMapper;

    @Autowired
    public void setAccDoctorAccountMapper(AccDoctorAccountMapper accDoctorAccountMapper) {
        this.accDoctorAccountMapper = accDoctorAccountMapper;
    }
    
    @Override
	public AccDoctorAccount findByPrimaryKey(Integer id) throws Exception {
    	if (null == id) {
    		logger.error("医生查询,根据主键查询,主键为空");
            throw new Exception("医生查询,根据主键查询,主键为空");
        }
		return accDoctorAccountMapper.selectByPrimaryKey(id);
	}


	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("医生删除,根据主键删除,主键为空");
            throw new Exception("医生删除,根据主键删除,主键为空");
        }
		 accDoctorAccountMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void save(AccDoctorAccount accDoctorAccount) throws Exception {
		if (null == accDoctorAccount) {
			logger.error("医生保存,根据对象保存,对象为空");
            throw new Exception("医生保存,根据对象保存,对象为空");
        }
		//密码MD5加密
		String passWord = accDoctorAccount.getPassword();
		accDoctorAccount.setPassword(MD5Utils.md5(passWord));
		accDoctorAccount.setCreateDate(new Date());
		accDoctorAccountMapper.insert(accDoctorAccount);
		
	}

	@Override
	public void update(AccDoctorAccount accDoctorAccount) throws Exception {
		if (null == accDoctorAccount || null == accDoctorAccount.getId()) {
			logger.error("医生修改,根据对象修改,对象为空");
            throw new Exception("医生修改,根据对象修改,对象为空");
        }

		AccDoctorAccount doctorAccountOld = new AccDoctorAccount();
		doctorAccountOld = accDoctorAccountMapper.selectByPrimaryKey(accDoctorAccount.getId());
		doctorAccountOld.setRealName(accDoctorAccount.getRealName());
		doctorAccountOld.setSex(accDoctorAccount.getSex());
		doctorAccountOld.setHeadImg(accDoctorAccount.getHeadImg());
        doctorAccountOld.setAreaProvince(accDoctorAccount.getAreaProvince());
        doctorAccountOld.setAreaCity(accDoctorAccount.getAreaCity());
        doctorAccountOld.setBirthday(accDoctorAccount.getBirthday());
        doctorAccountOld.setIdentityCard(accDoctorAccount.getIdentityCard());
        doctorAccountOld.setEmail(accDoctorAccount.getEmail());
        doctorAccountOld.setHosName(accDoctorAccount.getHosName());
        doctorAccountOld.setHosDepartment(accDoctorAccount.getHosDepartment());
        doctorAccountOld.setSkill(accDoctorAccount.getSkill());
        doctorAccountOld.setDepartmentPhone(accDoctorAccount.getDepartmentPhone());
        doctorAccountOld.setUpdateDate(new Date());
        accDoctorAccountMapper.updateByPrimaryKeySelective(doctorAccountOld);
		
	}

	@Override
	public Page<AccDoctorAccount> findPage(AccDoctorAccount accDoctorAccount,
			PageUtils pageUtils) throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return accDoctorAccountMapper.findByPage(accDoctorAccount);
	}

	

	@Override
	public List<AccDoctorAccount> findAll(AccDoctorAccount accDoctorAccount) throws Exception {
		return accDoctorAccountMapper.findAll(accDoctorAccount);
	}
}
