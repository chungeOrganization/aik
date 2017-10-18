package com.aik.controller;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aik.model.DietFoodCategory;
import com.aik.service.FoodCategoryManageService;
import com.aik.util.AikFileUtils;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 食物分类管理
 */
@RestController
@RequestMapping("/foodCategorys")
public class FoodCategoryController {
	
	private Logger logger = LoggerFactory.getLogger(FoodCategoryController.class);
	
	 @Value("${file.upload-root-uri}")
	 private String uploadRootUri;

    @Autowired
    private FoodCategoryManageService foodCategoryManageService;

    
    /**
     * 食物分类管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("foodCategory/foodCategoryManage");
         
         return result;
    }
    
    /**
     * 食物分类信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,DietFoodCategory dietFoodCategory,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("foodCategory/foodCategoryList");
    	Page<DietFoodCategory> dietFoodCategorys = new Page<DietFoodCategory>();
		try {
			Integer size = null;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	dietFoodCategorys = foodCategoryManageService.findPage(dietFoodCategory, pageRequest);
			logger.info("食物信息列表获取成功");
		} catch (Exception e) {
			logger.error("食物信息列表获取失败", e);
		}
    	PageInfo<DietFoodCategory> pageInfo = new PageInfo<DietFoodCategory>(dietFoodCategorys);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    /**
     * 食物分类信息新增
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {
    	 ModelAndView result = new ModelAndView("foodCategory/foodCategoryAdd");
    	 DietFoodCategory dietFoodCategory = new DietFoodCategory();
 		 result.addObject("dietFoodCategory", dietFoodCategory);
 		 logger.info("食物分类信息新增查询成功");
         return result;
    }
    
    
    
    /**
     * 食物分类信息编辑
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
    	ModelAndView result = new ModelAndView("foodCategory/foodCategoryView");
    	DietFoodCategory dietFoodCategory = new DietFoodCategory();
 		try {
 			dietFoodCategory = foodCategoryManageService.findById(id);
 			logger.info("食物分类信息编辑查询成功");
 		} catch (Exception e) {
 			logger.error("食物分类信息编辑查询", e.getMessage());
		}
 		result.addObject("opt", "edit");
 		result.addObject("dietFoodCategory", dietFoodCategory);
        return result;
    }
    
    

    /**
     * 食物分类信息明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("foodCategory/foodCategoryView");
        DietFoodCategory dietFoodCategory = new DietFoodCategory();
		try {
			dietFoodCategory = foodCategoryManageService.findById(id);
			logger.info("食物分类信息明细查询成功");
		}catch (Exception e) {
 			logger.error("食物分类信息明细查询", e);
		}
		result.addObject("opt", "view");
		result.addObject("dietFoodCategory", dietFoodCategory);
        return result;
    }

    /**
     * 食物分类删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	foodCategoryManageService.deleteByPrimaryKey(id);
        	data.put("code", "1");
        	data.put("info", "食物分类删除成功!");
			logger.info("食物分类删除成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "食物分类删除失败!原因未知异常");
			logger.error("食物分类删除失败!原因未知异常", e);
		}
        result.put("data", data);
        return result;
    }

    /**
     * 食物分类新增
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(DietFoodCategory dietFoodCategory, @RequestParam MultipartFile file) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	String imageName = Calendar.getInstance().getTimeInMillis()
                    + file.getName();

            String fileUri = "foodCategory" + File.separator + imageName;
            String uploadUrl = uploadRootUri + fileUri;
            AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);
            dietFoodCategory.setImage(imageName);
        	foodCategoryManageService.save(dietFoodCategory);
        	data.put("code", "1");
        	data.put("info", "食物分类新增成功!");
			logger.info("食物分类新增成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "食物分类新增失败!原因未知异常");
			logger.error("食物分类新增失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("dietFood", dietFoodCategory);
        return result;
    }
    
    /**
     * 食物分类修改
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelMap update(DietFoodCategory dietFoodCategory, @RequestParam MultipartFile file) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	
        	String imageName = Calendar.getInstance().getTimeInMillis()
                    + file.getName();

            String fileUri = "foodCategory" + File.separator + imageName;
            String uploadUrl = uploadRootUri + fileUri;
            AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);
            dietFoodCategory.setImage(imageName);
        	foodCategoryManageService.update(dietFoodCategory);
        	data.put("code", "1");
        	data.put("info", "食物分类修改成功!");
			logger.info("食物分类修改成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "食物分类修改失败!原因未知异常");
			logger.error("食物分类修改失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("dietFoodCategory", dietFoodCategory);
        return result;
    }
    
    
    
    
    
}
