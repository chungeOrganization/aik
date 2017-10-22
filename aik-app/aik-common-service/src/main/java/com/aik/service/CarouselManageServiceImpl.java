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
 * Description: 轮播图
 * Created by as on 2017/9/7.
 */
@Service
public class CarouselManageServiceImpl implements CarouselManageService {

    private static final Logger logger = LoggerFactory.getLogger(CarouselManageServiceImpl.class);

    private AikCarouselMapper aikCarouselMapper;

    @Autowired
    public void setAikCarouselMapper(AikCarouselMapper aikCarouselMapper) {
		this.aikCarouselMapper = aikCarouselMapper;
	}

    

	@Override
	public void deleteByPrimaryKey(Long id) throws Exception {
		if (null == id) {
            logger.error("轮播图删除,根据主键删除,主键为空");
            throw new Exception("轮播图删除,根据主键删除,主键为空");
        }
		aikCarouselMapper.deleteByPrimaryKey(id);
	}


	@Override
	public void save(AikCarousel aikCarousel) throws Exception {
		if (null == aikCarousel) {
			logger.error("轮播图保存,根据对象保存,对象为空");
            throw new Exception("轮播图保存,根据对象保存,对象为空");
        }
		aikCarousel.setCreateDate(new Date());
		aikCarouselMapper.insert(aikCarousel);
		
	}

	@Override
	public void update(AikCarousel aikCarousel) throws Exception {
		if (null == aikCarousel || null == aikCarousel.getId()) {
			logger.error("轮播图保存,根据对象保存,对象为空");
            throw new Exception("轮播图保存,根据对象保存,对象为空");
        }

		AikCarousel aikCarouselOld = new AikCarousel();
		aikCarouselOld = aikCarouselMapper.selectByPrimaryKey(aikCarousel.getId());
		aikCarouselOld.setAppNo(aikCarousel.getAppNo());
		aikCarouselOld.setImage(aikCarousel.getImage());
		aikCarouselMapper.updateByPrimaryKeySelective(aikCarouselOld);
		
	}

	@Override
	public Page<AikCarousel> findPage(AikCarousel aikCarousel, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return aikCarouselMapper.findByPage(aikCarousel);
	}

	@Override
	public AikCarousel findById(Long id) throws Exception {
		if (null == id) {
    		logger.error("轮播图查询,根据主键查询,主键为空");
            throw new Exception("轮播图查询,根据主键查询,主键为空");
        }
		return aikCarouselMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<AikCarousel> findAll(AikCarousel aikCarousel) throws Exception {
		return aikCarouselMapper.findAll(aikCarousel);
	}
}
