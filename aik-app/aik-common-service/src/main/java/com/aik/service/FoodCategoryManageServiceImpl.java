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
 * Description: 食物分类
 * Created by as on 2017/9/7.
 */
@Service
public class FoodCategoryManageServiceImpl implements FoodCategoryManageService {

    private static final Logger logger = LoggerFactory.getLogger(FoodCategoryManageServiceImpl.class);

    private DietFoodCategoryMapper dietFoodCategoryMapper;

    @Autowired
    public void setDietFoodCategoryMapper(
			DietFoodCategoryMapper dietFoodCategoryMapper) {
		this.dietFoodCategoryMapper = dietFoodCategoryMapper;
	}

    

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("食物分类删除,根据主键删除,主键为空");
            throw new Exception("食物分类删除,根据主键删除,主键为空");
        }
		dietFoodCategoryMapper.deleteByPrimaryKey(id);
		
	}

	

	@Override
	public void save(DietFoodCategory dietFoodCategory) throws Exception {
		if (null == dietFoodCategory) {
			logger.error("食物分类保存,根据对象保存,对象为空");
            throw new Exception("食物分类保存,根据对象保存,对象为空");
        }
		dietFoodCategory.setCreateDate(new Date());
		dietFoodCategoryMapper.insert(dietFoodCategory);
		
	}

	@Override
	public void update(DietFoodCategory dietFoodCategory) throws Exception {
		if (null == dietFoodCategory || null == dietFoodCategory.getId()) {
			logger.error("食物分类保存,根据对象保存,对象为空");
            throw new Exception("食物分类保存,根据对象保存,对象为空");
        }

		DietFoodCategory dietFoodCategoryOld = new DietFoodCategory();
		dietFoodCategoryOld = dietFoodCategoryMapper.selectByPrimaryKey(dietFoodCategory.getId());
		dietFoodCategoryOld.setName(dietFoodCategoryOld.getName());
		dietFoodCategoryOld.setImage(dietFoodCategoryOld.getImage());
		dietFoodCategoryMapper.updateByPrimaryKeySelective(dietFoodCategoryOld);
		
	}

	@Override
	public Page<DietFoodCategory> findPage(DietFoodCategory dietFoodCategory, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return dietFoodCategoryMapper.findByPage(dietFoodCategory);
	}

	@Override
	public DietFoodCategory findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("食物分类查询,根据主键查询,主键为空");
            throw new Exception("食物分类查询,根据主键查询,主键为空");
        }
		return dietFoodCategoryMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<DietFoodCategory> findAll(DietFoodCategory dietFoodCategory) throws Exception {
		return dietFoodCategoryMapper.findAll(dietFoodCategory);
	}
}
