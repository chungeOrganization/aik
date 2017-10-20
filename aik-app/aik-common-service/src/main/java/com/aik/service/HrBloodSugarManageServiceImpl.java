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
 * Description: 健康档案-血糖类
 * Created by as on 2017/9/7.
 */
@Service
public class HrBloodSugarManageServiceImpl implements HrBloodSugarManageService {

    private static final Logger logger = LoggerFactory.getLogger(HrBloodSugarManageServiceImpl.class);
    
    private AikHrBloodSugarMapper aikHrBloodSugarMapper;

    @Autowired
    public void setAikHrBloodSugarMapper(AikHrBloodSugarMapper aikHrBloodSugarMapper) {
		this.aikHrBloodSugarMapper = aikHrBloodSugarMapper;
	}

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("健康档案-血糖类删除,根据主键删除,主键为空");
            throw new Exception("健康档案-血糖类删除,根据主键删除,主键为空");
        }
		aikHrBloodSugarMapper.deleteByPrimaryKey(id);
		
	}

	

	@Override
	public void save(AikHrBloodSugar aikHrBloodSugar) throws Exception {
		if (null == aikHrBloodSugar) {
			logger.error("健康档案-血糖类保存,根据对象保存,对象为空");
            throw new Exception("健康档案-血糖类保存,根据对象保存,对象为空");
        }
		aikHrBloodSugar.setCreateDate(new Date());
		aikHrBloodSugarMapper.insert(aikHrBloodSugar);
		
	}

	@Override
	public void update(AikHrBloodSugar aikHrBloodSugar) throws Exception {
		if (null == aikHrBloodSugar || null == aikHrBloodSugar.getId()) {
			logger.error("健康档案-血糖类修改,根据对象保存,对象为空");
            throw new Exception("健康档案-血糖类修改,根据对象保存,对象为空");
        }

		AikHrBloodSugar aikHrBloodSugarOld = new AikHrBloodSugar();
		aikHrBloodSugarOld = aikHrBloodSugarMapper.selectByPrimaryKey(aikHrBloodSugar.getId());
		//TODO
		aikHrBloodSugarMapper.updateByPrimaryKeySelective(aikHrBloodSugarOld);
		
	}


	@Override
	public AikHrBloodSugar findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("健康档案-血糖类查询,根据主键查询,主键为空");
            throw new Exception("健康档案-血糖类查询,根据主键查询,主键为空");
        }
		return aikHrBloodSugarMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<AikHrBloodSugar> findAll(AikHrBloodSugar aikHrBloodSugar)
			throws Exception {
		return aikHrBloodSugarMapper.findAll(aikHrBloodSugar);
	}



}
