package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
import com.aik.vo.AikNutritionalIndexVo;
import com.aik.vo.DietUserCollectFoodVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Description: BMI
 * Created by as on 2017/9/7.
 */
@Service
public class NutritionalIndexManageServiceImpl implements NutritionalIndexManageService {

    private static final Logger logger = LoggerFactory.getLogger(NutritionalIndexManageServiceImpl.class);

    private AikNutritionalIndexMapper aikNutritionalIndexMapper;

    @Autowired
    public void setAikNutritionalIndexMapper(
			AikNutritionalIndexMapper aikNutritionalIndexMapper) {
		this.aikNutritionalIndexMapper = aikNutritionalIndexMapper;
	}


    

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("BMI删除,根据主键删除,主键为空");
            throw new Exception("BMI删除,根据主键删除,主键为空");
        }
		aikNutritionalIndexMapper.deleteByPrimaryKey(id);
		
	}


	@Override
	public void save(AikNutritionalIndex aikNutritionalIndex) throws Exception {
		if (null == aikNutritionalIndex) {
			logger.error("BMI保存,根据对象保存,对象为空");
            throw new Exception("BMI保存,根据对象保存,对象为空");
        }
		aikNutritionalIndex.setCreateDate(new Date());
		aikNutritionalIndexMapper.insert(aikNutritionalIndex);
		
	}

	@Override
	public void update(AikNutritionalIndex aikNutritionalIndex) throws Exception {
		if (null == aikNutritionalIndex || null == aikNutritionalIndex.getId()) {
			logger.error("BMI修改,根据对象保存,对象为空");
            throw new Exception("BMI修改,根据对象保存,对象为空");
        }
		AikNutritionalIndex aikNutritionalIndexOld = new AikNutritionalIndex();
		aikNutritionalIndexOld = aikNutritionalIndexMapper.selectByPrimaryKey(aikNutritionalIndex.getId());
		aikNutritionalIndexOld.setUserId(aikNutritionalIndex.getUserId());
		aikNutritionalIndexOld.setBmi(aikNutritionalIndex.getBmi());
		aikNutritionalIndexOld.setHeight(aikNutritionalIndex.getHeight());
		aikNutritionalIndexOld.setWeight(aikNutritionalIndex.getWeight());
		aikNutritionalIndexOld.setRecordDate(aikNutritionalIndex.getRecordDate());
		aikNutritionalIndexMapper.updateByPrimaryKeySelective(aikNutritionalIndexOld);
		
	}

	@Override
	public Page<AikNutritionalIndexVo> findPage(AikNutritionalIndexVo aikNutritionalIndexVo, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return aikNutritionalIndexMapper.findByPage(aikNutritionalIndexVo);
	}

	@Override
	public AikNutritionalIndex findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("BMI查询,根据主键查询,主键为空");
            throw new Exception("BMI查询,根据主键查询,主键为空");
        }
		return aikNutritionalIndexMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<AikNutritionalIndex> findAll(AikNutritionalIndex aikNutritionalIndex) throws Exception {
		return aikNutritionalIndexMapper.findAll(aikNutritionalIndex);
	}
}
