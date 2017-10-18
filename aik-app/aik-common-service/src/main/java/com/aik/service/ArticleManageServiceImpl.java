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
 * Description: 文章
 * Created by as on 2017/9/7.
 */
@Service
public class ArticleManageServiceImpl implements ArticleManageService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleManageServiceImpl.class);

    private AikNutritionLessonMapper aikNutritionLessonMapper;

    @Autowired
    public void setAikNutritionLessonMapper(
			AikNutritionLessonMapper aikNutritionLessonMapper) {
		this.aikNutritionLessonMapper = aikNutritionLessonMapper;
	}

    

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("文章删除,根据主键删除,主键为空");
            throw new Exception("文章删除,根据主键删除,主键为空");
        }
		aikNutritionLessonMapper.deleteByPrimaryKey(id);
		
	}

	

	



	@Override
	public void save(AikNutritionLesson aikNutritionLesson) throws Exception {
		if (null == aikNutritionLesson) {
			logger.error("文章保存,根据对象保存,对象为空");
            throw new Exception("文章保存,根据对象保存,对象为空");
        }
		aikNutritionLesson.setCreateDate(new Date());
		aikNutritionLesson.setIssueDate(new Date());
		aikNutritionLessonMapper.insert(aikNutritionLesson);
		
	}

	@Override
	public void update(AikNutritionLesson aikNutritionLesson) throws Exception {
		if (null == aikNutritionLesson || null == aikNutritionLesson.getId()) {
			logger.error("文章保存,根据对象保存,对象为空");
            throw new Exception("文章保存,根据对象保存,对象为空");
        }

		AikNutritionLesson aikNutritionLessonOld = new AikNutritionLesson();
		aikNutritionLessonOld = aikNutritionLessonMapper.selectByPrimaryKey(aikNutritionLesson.getId());
		aikNutritionLessonOld.setTitle(aikNutritionLesson.getTitle());
		aikNutritionLessonOld.setImage(aikNutritionLesson.getImage());
		aikNutritionLessonOld.setContent(aikNutritionLesson.getContent());
		//aikNutritionLessonOld.setIssueDate(aikNutritionLesson.getIssueDate());
		aikNutritionLessonMapper.updateByPrimaryKeySelective(aikNutritionLessonOld);
		
	}

	@Override
	public Page<AikNutritionLesson> findPage(AikNutritionLesson aikNutritionLesson, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return aikNutritionLessonMapper.findByPage(aikNutritionLesson);
	}

	@Override
	public AikNutritionLesson findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("文章查询,根据主键查询,主键为空");
            throw new Exception("文章查询,根据主键查询,主键为空");
        }
		return aikNutritionLessonMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<AikNutritionLesson> findAll(AikNutritionLesson aikNutritionLesson) throws Exception {
		return aikNutritionLessonMapper.findAll(aikNutritionLesson);
	}
}
