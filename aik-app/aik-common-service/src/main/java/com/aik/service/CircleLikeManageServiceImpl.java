package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
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
 * Description: 互助圈-点赞
 * Created by as on 2017/9/7.
 */
@Service
public class CircleLikeManageServiceImpl implements CircleLikeManageService {

    private static final Logger logger = LoggerFactory.getLogger(CircleLikeManageServiceImpl.class);
    
    private AccCircleLikeMapper accCircleLikeMapper;

    @Autowired
    public void setAccCircleLikeMapper(AccCircleLikeMapper accCircleLikeMapper) {
		this.accCircleLikeMapper = accCircleLikeMapper;
	}
   

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("互助圈-点赞删除,根据主键删除,主键为空");
            throw new Exception("互助圈-点赞删除,根据主键删除,主键为空");
        }
		accCircleLikeMapper.deleteByPrimaryKey(id);
		
	}


	@Override
	public void save(AccCircleLike accCircleLike) throws Exception {
		if (null == accCircleLike) {
			logger.error("互助圈-点赞保存,根据对象保存,对象为空");
            throw new Exception("互助圈-点赞保存,根据对象保存,对象为空");
        }
		accCircleLike.setCreateDate(new Date());
		accCircleLikeMapper.insert(accCircleLike);
		
	}

	@Override
	public void update(AccCircleLike accCircleLike) throws Exception {
		if (null == accCircleLike || null == accCircleLike.getId()) {
			logger.error("互助圈-点赞修改,根据对象保存,对象为空");
            throw new Exception("互助圈-点赞修改,根据对象保存,对象为空");
        }

		AccCircleLike accCircleLikeOld = new AccCircleLike();
		accCircleLikeOld = accCircleLikeMapper.selectByPrimaryKey(accCircleLike.getId());
		//TODO
		accCircleLikeMapper.updateByPrimaryKeySelective(accCircleLikeOld);
		
	}


	@Override
	public AccCircleLike findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("互助圈-点赞查询,根据主键查询,主键为空");
            throw new Exception("互助圈-点赞查询,根据主键查询,主键为空");
        }
		return accCircleLikeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<AccCircleLike> findAll(AccCircleLike accCircleLike)
			throws Exception {
		return accCircleLikeMapper.findAll(accCircleLike);
	}



}
