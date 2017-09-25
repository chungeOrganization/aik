package com.aik.controller;

import java.util.HashMap;
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

import com.aik.model.DietFood;
import com.aik.service.FoodManageService;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 食物库管理
 */
@RestController
@RequestMapping("/foods")
public class FoodController {
	
	private Logger logger = LoggerFactory.getLogger(FoodController.class);

    @Autowired
    private FoodManageService foodManageService;

    
    /**
     * 食物信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping
    public PageInfo<DietFood> queryPage(HttpServletRequest request, HttpServletResponse response) {
       
    	
    	Page<DietFood> dietFoods = new Page<DietFood>();
		try {
			Integer page = Integer.parseInt(request.getParameter("pageNo"));
	    	Integer size = Integer.parseInt(request.getParameter("rowsPerPage"));
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	DietFood dietFood = new DietFood();
			dietFoods = foodManageService.findPage(dietFood, pageRequest);
			logger.info("食物信息列表获取成功");
		} catch (Exception e) {
			logger.error("食物信息列表获取失败", e);
		}
    	PageInfo<DietFood> pageInfo = new PageInfo<>(dietFoods);
        return pageInfo;
    }

    /**
     * 食物信息新增
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {
    	 ModelAndView result = new ModelAndView();
    	 DietFood dietFood = new DietFood();
 		 result.addObject("dietFood", dietFood);
 		logger.info("食物信息新增成功");
         return result;
    }
    
    
    
    /**
     * 食物信息编辑
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
    	ModelAndView result = new ModelAndView();
    	DietFood dietFood = new DietFood();
 		try {
 			dietFood = foodManageService.findById(id);
 			logger.info("食物信息编辑查询成功");
 		} catch (Exception e) {
 			logger.error("食物信息编辑查询", e.getMessage());
		}
 		result.addObject("opt", "edit");
 		result.addObject("dietFood", dietFood);
        return result;
    }
    
    

    /**
     * 食物信息明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView();
        DietFood dietFood = new DietFood();
		try {
			dietFood = foodManageService.findById(id);
			logger.info("食物信息明细查询成功");
		}catch (Exception e) {
 			logger.error("食物信息明细查询", e);
		}
		result.addObject("opt", "view");
		result.addObject("dietFood", dietFood);
        return result;
    }

    /**
     * 食物删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	foodManageService.deleteByPrimaryKey(id);
        	data.put("code", "1");
        	data.put("info", "食物删除成功!");
			logger.info("食物删除成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "食物删除失败!原因未知异常");
			logger.error("食物删除失败!原因未知异常", e);
		}
        result.put("data", data);
        return result;
    }

    /**
     * 食物新增
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(DietFood dietFood) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	foodManageService.save(dietFood);
        	data.put("code", "1");
        	data.put("info", "食物新增成功!");
			logger.info("食物新增成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "食物新增失败!原因未知异常");
			logger.error("食物新增失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("dietFood", dietFood);
        return result;
    }
    
    /**
     * 食物修改
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelMap update(DietFood dietFood) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	foodManageService.update(dietFood);
        	data.put("code", "1");
        	data.put("info", "食物修改成功!");
			logger.info("食物修改成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "食物修改失败!原因未知异常");
			logger.error("食物修改失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("dietFood", dietFood);
        return result;
    }
    
    
    
    
    
}
