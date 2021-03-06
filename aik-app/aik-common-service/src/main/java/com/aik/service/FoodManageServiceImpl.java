package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
import com.aik.vo.DietFoodVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Description: 食物
 * Created by as on 2017/9/7.
 */
@Service
public class FoodManageServiceImpl implements FoodManageService {

    private static final Logger logger = LoggerFactory.getLogger(FoodManageServiceImpl.class);

    private DietFoodMapper dietFoodMapper;

    @Autowired
    public void setDietFoodMapper(DietFoodMapper dietFoodMapper) {
        this.dietFoodMapper = dietFoodMapper;
    }

    

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("食物删除,根据主键删除,主键为空");
            throw new Exception("食物删除,根据主键删除,主键为空");
        }
		 dietFoodMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void save(DietFood dietFood) throws Exception {
		if (null == dietFood) {
			logger.error("食物保存,根据对象保存,对象为空");
            throw new Exception("食物保存,根据对象保存,对象为空");
        }
		dietFood.setCreateDate(new Date());
		dietFoodMapper.insert(dietFood);
		
	}

	@Override
	public void update(DietFood dietFood) throws Exception {
		if (null == dietFood || null == dietFood.getId()) {
			logger.error("食物修改,根据对象保存,对象为空");
            throw new Exception("食物修改,根据对象保存,对象为空");
        }

		DietFood dietFoodOld = new DietFood();
		dietFoodOld = dietFoodMapper.selectByPrimaryKey(dietFood.getId());
		dietFoodOld.setName(dietFood.getName());
		dietFoodOld.setCategory(dietFood.getCategory());
		dietFoodOld.setFatRadio(dietFood.getFatRadio());
		dietFoodOld.setProteinRadio(dietFood.getProteinRadio());
		dietFoodOld.setRecommend(dietFood.getRecommend());
		dietFoodOld.setSpotRank(dietFood.getSpotRank());
		dietFoodOld.setWeight(dietFood.getWeight());
		dietFoodOld.setWeightUnit(dietFood.getWeightUnit());
		dietFoodOld.setType(dietFood.getType());
		dietFoodOld.setImage(dietFood.getImage());
		//TODO
		dietFoodOld.setUpdateDate(new Date());
		dietFoodMapper.updateByPrimaryKeySelective(dietFoodOld);
		
	}

	@Override
	public Page<DietFoodVo> findPage(DietFood dietFood, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return dietFoodMapper.findByPage(dietFood);
	}

	@Override
	public DietFood findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("食物查询,根据主键查询,主键为空");
            throw new Exception("食物查询,根据主键查询,主键为空");
        }
		return dietFoodMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<DietFood> findAll(DietFood dietFood) throws Exception {
		return dietFoodMapper.findAll(dietFood);
	}
}
