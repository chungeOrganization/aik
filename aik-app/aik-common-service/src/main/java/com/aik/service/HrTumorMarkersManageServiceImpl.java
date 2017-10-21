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
 * Description: 健康档案-肿瘤标志物指标
 * Created by as on 2017/9/7.
 */
@Service
public class HrTumorMarkersManageServiceImpl implements HrTumorMarkersManageService {

    private static final Logger logger = LoggerFactory.getLogger(HrTumorMarkersManageServiceImpl.class);
    
    private AikHrTumorMarkersMapper aikHrTumorMarkersMapper;

    @Autowired
    public void setAikHrTumorMarkersMapper(
			AikHrTumorMarkersMapper aikHrTumorMarkersMapper) {
		this.aikHrTumorMarkersMapper = aikHrTumorMarkersMapper;
	}

	@Override
	public void deleteByPrimaryKey(Integer id) throws Exception {
		if (null == id) {
            logger.error("健康档案-肿瘤标志物指标删除,根据主键删除,主键为空");
            throw new Exception("健康档案-肿瘤标志物指标删除,根据主键删除,主键为空");
        }
		aikHrTumorMarkersMapper.deleteByPrimaryKey(id);
		
	}

	

	@Override
	public void save(AikHrTumorMarkers aikHrTumorMarkers) throws Exception {
		if (null == aikHrTumorMarkers) {
			logger.error("健康档案-肿瘤标志物指标保存,根据对象保存,对象为空");
            throw new Exception("健康档案-肿瘤标志物指标保存,根据对象保存,对象为空");
        }
		aikHrTumorMarkers.setCreateDate(new Date());
		aikHrTumorMarkersMapper.insert(aikHrTumorMarkers);
		
	}

	@Override
	public void update(AikHrTumorMarkers aikHrTumorMarkers) throws Exception {
		if (null == aikHrTumorMarkers || null == aikHrTumorMarkers.getId()) {
			logger.error("健康档案-肿瘤标志物指标修改,根据对象保存,对象为空");
            throw new Exception("健康档案-肿瘤标志物指标修改,根据对象保存,对象为空");
        }

		AikHrTumorMarkers aikHrTumorMarkersOld = new AikHrTumorMarkers();
		aikHrTumorMarkersOld = aikHrTumorMarkersMapper.selectByPrimaryKey(aikHrTumorMarkers.getId());
		//TODO
		aikHrTumorMarkersMapper.updateByPrimaryKeySelective(aikHrTumorMarkersOld);
		
	}


	@Override
	public AikHrTumorMarkers findById(Integer id) throws Exception {
		if (null == id) {
    		logger.error("健康档案-肿瘤标志物指标查询,根据主键查询,主键为空");
            throw new Exception("健康档案-肿瘤标志物指标查询,根据主键查询,主键为空");
        }
		return aikHrTumorMarkersMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<AikHrTumorMarkers> findAll(AikHrTumorMarkers aikHrTumorMarkers)
			throws Exception {
		return aikHrTumorMarkersMapper.findAll(aikHrTumorMarkers);
	}



}
