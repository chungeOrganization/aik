package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
import com.aik.vo.AikNutritionLessonViewVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Description: 文章查看
 * Created by as on 2017/9/7.
 */
@Service
public class ArticleShowManageServiceImpl implements ArticleShowManageService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleShowManageServiceImpl.class);

    private AikNutritionLessonViewMapper aikNutritionLessonViewMapper;

    @Autowired
    public void setAikNutritionLessonViewMapper(
			AikNutritionLessonViewMapper aikNutritionLessonViewMapper) {
		this.aikNutritionLessonViewMapper = aikNutritionLessonViewMapper;
	}
    

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("文章查看删除,根据主键删除,主键为空");
            throw new Exception("文章查看删除,根据主键删除,主键为空");
        }
		aikNutritionLessonViewMapper.deleteByPrimaryKey(id);
		
	}


	@Override
	public void save(AikNutritionLessonView aikNutritionLessonView) throws Exception {
		if (null == aikNutritionLessonView) {
			logger.error("文章查看保存,根据对象保存,对象为空");
            throw new Exception("文章查看保存,根据对象保存,对象为空");
        }
		aikNutritionLessonView.setCreateDate(new Date());
		aikNutritionLessonViewMapper.insert(aikNutritionLessonView);
		
	}

	@Override
	public void update(AikNutritionLessonView aikNutritionLessonView) throws Exception {
		if (null == aikNutritionLessonView || null == aikNutritionLessonView.getId()) {
			logger.error("文章查看保存,根据对象保存,对象为空");
            throw new Exception("文章查看保存,根据对象保存,对象为空");
        }

		AikNutritionLessonView aikNutritionLessonViewOld = new AikNutritionLessonView();
		aikNutritionLessonViewOld = aikNutritionLessonViewMapper.selectByPrimaryKey(aikNutritionLessonView.getId());
		aikNutritionLessonViewOld.setLessonId(aikNutritionLessonView.getLessonId());
		aikNutritionLessonViewOld.setUserId(aikNutritionLessonView.getUserId());
		aikNutritionLessonViewMapper.updateByPrimaryKeySelective(aikNutritionLessonViewOld);
		
	}

	@Override
	public Page<AikNutritionLessonViewVo> findPage(AikNutritionLessonViewVo aikNutritionLessonViewVo, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return aikNutritionLessonViewMapper.findByPage(aikNutritionLessonViewVo);
	}

	@Override
	public AikNutritionLessonView findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("文章查看查询,根据主键查询,主键为空");
            throw new Exception("文章查看查询,根据主键查询,主键为空");
        }
		return aikNutritionLessonViewMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<AikNutritionLessonView> findAll(AikNutritionLessonView aikNutritionLessonView) throws Exception {
		return aikNutritionLessonViewMapper.findAll(aikNutritionLessonView);
	}
}
