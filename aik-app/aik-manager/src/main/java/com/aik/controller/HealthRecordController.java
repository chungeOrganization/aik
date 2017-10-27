package com.aik.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aik.model.AikHealthRecord;
import com.aik.model.AikHrBloodSugar;
import com.aik.model.AikHrRoutineBlood;
import com.aik.model.AikHrTumorMarkers;
import com.aik.service.HealthRecordManageService;
import com.aik.service.HrBloodSugarManageService;
import com.aik.service.HrRoutineBloodManageService;
import com.aik.service.HrTumorMarkersManageService;
import com.aik.util.PageUtils;
import com.aik.vo.AikHealthRecordVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 健康档案库管理
 */
@RestController
@RequestMapping("/healthRecord")
public class HealthRecordController {
	
	private Logger logger = LoggerFactory.getLogger(HealthRecordController.class);

    @Autowired
    private HealthRecordManageService healthRecordManageService;
    
    @Autowired
    private HrBloodSugarManageService hrBloodSugarManageService;
    
    @Autowired
    private HrRoutineBloodManageService	hrRoutineBloodManageService;
    
    @Autowired
    private HrTumorMarkersManageService hrTumorMarkersManageService;
    
    
    /**
     * 健康档案管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("healthRecord/healthRecordManage");
         
         return result;
    }
    
    /**
     * 健康档案信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,AikHealthRecordVo aikHealthRecordVo,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("healthRecord/healthRecordList");
    	Page<AikHealthRecordVo> aikHealthRecordVoList = new Page<AikHealthRecordVo>();
		try {
			Integer size = null;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	aikHealthRecordVoList = healthRecordManageService.findPage(aikHealthRecordVo, pageRequest);
			logger.info("健康档案信息列表获取成功");
		} catch (Exception e) {
			logger.error("健康档案信息列表获取失败", e);
		}
    	PageInfo<AikHealthRecordVo> pageInfo = new PageInfo<AikHealthRecordVo>(aikHealthRecordVoList);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    /**
     * 健康档案信息新增
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {
    	 ModelAndView result = new ModelAndView("healthRecord/healthRecordAdd");
    	 AikHealthRecord aikHealthRecord = new AikHealthRecord();
 		 result.addObject("aikHealthRecord", aikHealthRecord);
 		 logger.info("健康档案信息新增查询成功");
         return result;
    }
    
    
    
    /**
     * 健康档案信息编辑
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
    	ModelAndView result = new ModelAndView("healthRecord/healthRecordView");
    	
    	AikHealthRecordVo aikHealthRecordVo = new AikHealthRecordVo();
    	List<AikHealthRecordVo> aikHealthRecordVos = new ArrayList();
    	List<AikHrBloodSugar> aikHrBloodSugars = new ArrayList();
    	List<AikHrRoutineBlood> aikHrRoutineBloods = new ArrayList();
    	List<AikHrTumorMarkers> aikHrTumorMarkerss = new ArrayList();
 		try {
 			AikHealthRecord aikHealthRecord = new AikHealthRecord();
 			aikHealthRecord.setId(id);
 			aikHealthRecordVos = healthRecordManageService.findAll(aikHealthRecord);
 			if(aikHealthRecordVos != null && aikHealthRecordVos.size() > 0){
 				aikHealthRecordVo = aikHealthRecordVos.get(0);
 			}
 			
 			AikHrBloodSugar aikHrBloodSugar = new AikHrBloodSugar();
 			aikHrBloodSugar.setHrId(id);
 			aikHrBloodSugars = hrBloodSugarManageService.findAll(aikHrBloodSugar);
 			AikHrRoutineBlood aikHrRoutineBlood = new AikHrRoutineBlood();
 			aikHrRoutineBlood.setHrId(id);
 			aikHrRoutineBloods = hrRoutineBloodManageService.findAll(aikHrRoutineBlood);
 			AikHrTumorMarkers aikHrTumorMarkers = new AikHrTumorMarkers();
 			aikHrTumorMarkers.setHrId(id);
 			aikHrTumorMarkerss = hrTumorMarkersManageService.findAll(aikHrTumorMarkers);
 			logger.info("健康档案信息编辑查询成功");
 		} catch (Exception e) {
 			logger.error("健康档案信息编辑查询", e.getMessage());
		}
 		result.addObject("opt", "edit");
 		result.addObject("healthRecord", aikHealthRecordVo);
 		result.addObject("aikHrBloodSugars", aikHrBloodSugars);
 		result.addObject("aikHrRoutineBloods", aikHrRoutineBloods);
 		result.addObject("aikHrTumorMarkerss", aikHrTumorMarkerss);
        return result;
    }
    
    

    /**
     * 健康档案信息明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("healthRecord/healthRecordView");
        AikHealthRecordVo aikHealthRecordVo = new AikHealthRecordVo();
    	List<AikHealthRecordVo> aikHealthRecordVos = new ArrayList();
    	List<AikHrBloodSugar> aikHrBloodSugars = new ArrayList();
    	List<AikHrRoutineBlood> aikHrRoutineBloods = new ArrayList();
    	List<AikHrTumorMarkers> aikHrTumorMarkerss = new ArrayList();
 		try {
 			AikHealthRecord aikHealthRecord = new AikHealthRecord();
 			aikHealthRecord.setId(id);
 			aikHealthRecordVos = healthRecordManageService.findAll(aikHealthRecord);
 			if(aikHealthRecordVos != null && aikHealthRecordVos.size() > 0){
 				aikHealthRecordVo = aikHealthRecordVos.get(0);
 			}
 			AikHrBloodSugar aikHrBloodSugar = new AikHrBloodSugar();
 			aikHrBloodSugar.setHrId(id);
 			aikHrBloodSugars = hrBloodSugarManageService.findAll(aikHrBloodSugar);
 			AikHrRoutineBlood aikHrRoutineBlood = new AikHrRoutineBlood();
 			aikHrRoutineBlood.setHrId(id);
 			aikHrRoutineBloods = hrRoutineBloodManageService.findAll(aikHrRoutineBlood);
 			AikHrTumorMarkers aikHrTumorMarkers = new AikHrTumorMarkers();
 			aikHrTumorMarkers.setHrId(id);
 			aikHrTumorMarkerss = hrTumorMarkersManageService.findAll(aikHrTumorMarkers);
 			logger.info("健康档案信息编辑查询成功");
 		} catch (Exception e) {
 			logger.error("健康档案信息编辑查询", e.getMessage());
		}
 		result.addObject("opt", "view");
 		result.addObject("healthRecord", aikHealthRecordVo);
 		result.addObject("aikHrBloodSugars", aikHrBloodSugars);
 		result.addObject("aikHrRoutineBloods", aikHrRoutineBloods);
 		result.addObject("aikHrTumorMarkerss", aikHrTumorMarkerss);

        return result;
    }

    /**
     * 健康档案删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	healthRecordManageService.deleteByPrimaryKey(id);
        	data.put("code", "1");
        	data.put("info", "健康档案删除成功!");
			logger.info("健康档案删除成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "健康档案删除失败!原因未知异常");
			logger.error("健康档案删除失败!原因未知异常", e);
		}
        result.put("data", data);
        return result;
    }

    /**
     * 健康档案新增
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(AikHealthRecord aikHealthRecord) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	healthRecordManageService.save(aikHealthRecord);
        	data.put("code", "1");
        	data.put("info", "健康档案新增成功!");
			logger.info("健康档案新增成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "健康档案新增失败!原因未知异常");
			logger.error("健康档案新增失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("aikHealthRecord", aikHealthRecord);
        return result;
    }
    
    /**
     * 健康档案修改
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelMap update(AikHealthRecord aikHealthRecord) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	healthRecordManageService.update(aikHealthRecord);
        	data.put("code", "1");
        	data.put("info", "健康档案修改成功!");
			logger.info("健康档案修改成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "健康档案修改失败!原因未知异常");
			logger.error("健康档案修改失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("aikHealthRecord", aikHealthRecord);
        return result;
    }
    
    
    
    
    
}
