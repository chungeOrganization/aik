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

import com.aik.model.AccCircleComment;
import com.aik.model.AccCircleLike;
import com.aik.model.AccMutualCircle;
import com.aik.model.AikHealthRecord;
import com.aik.model.AikHrBloodSugar;
import com.aik.model.AikHrRoutineBlood;
import com.aik.model.AikHrTumorMarkers;
import com.aik.service.CircleCommentManageService;
import com.aik.service.CircleLikeManageService;
import com.aik.service.HealthRecordManageService;
import com.aik.service.HrBloodSugarManageService;
import com.aik.service.HrRoutineBloodManageService;
import com.aik.service.HrTumorMarkersManageService;
import com.aik.service.MutualCircleManageService;
import com.aik.util.PageUtils;
import com.aik.vo.AccCircleCommentVo;
import com.aik.vo.AccCircleLikeVo;
import com.aik.vo.AccMutualCircleVo;
import com.aik.vo.AikHealthRecordVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 互助圈管理
 */
@RestController
@RequestMapping("/mutualCircle")
public class MutualCircleController {
	
	private Logger logger = LoggerFactory.getLogger(MutualCircleController.class);

    @Autowired
    private MutualCircleManageService mutualCircleManageService;
    
    @Autowired
    private CircleLikeManageService circleLikeManageService;
    
    
    @Autowired
    private CircleCommentManageService circleCommentManageService;
    
    
    /**
     * 互助圈管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("mutualCircle/mutualCircleManage");
         
         return result;
    }
    
    /**
     * 互助圈列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,AccMutualCircleVo accMutualCircleVo,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("mutualCircle/mutualCircleList");
    	Page<AccMutualCircleVo> accMutualCircleVos = new Page<AccMutualCircleVo>();
		try {
			Integer size = null;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	accMutualCircleVos = mutualCircleManageService.findPage(accMutualCircleVo, pageRequest);
			logger.info("互助圈列表获取成功");
		} catch (Exception e) {
			logger.error("互助圈列表获取失败", e);
		}
    	PageInfo<AccMutualCircleVo> pageInfo = new PageInfo<AccMutualCircleVo>(accMutualCircleVos);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    /**
     * 互助圈新增
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {
    	 ModelAndView result = new ModelAndView("mutualCircle/mutualCircleAdd");
    	 AccMutualCircle accMutualCircle = new AccMutualCircle();
 		 result.addObject("accMutualCircle", accMutualCircle);
 		 logger.info("互助圈新增查询成功");
         return result;
    }
    
    
    
    /**
     * 互助圈编辑
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
    	ModelAndView result = new ModelAndView("mutualCircle/mutualCircleView");
        AccMutualCircleVo accMutualCircleVo = new AccMutualCircleVo();
        
        List<AccMutualCircleVo> accMutualCircles = new ArrayList();
    	List<AccCircleCommentVo> accCircleComments = new ArrayList();
    	List<AccCircleLikeVo> accCircleLikes = new ArrayList();
 		try {
 			AccMutualCircle accMutualCircle = new AccMutualCircle();
 		    accMutualCircle.setId(id);
 			accMutualCircles = mutualCircleManageService.findAll(accMutualCircle);
 			if(accMutualCircles != null && accMutualCircles.size() > 0){
 				accMutualCircleVo = accMutualCircles.get(0);
 			}
 			AccCircleComment accCircleComment = new AccCircleComment();
 			accCircleComment.setCircleId(id);
 			accCircleComments = circleCommentManageService.findAll(accCircleComment);
 			AccCircleLike accCircleLike = new AccCircleLike();
 			accCircleLike.setCircleId(id);
 			accCircleLikes = circleLikeManageService.findAll(accCircleLike);
 			logger.info("互助圈编辑查询成功");
 		} catch (Exception e) {
 			logger.error("互助圈编辑查询", e.getMessage());
		}
 		result.addObject("opt", "edit");
 		result.addObject("mutualCircle", accMutualCircleVo);
 		result.addObject("accCircleLikes", accCircleLikes);
 		result.addObject("accCircleComments", accCircleComments);
        return result;
    }
    
    

    /**
     * 互助圈明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("mutualCircle/mutualCircleView");
        
        AccMutualCircleVo accMutualCircleVo = new AccMutualCircleVo();
        
      
        List<AccMutualCircleVo> accMutualCircles = new ArrayList();
    	List<AccCircleCommentVo> accCircleComments = new ArrayList();
    	List<AccCircleLikeVo> accCircleLikes = new ArrayList();
 		try {
 			AccMutualCircle accMutualCircle = new AccMutualCircle();
 		    accMutualCircle.setId(id);
 			accMutualCircles = mutualCircleManageService.findAll(accMutualCircle);
 			if(accMutualCircles != null && accMutualCircles.size() > 0){
 				accMutualCircleVo = accMutualCircles.get(0);
 			}
 			
 			AccCircleComment accCircleComment = new AccCircleComment();
 			accCircleComment.setCircleId(id);
 			accCircleComments = circleCommentManageService.findAll(accCircleComment);
 			AccCircleLike accCircleLike = new AccCircleLike();
 			accCircleLike.setCircleId(id);
 			accCircleLikes = circleLikeManageService.findAll(accCircleLike);
 			logger.info("互助圈编辑查询成功");
 		} catch (Exception e) {
 			logger.error("互助圈编辑查询", e.getMessage());
		}
 		result.addObject("opt", "view");
 		result.addObject("mutualCircle", accMutualCircleVo);
 		result.addObject("accCircleLikes", accCircleLikes);
 		result.addObject("accCircleComments", accCircleComments);
        return result;
    }

    /**
     * 互助圈删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	mutualCircleManageService.deleteByPrimaryKey(id);
        	data.put("code", "1");
        	data.put("info", "互助圈删除成功!");
			logger.info("互助圈删除成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "互助圈删除失败!原因未知异常");
			logger.error("互助圈删除失败!原因未知异常", e);
		}
        result.put("data", data);
        return result;
    }

    /**
     * 互助圈新增
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(AccMutualCircle accMutualCircle) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	mutualCircleManageService.save(accMutualCircle);
        	data.put("code", "1");
        	data.put("info", "互助圈新增成功!");
			logger.info("互助圈新增成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "互助圈新增失败!原因未知异常");
			logger.error("互助圈新增失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("accMutualCircle", accMutualCircle);
        return result;
    }
    
    /**
     * 互助圈修改
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelMap update(AccMutualCircle accMutualCircle) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	mutualCircleManageService.update(accMutualCircle);
        	data.put("code", "1");
        	data.put("info", "互助圈修改成功!");
			logger.info("互助圈修改成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "互助圈修改失败!原因未知异常");
			logger.error("互助圈修改失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("accMutualCircle", accMutualCircle);
        return result;
    }
    
    
    
    
    
}
