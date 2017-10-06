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
 * Description: 问题订单
 * Created by as on 2017/9/7.
 */
@Service
public class QuestionOrderManageServiceImpl implements QuestionOrderManageService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionOrderManageServiceImpl.class);

    private AikQuestionOrderMapper aikQuestionOrderMapper;

    @Autowired
    public void setAikQuestionOrderMapper(
			AikQuestionOrderMapper aikQuestionOrderMapper) {
		this.aikQuestionOrderMapper = aikQuestionOrderMapper;
	}

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("问题订单删除,根据主键删除,主键为空");
            throw new Exception("问题订单删除,根据主键删除,主键为空");
        }
		aikQuestionOrderMapper.deleteByPrimaryKey(id);
	}


	@Override
	public void save(AikQuestionOrder aikQuestionOrder) throws Exception {
		if (null == aikQuestionOrder) {
			logger.error("问题订单保存,根据对象保存,对象为空");
            throw new Exception("问题订单保存,根据对象保存,对象为空");
        }
		aikQuestionOrder.setCreateDate(new Date());
		aikQuestionOrderMapper.insert(aikQuestionOrder);
		
	}

	@Override
	public void update(AikQuestionOrder aikQuestionOrder) throws Exception {
		if (null == aikQuestionOrder || null == aikQuestionOrder.getId()) {
			logger.error("问题订单修改,根据对象保存,对象为空");
            throw new Exception("问题订单修改,根据对象保存,对象为空");
        }

		AikQuestionOrder aikQuestionOrderOld = new AikQuestionOrder();
		aikQuestionOrderOld = aikQuestionOrderMapper.selectByPrimaryKey(aikQuestionOrder.getId());
		//TODO
		aikQuestionOrderOld.setUpdateDate(new Date());
		aikQuestionOrderMapper.updateByPrimaryKeySelective(aikQuestionOrderOld);
		
	}

	@Override
	public Page<AikQuestionOrder> findPage(AikQuestionOrder aikQuestionOrder, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return aikQuestionOrderMapper.findByPage(aikQuestionOrder);
	}

	@Override
	public AikQuestionOrder findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("问题订单查询,根据主键查询,主键为空");
            throw new Exception("问题订单查询,根据主键查询,主键为空");
        }
		return aikQuestionOrderMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<AikQuestionOrder> findAll(AikQuestionOrder aikQuestionOrder) throws Exception {
		return aikQuestionOrderMapper.findAll(aikQuestionOrder);
	}
}
