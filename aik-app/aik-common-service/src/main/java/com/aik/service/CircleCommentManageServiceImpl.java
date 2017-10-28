package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
import com.aik.vo.AccCircleCommentVo;
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
 * Description: 互助圈-评论
 * Created by as on 2017/9/7.
 */
@Service
public class CircleCommentManageServiceImpl implements CircleCommentManageService {

    private static final Logger logger = LoggerFactory.getLogger(CircleCommentManageServiceImpl.class);
    
    private AccCircleCommentMapper accCircleCommentMapper;

    @Autowired
    public void setAccCircleCommentMapper(
			AccCircleCommentMapper accCircleCommentMapper) {
		this.accCircleCommentMapper = accCircleCommentMapper;
	}

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("互助圈-评论删除,根据主键删除,主键为空");
            throw new Exception("互助圈-评论删除,根据主键删除,主键为空");
        }
		accCircleCommentMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void save(AccCircleComment accCircleComment) throws Exception {
		if (null == accCircleComment) {
			logger.error("互助圈-评论保存,根据对象保存,对象为空");
            throw new Exception("互助圈-评论保存,根据对象保存,对象为空");
        }
		accCircleComment.setCreateDate(new Date());
		accCircleCommentMapper.insert(accCircleComment);
		
	}

	@Override
	public void update(AccCircleComment accCircleComment) throws Exception {
		if (null == accCircleComment || null == accCircleComment.getId()) {
			logger.error("互助圈-评论修改,根据对象保存,对象为空");
            throw new Exception("互助圈-评论修改,根据对象保存,对象为空");
        }

		AccCircleComment accCircleCommentOld = new AccCircleComment();
		accCircleCommentOld = accCircleCommentMapper.selectByPrimaryKey(accCircleComment.getId());
		//TODO
		accCircleCommentMapper.updateByPrimaryKeySelective(accCircleCommentOld);
		
	}


	@Override
	public AccCircleComment findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("互助圈-评论查询,根据主键查询,主键为空");
            throw new Exception("互助圈-评论查询,根据主键查询,主键为空");
        }
		return accCircleCommentMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<AccCircleCommentVo> findAll(AccCircleComment accCircleComment)
			throws Exception {
		return accCircleCommentMapper.findAll(accCircleComment);
	}



}
