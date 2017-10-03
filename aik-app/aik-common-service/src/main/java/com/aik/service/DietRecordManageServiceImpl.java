package com.aik.service;

import com.aik.dao.*;
import com.aik.model.*;
import com.aik.util.PageUtils;
import com.aik.vo.DietDailyDietRecordVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Description: 饮食实际记录
 * Created by as on 2017/9/7.
 */
@Service
public class DietRecordManageServiceImpl implements DietRecordManageService {

    private static final Logger logger = LoggerFactory.getLogger(DietRecordManageServiceImpl.class);

    private DietDailyDietRecordMapper dietDailyDietRecordMapper;

    @Autowired
    public void setDietDailyDietRecordMapper(
			DietDailyDietRecordMapper dietDailyDietRecordMapper) {
		this.dietDailyDietRecordMapper = dietDailyDietRecordMapper;
	}


    

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("饮食实际记录删除,根据主键删除,主键为空");
            throw new Exception("饮食实际记录删除,根据主键删除,主键为空");
        }
		dietDailyDietRecordMapper.deleteByPrimaryKey(id);
		
	}



	@Override
	public void save(DietDailyDietRecord dietDailyDietRecord) throws Exception {
		if (null == dietDailyDietRecord) {
			logger.error("饮食实际记录保存,根据对象保存,对象为空");
            throw new Exception("饮食实际记录保存,根据对象保存,对象为空");
        }
		dietDailyDietRecord.setCreateDate(new Date());
		dietDailyDietRecordMapper.insert(dietDailyDietRecord);
		
	}

	@Override
	public void update(DietDailyDietRecord dietDailyDietRecord) throws Exception {
		if (null == dietDailyDietRecord || null == dietDailyDietRecord.getId()) {
			logger.error("饮食实际记录修改,根据对象保存,对象为空");
            throw new Exception("饮食实际记录修改,根据对象保存,对象为空");
        }
		DietDailyDietRecord dietDailyDietRecordOld = new DietDailyDietRecord();
		dietDailyDietRecordOld = dietDailyDietRecordMapper.selectByPrimaryKey(dietDailyDietRecord.getId());
		dietDailyDietRecordOld.setUserId(dietDailyDietRecord.getUserId());
		//TODO
		dietDailyDietRecordMapper.updateByPrimaryKeySelective(dietDailyDietRecordOld);
		
	}

	@Override
	public Page<DietDailyDietRecordVo> findPage(DietDailyDietRecordVo dietDailyDietRecord, PageUtils pageUtils)
			throws Exception {
		 PageHelper.startPage(pageUtils.getPage(), pageUtils.getSize());
		 return dietDailyDietRecordMapper.findByPage(dietDailyDietRecord);
	}

	@Override
	public DietDailyDietRecord findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("饮食实际记录查询,根据主键查询,主键为空");
            throw new Exception("饮食实际记录查询,根据主键查询,主键为空");
        }
		return dietDailyDietRecordMapper.selectByPrimaryKey(id);
	}



	@Override
	public List<DietDailyDietRecord> findAll(DietDailyDietRecord dietDailyDietRecord) throws Exception {
		return dietDailyDietRecordMapper.findAll(dietDailyDietRecord);
	}
}
