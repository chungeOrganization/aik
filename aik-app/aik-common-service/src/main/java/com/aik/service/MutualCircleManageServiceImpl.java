package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
import com.aik.vo.AccCircleCommentVo;
import com.aik.vo.AccMutualCircleVo;
import com.aik.vo.AikHealthRecordVo;
import com.aik.vo.StoUserOrderVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Description: 互助圈
 * Created by as on 2017/9/7.
 */
@Service
public class MutualCircleManageServiceImpl implements MutualCircleManageService {

    private static final Logger logger = LoggerFactory.getLogger(MutualCircleManageServiceImpl.class);

    private AccMutualCircleMapper accMutualCircleMapper;

    @Autowired
    public void setAccMutualCircleMapper(AccMutualCircleMapper accMutualCircleMapper) {
		this.accMutualCircleMapper = accMutualCircleMapper;
	}

    
    
	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("互助圈删除,根据主键删除,主键为空");
            throw new Exception("互助圈删除,根据主键删除,主键为空");
        }
		accMutualCircleMapper.deleteByPrimaryKey(id);
		
	}

	
	@Override
	public void save(AccMutualCircle accMutualCircle) throws Exception {
		if (null == accMutualCircle) {
			logger.error("互助圈保存,根据对象保存,对象为空");
            throw new Exception("互助圈保存,根据对象保存,对象为空");
        }
		accMutualCircle.setCreateDate(new Date());
		accMutualCircleMapper.insert(accMutualCircle);
		
	}

	@Override
	public void update(AccMutualCircle accMutualCircle) throws Exception {
		if (null == accMutualCircle || null == accMutualCircle.getId()) {
			logger.error("互助圈修改,根据对象保存,对象为空");
            throw new Exception("互助圈修改,根据对象保存,对象为空");
        }

		AccMutualCircle accMutualCircleOld = new AccMutualCircle();
		accMutualCircleOld = accMutualCircleMapper.selectByPrimaryKey(accMutualCircle.getId());
		//TODO
		accMutualCircleMapper.updateByPrimaryKeySelective(accMutualCircleOld);
		
	}

	@Override
	public Page<AccMutualCircleVo> findPage(AccMutualCircleVo accMutualCircleVo, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return accMutualCircleMapper.findByPage(accMutualCircleVo);
	}

	@Override
	public AccMutualCircle findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("互助圈查询,根据主键查询,主键为空");
            throw new Exception("互助圈查询,根据主键查询,主键为空");
        }
		return accMutualCircleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<AccMutualCircleVo> findAll(AccMutualCircle accMutualCircle)
			throws Exception {
		return accMutualCircleMapper.findAll(accMutualCircle);
	}



}
