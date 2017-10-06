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
 * Description: 问题明细
 * Created by as on 2017/9/7.
 */
@Service
public class QuestionManageServiceImpl implements QuestionManageService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionManageServiceImpl.class);

    private AikQuestionMapper aikQuestionMapper;

    @Autowired
    public void setAikQuestionMapper(AikQuestionMapper aikQuestionMapper) {
		this.aikQuestionMapper = aikQuestionMapper;
	}
    
	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("问题明细删除,根据主键删除,主键为空");
            throw new Exception("问题明细删除,根据主键删除,主键为空");
        }
		aikQuestionMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void save(AikQuestion aikQuestion) throws Exception {
		if (null == aikQuestion) {
			logger.error("问题明细保存,根据对象保存,对象为空");
            throw new Exception("问题明细保存,根据对象保存,对象为空");
        }
		aikQuestion.setCreateDate(new Date());
		aikQuestionMapper.insert(aikQuestion);
		
	}

	@Override
	public void update(AikQuestion aikQuestion) throws Exception {
		if (null == aikQuestion || null == aikQuestion.getId()) {
			logger.error("问题明细修改,根据对象保存,对象为空");
            throw new Exception("问题明细修改,根据对象保存,对象为空");
        }

		AikQuestion aikQuestionOld = new AikQuestion();
		aikQuestionOld = aikQuestionMapper.selectByPrimaryKey(aikQuestion.getId());
		aikQuestionOld.setOrderId(aikQuestion.getOrderId());
		//TODO
		aikQuestionMapper.updateByPrimaryKeySelective(aikQuestionOld);
		
	}

	@Override
	public Page<AikQuestion> findPage(AikQuestion aikQuestion, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return aikQuestionMapper.findByPage(aikQuestion);
	}

	@Override
	public AikQuestion findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("问题明细查询,根据主键查询,主键为空");
            throw new Exception("问题明细查询,根据主键查询,主键为空");
        }
		return aikQuestionMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<AikQuestion> findAll(AikQuestion aikQuestion) throws Exception {
		return aikQuestionMapper.findAll(aikQuestion);
	}
}
