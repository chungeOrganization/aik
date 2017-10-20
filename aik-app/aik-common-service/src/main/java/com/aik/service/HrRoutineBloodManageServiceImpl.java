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
 * Description: 健康档案-血常规指标类
 * Created by as on 2017/9/7.
 */
@Service
public class HrRoutineBloodManageServiceImpl implements HrRoutineBloodManageService {

    private static final Logger logger = LoggerFactory.getLogger(HrRoutineBloodManageServiceImpl.class);
    
    private AikHrRoutineBloodMapper aikHrRoutineBloodMapper;

    @Autowired
    public void setAikHrRoutineBloodMapper(
			AikHrRoutineBloodMapper aikHrRoutineBloodMapper) {
		this.aikHrRoutineBloodMapper = aikHrRoutineBloodMapper;
	}

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("健康档案-血常规指标类删除,根据主键删除,主键为空");
            throw new Exception("健康档案-血常规指标类删除,根据主键删除,主键为空");
        }
		aikHrRoutineBloodMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void save(AikHrRoutineBlood aikHrRoutineBlood) throws Exception {
		if (null == aikHrRoutineBlood) {
			logger.error("健康档案-血常规指标类保存,根据对象保存,对象为空");
            throw new Exception("健康档案-血常规指标类保存,根据对象保存,对象为空");
        }
		aikHrRoutineBlood.setCreateDate(new Date());
		aikHrRoutineBloodMapper.insert(aikHrRoutineBlood);
		
	}

	@Override
	public void update(AikHrRoutineBlood aikHrRoutineBlood) throws Exception {
		if (null == aikHrRoutineBlood || null == aikHrRoutineBlood.getId()) {
			logger.error("健康档案-血常规指标类修改,根据对象保存,对象为空");
            throw new Exception("健康档案-血常规指标类修改,根据对象保存,对象为空");
        }

		AikHrRoutineBlood aikHrRoutineBloodOld = new AikHrRoutineBlood();
		aikHrRoutineBloodOld = aikHrRoutineBloodMapper.selectByPrimaryKey(aikHrRoutineBlood.getId());
		//TODO
		aikHrRoutineBloodMapper.updateByPrimaryKeySelective(aikHrRoutineBloodOld);
		
	}


	@Override
	public AikHrRoutineBlood findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("健康档案-血常规指标类查询,根据主键查询,主键为空");
            throw new Exception("健康档案-血常规指标类查询,根据主键查询,主键为空");
        }
		return aikHrRoutineBloodMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<AikHrRoutineBlood> findAll(AikHrRoutineBlood aikHrRoutineBlood)
			throws Exception {
		return aikHrRoutineBloodMapper.findAll(aikHrRoutineBlood);
	}



}
