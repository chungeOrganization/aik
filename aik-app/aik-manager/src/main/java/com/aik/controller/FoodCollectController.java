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
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 食物收藏管理
 */
@RestController
@RequestMapping("/foodCollects")
public class FoodCollectController {
	
	private Logger logger = LoggerFactory.getLogger(FoodCollectController.class);

    @Autowired
    private FoodCollectManageService foodCollectManageService;

    
    /**
     * 食物收藏管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("foodCollect/foodCollectManage");
         
         return result;
    }
    
    /**
     * 食物收藏信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,DietUserCollectFood dietUserCollectFood,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("foodCollect/foodCollectList");
    	Page<DietUserCollectFood> dietUserCollectFoods = new Page<DietUserCollectFood>();
		try {
			Integer size = 3;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	dietUserCollectFoods = foodCollectManageService.findPage(dietUserCollectFood, pageRequest);
			logger.info("食物收藏信息列表获取成功");
		} catch (Exception e) {
			logger.error("食物收藏信息列表获取失败", e);
		}
    	PageInfo<DietUserCollectFood> pageInfo = new PageInfo<DietUserCollectFood>(dietUserCollectFoods);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    
    
    
    
    
    
}
