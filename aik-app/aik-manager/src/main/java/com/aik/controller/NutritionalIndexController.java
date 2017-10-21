package com.aik.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aik.model.DietUserCollectFood;
import com.aik.service.FoodCollectManageService;
import com.aik.service.NutritionalIndexManageService;
import com.aik.util.PageUtils;
import com.aik.vo.AikNutritionalIndexVo;
import com.aik.vo.DietUserCollectFoodVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message BMI管理
 */
@RestController
@RequestMapping("/nutritionalIndex")
public class NutritionalIndexController {
	
	private Logger logger = LoggerFactory.getLogger(NutritionalIndexController.class);

    @Autowired
    private NutritionalIndexManageService nutritionalIndexManageService;

    
    /**
     * BMI管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("nutritionalIndex/nutritionalIndexManage");
         
         return result;
    }
    
    /**
     * BMI信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,AikNutritionalIndexVo aikNutritionalIndexVo,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("nutritionalIndex/nutritionalIndexList");
    	Page<AikNutritionalIndexVo> aikNutritionalIndexVos = new Page<AikNutritionalIndexVo>();
		try {
			Integer size = null;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	aikNutritionalIndexVos = nutritionalIndexManageService.findPage( aikNutritionalIndexVo, pageRequest);
			logger.info("BMI信息列表获取成功");
		} catch (Exception e) {
			logger.error("BMI信息列表获取失败", e);
		}
    	PageInfo<AikNutritionalIndexVo> pageInfo = new PageInfo<AikNutritionalIndexVo>(aikNutritionalIndexVos);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    
    
    
    
    
    
}
