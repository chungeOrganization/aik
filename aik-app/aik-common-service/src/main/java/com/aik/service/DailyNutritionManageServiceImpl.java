package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
import com.aik.vo.DietDailyDietPlanVo;
import com.aik.vo.DietDailyNutritionVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Description: 营养元素摄于量对比分析
 * Created by as on 2017/9/7.
 */
@Service
public class DailyNutritionManageServiceImpl implements DailyNutritionManageService {

    private static final Logger logger = LoggerFactory.getLogger(DailyNutritionManageServiceImpl.class);

    private DietDailyNutritionMapper dietDailyNutritionMapper;

    @Autowired
    public void setDietDailyNutritionMapper(
			DietDailyNutritionMapper dietDailyNutritionMapper) {
		this.dietDailyNutritionMapper = dietDailyNutritionMapper;
	}

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("营养元素摄于量对比分析删除,根据主键删除,主键为空");
            throw new Exception("营养元素摄于量对比分析删除,根据主键删除,主键为空");
        }
		dietDailyNutritionMapper.deleteByPrimaryKey(id);
		
	}


	@Override
	public void save(DietDailyNutrition dietDailyNutrition) throws Exception {
		if (null == dietDailyNutrition) {
			logger.error("营养元素摄于量对比分析保存,根据对象保存,对象为空");
            throw new Exception("营养元素摄于量对比分析保存,根据对象保存,对象为空");
        }
		dietDailyNutrition.setCreateDate(new Date());
		dietDailyNutritionMapper.insert(dietDailyNutrition);
		
	}

	@Override
	public void update(DietDailyNutrition dietDailyNutrition) throws Exception {
		if (null == dietDailyNutrition || null == dietDailyNutrition.getId()) {
			logger.error("营养元素摄于量对比分析修改,根据对象保存,对象为空");
            throw new Exception("营养元素摄于量对比分析修改,根据对象保存,对象为空");
        }
		DietDailyNutrition dietDailyNutritionOld = new DietDailyNutrition();
		dietDailyNutritionOld = dietDailyNutritionMapper.selectByPrimaryKey(dietDailyNutrition.getId());
		dietDailyNutritionOld.setUserId(dietDailyNutrition.getUserId());
		dietDailyNutritionOld.setCarbs(dietDailyNutrition.getCarbs());
		dietDailyNutritionOld.setLipid(dietDailyNutrition.getLipid());
		dietDailyNutritionOld.setMinerals(dietDailyNutrition.getMinerals());
		dietDailyNutritionOld.setProtein(dietDailyNutrition.getProtein());
		dietDailyNutritionOld.setVitamin(dietDailyNutrition.getVitamin());
		dietDailyNutritionOld.setWater(dietDailyNutrition.getWater());
		dietDailyNutritionOld.setUpdateDate(dietDailyNutrition.getUpdateDate());
		
		//TODO
		dietDailyNutritionMapper.updateByPrimaryKeySelective(dietDailyNutritionOld);
		
	}

	@Override
	public Page<DietDailyNutritionVo> findPage(DietDailyNutritionVo dietDailyNutritionVo, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return dietDailyNutritionMapper.findByPage(dietDailyNutritionVo);
	}

	@Override
	public DietDailyNutrition findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("营养元素摄于量对比分析查询,根据主键查询,主键为空");
            throw new Exception("营养元素摄于量对比分析查询,根据主键查询,主键为空");
        }
		return dietDailyNutritionMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DietDailyNutrition> findAll(DietDailyNutrition dietDailyNutrition) throws Exception {
		return dietDailyNutritionMapper.findAll(dietDailyNutrition);
	}
}
