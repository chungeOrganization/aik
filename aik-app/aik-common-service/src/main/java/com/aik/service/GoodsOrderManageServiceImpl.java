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
 * Description: 商品订单
 * Created by as on 2017/9/7.
 */
@Service
public class GoodsOrderManageServiceImpl implements GoodsOrderManageService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsOrderManageServiceImpl.class);

    private StoUserOrderMapper stoUserOrderMapper;

    @Autowired
    public void setStoUserOrderMapper(StoUserOrderMapper stoUserOrderMapper) {
		this.stoUserOrderMapper = stoUserOrderMapper;
	}

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("商品订单删除,根据主键删除,主键为空");
            throw new Exception("商品订单删除,根据主键删除,主键为空");
        }
		stoUserOrderMapper.deleteByPrimaryKey(id);
		
	}


	@Override
	public void save(StoUserOrder stoUserOrder) throws Exception {
		if (null == stoUserOrder) {
			logger.error("商品订单保存,根据对象保存,对象为空");
            throw new Exception("商品订单保存,根据对象保存,对象为空");
        }
		stoUserOrder.setCreateDate(new Date());
		stoUserOrderMapper.insert(stoUserOrder);
		
	}

	@Override
	public void update(StoUserOrder stoUserOrder) throws Exception {
		if (null == stoUserOrder || null == stoUserOrder.getId()) {
			logger.error("商品订单修改,根据对象保存,对象为空");
            throw new Exception("商品订单修改,根据对象保存,对象为空");
        }

		StoUserOrder stoUserOrderOld = new StoUserOrder();
		stoUserOrderOld = stoUserOrderMapper.selectByPrimaryKey(stoUserOrder.getId());
		stoUserOrderOld.setOrderNum(stoUserOrder.getOrderNum());
		stoUserOrderOld.setUserId(stoUserOrder.getUserId());
		//TODO
		stoUserOrderOld.setUpdateDate(new Date());
		stoUserOrderMapper.updateByPrimaryKeySelective(stoUserOrderOld);
		
	}

	@Override
	public Page<StoUserOrder> findPage(StoUserOrder stoUserOrder, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return stoUserOrderMapper.findByPage(stoUserOrder);
	}

	@Override
	public StoUserOrder findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("商品订单查询,根据主键查询,主键为空");
            throw new Exception("商品订单查询,根据主键查询,主键为空");
        }
		return stoUserOrderMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<StoUserOrder> findAll(StoUserOrder stoUserOrder) throws Exception {
		return stoUserOrderMapper.findAll(stoUserOrder);
	}
}
