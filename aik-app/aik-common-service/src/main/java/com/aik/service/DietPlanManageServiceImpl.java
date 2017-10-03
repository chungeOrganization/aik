package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
import com.aik.vo.DietDailyDietPlanVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Description: 饮食计划
 * Created by as on 2017/9/7.
 */
@Service
public class DietPlanManageServiceImpl implements DietPlanManageService {

    private static final Logger logger = LoggerFactory.getLogger(DietPlanManageServiceImpl.class);

    private DietDailyDietPlanMapper dietDailyPlanRecordMapper;

    @Autowired
    public void setDietDailyPlanRecordMapper(
			DietDailyDietPlanMapper dietDailyPlanRecordMapper) {
		this.dietDailyPlanRecordMapper = dietDailyPlanRecordMapper;
	}


    

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("饮食计划删除,根据主键删除,主键为空");
            throw new Exception("饮食计划删除,根据主键删除,主键为空");
        }
		dietDailyPlanRecordMapper.deleteByPrimaryKey(id);
		
	}



	



	@Override
	public void save(DietDailyDietPlan dietDailyDietPlan) throws Exception {
		if (null == dietDailyDietPlan) {
			logger.error("饮食计划保存,根据对象保存,对象为空");
            throw new Exception("饮食计划保存,根据对象保存,对象为空");
        }
		dietDailyDietPlan.setCreateDate(new Date());
		dietDailyPlanRecordMapper.insert(dietDailyDietPlan);
		
	}

	@Override
	public void update(DietDailyDietPlan dietDailyDietPlan) throws Exception {
		if (null == dietDailyDietPlan || null == dietDailyDietPlan.getId()) {
			logger.error("饮食计划修改,根据对象保存,对象为空");
            throw new Exception("饮食计划修改,根据对象保存,对象为空");
        }
		DietDailyDietPlan dietDailyDietPlanOld = new DietDailyDietPlan();
		dietDailyDietPlanOld = dietDailyPlanRecordMapper.selectByPrimaryKey(dietDailyDietPlan.getId());
		dietDailyDietPlanOld.setUserId(dietDailyDietPlan.getUserId());
		//TODO
		dietDailyPlanRecordMapper.updateByPrimaryKeySelective(dietDailyDietPlanOld);
		
	}

	@Override
	public Page<DietDailyDietPlanVo> findPage(DietDailyDietPlanVo dietDailyDietPlanVo, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return dietDailyPlanRecordMapper.findByPage(dietDailyDietPlanVo);
	}

	@Override
	public DietDailyDietPlan findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("饮食计划查询,根据主键查询,主键为空");
            throw new Exception("饮食计划查询,根据主键查询,主键为空");
        }
		return dietDailyPlanRecordMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<DietDailyDietPlan> findAll(DietDailyDietPlan dietDailyDietPlan) throws Exception {
		return dietDailyPlanRecordMapper.findAll(dietDailyDietPlan);
	}
}
