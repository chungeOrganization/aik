package com.aik.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aik.service.DailyNutritionManageService;
import com.aik.service.DietPlanManageService;
import com.aik.util.PageUtils;
import com.aik.vo.DietDailyDietPlanVo;
import com.aik.vo.DietDailyNutritionVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 营养元素摄于量对比分析管理
 */
@RestController
@RequestMapping("/dailyNutrition")
public class DailyNutritionController {
	
	private Logger logger = LoggerFactory.getLogger(DailyNutritionController.class);

    @Autowired
    private DailyNutritionManageService dailyNutritionManageService;
    
    @InitBinder
  	public void initBinder(WebDataBinder binder) {
  		SimpleDateFormat dateFormat = new SimpleDateFormat(
  				"yyyy-MM-dd HH:mm:ss");
  		SimpleDateFormat stampFormat = new SimpleDateFormat("yyyy-MM-dd");
  		dateFormat.setLenient(false);
  		stampFormat.setLenient(false);
  		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true,19));
  		binder.registerCustomEditor(Date.class, new CustomDateEditor(stampFormat, true, 10)); 
  	}
      

    
    /**
     * 营养元素摄于量对比分析管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("dailyNutrition/dailyNutritionManage");
         
         return result;
    }
    
    /**
     * 营养元素摄于量对比分析信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,DietDailyNutritionVo dietDailyNutritionVo,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("dailyNutrition/dailyNutritionList");
    	Page<DietDailyNutritionVo> dietDailyNutritionVos = new Page<DietDailyNutritionVo>();
		try {
			Integer size = null;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	dietDailyNutritionVos = dailyNutritionManageService.findPage(dietDailyNutritionVo, pageRequest);
			logger.info("营养元素摄于量对比分析信息列表获取成功");
		} catch (Exception e) {
			logger.error("营养元素摄于量对比分析信息列表获取失败", e);
		}
    	PageInfo<DietDailyNutritionVo> pageInfo = new PageInfo<DietDailyNutritionVo>(dietDailyNutritionVos);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    
    
    
    
    
    
}
