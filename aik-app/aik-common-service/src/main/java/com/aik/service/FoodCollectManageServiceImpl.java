package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

/**
 * Description: 食物收藏
 * Created by as on 2017/9/7.
 */
@Service
public class FoodCollectManageServiceImpl implements FoodCollectManageService {

    private static final Logger logger = LoggerFactory.getLogger(FoodCollectManageServiceImpl.class);

    private DietUserCollectFoodMapper dietUserCollectFoodMapper;

    @Autowired
    public void setDietUserCollectFoodMapper(
			DietUserCollectFoodMapper dietUserCollectFoodMapper) {
		this.dietUserCollectFoodMapper = dietUserCollectFoodMapper;
	}

    

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("食物收藏删除,根据主键删除,主键为空");
            throw new Exception("食物收藏删除,根据主键删除,主键为空");
        }
		dietUserCollectFoodMapper.deleteByPrimaryKey(id);
		
	}


	

	@Override
	public void save(DietUserCollectFood dietUserCollectFood) throws Exception {
		if (null == dietUserCollectFood) {
			logger.error("食物收藏保存,根据对象保存,对象为空");
            throw new Exception("食物收藏保存,根据对象保存,对象为空");
        }
		dietUserCollectFood.setCreateDate(new Date());
		dietUserCollectFoodMapper.insert(dietUserCollectFood);
		
	}

	@Override
	public void update(DietUserCollectFood dietUserCollectFood) throws Exception {
		if (null == dietUserCollectFood || null == dietUserCollectFood.getId()) {
			logger.error("食物收藏修改,根据对象保存,对象为空");
            throw new Exception("食物收藏修改,根据对象保存,对象为空");
        }
		DietUserCollectFood dietUserCollectFoodOld = new DietUserCollectFood();
		dietUserCollectFoodOld = dietUserCollectFoodMapper.selectByPrimaryKey(dietUserCollectFood.getId());
		dietUserCollectFoodOld.setUserId(dietUserCollectFood.getUserId());
		dietUserCollectFoodOld.setFoodId(dietUserCollectFood.getFoodId());
		dietUserCollectFoodMapper.updateByPrimaryKeySelective(dietUserCollectFoodOld);
		
	}

	@Override
	public Page<DietUserCollectFood> findPage(DietUserCollectFood dietUserCollectFood, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return dietUserCollectFoodMapper.findByPage(dietUserCollectFood);
	}

	@Override
	public DietUserCollectFood findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("食物收藏查询,根据主键查询,主键为空");
            throw new Exception("食物收藏查询,根据主键查询,主键为空");
        }
		return dietUserCollectFoodMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<DietUserCollectFood> findAll(DietUserCollectFood dietUserCollectFood) throws Exception {
		return dietUserCollectFoodMapper.findAll(dietUserCollectFood);
	}
}
