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
 * Description: 商品
 * Created by as on 2017/9/7.
 */
@Service
public class GoodsManageServiceImpl implements GoodsManageService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsManageServiceImpl.class);

    private StoGoodsMapper stoGoodsMapper;

    @Autowired
    public void setStoGoodsMapper(StoGoodsMapper stoGoodsMapper) {
		this.stoGoodsMapper = stoGoodsMapper;
	}

    

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("商品删除,根据主键删除,主键为空");
            throw new Exception("商品删除,根据主键删除,主键为空");
        }
		stoGoodsMapper.deleteByPrimaryKey(id);
		
	}


	

	@Override
	public void save(StoGoods stoGoods) throws Exception {
		if (null == stoGoods) {
			logger.error("商品保存,根据对象保存,对象为空");
            throw new Exception("商品保存,根据对象保存,对象为空");
        }
		stoGoods.setCreateDate(new Date());
		stoGoodsMapper.insert(stoGoods);
		
	}

	@Override
	public void update(StoGoods stoGoods) throws Exception {
		if (null == stoGoods || null == stoGoods.getId()) {
			logger.error("商品修改,根据对象保存,对象为空");
            throw new Exception("商品修改,根据对象保存,对象为空");
        }

		StoGoods stoGoodsOld = new StoGoods();
		stoGoodsOld = stoGoodsMapper.selectByPrimaryKey(stoGoods.getId());
		stoGoodsOld.setName(stoGoods.getName());
		stoGoodsOld.setImage(stoGoods.getImage());
		stoGoodsOld.setIsRecommend(stoGoods.getIsRecommend());
		stoGoodsOld.setPrice(stoGoods.getPrice());
		stoGoodsOld.setStatus(stoGoods.getStatus());
		stoGoodsOld.setDescription(stoGoods.getDescription());
		stoGoodsOld.setType(stoGoods.getType());
		//TODO
		stoGoodsOld.setUpdateDate(new Date());
		stoGoodsMapper.updateByPrimaryKeySelective(stoGoodsOld);
		
	}

	@Override
	public Page<StoGoods> findPage(StoGoods stoGoods, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return stoGoodsMapper.findByPage(stoGoods);
	}

	@Override
	public StoGoods findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("商品查询,根据主键查询,主键为空");
            throw new Exception("商品查询,根据主键查询,主键为空");
        }
		return stoGoodsMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<StoGoods> findAll(StoGoods stoGoods) throws Exception {
		return stoGoodsMapper.findAll(stoGoods);
	}
}
