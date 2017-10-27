package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
import com.aik.vo.AikHealthRecordVo;
import com.aik.vo.StoUserOrderVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Description: 健康档案
 * Created by as on 2017/9/7.
 */
@Service
public class HealthRecordManageServiceImpl implements HealthRecordManageService {

    private static final Logger logger = LoggerFactory.getLogger(HealthRecordManageServiceImpl.class);

    private AikHealthRecordMapper aikHealthRecordMapper;

    @Autowired
    public void setAikHealthRecordMapper(AikHealthRecordMapper aikHealthRecordMapper) {
		this.aikHealthRecordMapper = aikHealthRecordMapper;
	}

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("健康档案删除,根据主键删除,主键为空");
            throw new Exception("健康档案删除,根据主键删除,主键为空");
        }
		aikHealthRecordMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void save(AikHealthRecord aikHealthRecord) throws Exception {
		if (null == aikHealthRecord) {
			logger.error("健康档案保存,根据对象保存,对象为空");
            throw new Exception("健康档案保存,根据对象保存,对象为空");
        }
		aikHealthRecord.setCreateDate(new Date());
		aikHealthRecordMapper.insert(aikHealthRecord);
		
	}

	@Override
	public void update(AikHealthRecord aikHealthRecord) throws Exception {
		if (null == aikHealthRecord || null == aikHealthRecord.getId()) {
			logger.error("健康档案修改,根据对象保存,对象为空");
            throw new Exception("健康档案修改,根据对象保存,对象为空");
        }

		AikHealthRecord aikHealthRecordOld = new AikHealthRecord();
		aikHealthRecordOld = aikHealthRecordMapper.selectByPrimaryKey(aikHealthRecord.getId());
		aikHealthRecordOld.setId(aikHealthRecord.getId());
		aikHealthRecordOld.setHeight(aikHealthRecord.getHeight());
		aikHealthRecordOld.setHwRecordDate(aikHealthRecord.getHwRecordDate());
		aikHealthRecordOld.setMedicalRecord(aikHealthRecord.getMedicalRecord());
		aikHealthRecordOld.setRemark(aikHealthRecord.getRemark());
		aikHealthRecordOld.setUserId(aikHealthRecord.getUserId());
		aikHealthRecordOld.setWeight(aikHealthRecord.getWeight());
		//TODO
		aikHealthRecordMapper.updateByPrimaryKeySelective(aikHealthRecordOld);
		
	}

	@Override
	public Page<AikHealthRecordVo> findPage(AikHealthRecordVo aikHealthRecordVo, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return aikHealthRecordMapper.findByPage(aikHealthRecordVo);
	}

	@Override
	public AikHealthRecord findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("健康档案查询,根据主键查询,主键为空");
            throw new Exception("健康档案查询,根据主键查询,主键为空");
        }
		return aikHealthRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<AikHealthRecordVo> findAll(AikHealthRecord aikHealthRecord)
			throws Exception {
		
		return aikHealthRecordMapper.findAll(aikHealthRecord);
	}



}
