package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
import com.aik.vo.StoUserOrderDetailVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Description: 商品订单明细
 * Created by as on 2017/9/7.
 */
@Service
public class GoodsOrderDetailManageServiceImpl implements GoodsOrderDetailManageService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsOrderDetailManageServiceImpl.class);

    private StoUserOrderDetailMapper stoUserOrderDetailMapper;

    @Autowired
    public void setStoUserOrderDetailMapper(
			StoUserOrderDetailMapper stoUserOrderDetailMapper) {
		this.stoUserOrderDetailMapper = stoUserOrderDetailMapper;
	}
    

    

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("商品订单明细删除,根据主键删除,主键为空");
            throw new Exception("商品订单明细删除,根据主键删除,主键为空");
        }
		stoUserOrderDetailMapper.deleteByPrimaryKey(id);
		
	}


	

	




	@Override
	public void save(StoUserOrderDetail stoUserOrderDetail) throws Exception {
		if (null == stoUserOrderDetail) {
			logger.error("商品订单明细保存,根据对象保存,对象为空");
            throw new Exception("商品订单明细保存,根据对象保存,对象为空");
        }
		stoUserOrderDetail.setCreateDate(new Date());
		stoUserOrderDetailMapper.insert(stoUserOrderDetail);
		
	}

	@Override
	public void update(StoUserOrderDetail stoUserOrderDetail) throws Exception {
		if (null == stoUserOrderDetail || null == stoUserOrderDetail.getId()) {
			logger.error("商品订单明细修改,根据对象保存,对象为空");
            throw new Exception("商品订单明细修改,根据对象保存,对象为空");
        }

		StoUserOrderDetail stoUserOrderDetailOld = new StoUserOrderDetail();
		stoUserOrderDetailOld = stoUserOrderDetailMapper.selectByPrimaryKey(stoUserOrderDetail.getId());
		stoUserOrderDetailOld.setOrderId(stoUserOrderDetail.getOrderId());
		stoUserOrderDetailOld.setGoodsId(stoUserOrderDetail.getGoodsId());
		stoUserOrderDetailOld.setUserId(stoUserOrderDetail.getUserId());
		//TODO
		stoUserOrderDetailOld.setUpdateDate(new Date());
		stoUserOrderDetailMapper.updateByPrimaryKeySelective(stoUserOrderDetailOld);
		
	}

	@Override
	public Page<StoUserOrderDetail> findPage(StoUserOrderDetail stoUserOrderDetail, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return stoUserOrderDetailMapper.findByPage(stoUserOrderDetail);
	}

	@Override
	public StoUserOrderDetail findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("商品订单明细查询,根据主键查询,主键为空");
            throw new Exception("商品订单明细查询,根据主键查询,主键为空");
        }
		return stoUserOrderDetailMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<StoUserOrderDetailVo> findAll(StoUserOrderDetail stoUserOrderDetail) throws Exception {
		return stoUserOrderDetailMapper.findAll(stoUserOrderDetail);
	}
}
