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

import com.aik.model.DietUserCollectFood;
import com.aik.service.DietRecordManageService;
import com.aik.util.PageUtils;
import com.aik.vo.DietDailyDietRecordVo;
import com.aik.vo.DietUserCollectFoodVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 饮食实际记录管理
 */
@RestController
@RequestMapping("/dietRecords")
public class DietRecordController {
	
	private Logger logger = LoggerFactory.getLogger(DietRecordController.class);

    @Autowired
    private DietRecordManageService dietRecordManageService;
    
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
     * 饮食实际记录管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("dietRecord/dietRecordManage");
         
         return result;
    }
    
    /**
     * 饮食实际记录信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,DietDailyDietRecordVo dietUserCollectFoodVo,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("dietRecord/dietRecordList");
    	Page<DietDailyDietRecordVo> dietDailyDietRecords = new Page<DietDailyDietRecordVo>();
		try {
			Integer size = 3;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	dietDailyDietRecords = dietRecordManageService.findPage(dietUserCollectFoodVo, pageRequest);
			logger.info("饮食实际记录信息列表获取成功");
		} catch (Exception e) {
			logger.error("饮食实际记录信息列表获取失败", e);
		}
    	PageInfo<DietDailyDietRecordVo> pageInfo = new PageInfo<DietDailyDietRecordVo>(dietDailyDietRecords);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    
    
    
    
    
    
}
