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

import com.aik.model.AikNutritionLesson;
import com.aik.model.DietFoodCategory;
import com.aik.service.ArticleManageService;
import com.aik.service.FoodCategoryManageService;
import com.aik.util.AikFileUtils;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 文章管理
 */
@RestController
@RequestMapping("/article")
public class ArticleyController {
	
	private Logger logger = LoggerFactory.getLogger(ArticleyController.class);
	
	 @Value("${file.upload-root-uri}")
	 private String uploadRootUri;

    @Autowired
    private ArticleManageService articleManageService;

    
    /**
     * 文章管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("article/articleManage");
         
         return result;
    }
    
    /**
     * 文章信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,AikNutritionLesson aikNutritionLesson,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("article/articleList");
    	Page<AikNutritionLesson> aikNutritionLessons = new Page<AikNutritionLesson>();
		try {
			Integer size = null;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	aikNutritionLessons = articleManageService.findPage(aikNutritionLesson, pageRequest);
			logger.info("食物信息列表获取成功");
		} catch (Exception e) {
			logger.error("食物信息列表获取失败", e);
		}
    	PageInfo<AikNutritionLesson> pageInfo = new PageInfo<AikNutritionLesson>(aikNutritionLessons);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    /**
     * 文章信息新增
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {
    	 ModelAndView result = new ModelAndView("article/articleAdd");
    	 AikNutritionLesson aikNutritionLesson = new AikNutritionLesson();
 		 result.addObject("aikNutritionLesson", aikNutritionLesson);
 		 logger.info("文章信息新增查询成功");
         return result;
    }
    
    
    
    /**
     * 文章信息编辑
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
    	ModelAndView result = new ModelAndView("article/articleView");
    	AikNutritionLesson aikNutritionLesson = new AikNutritionLesson();
 		try {
 			aikNutritionLesson = articleManageService.findById(id);
 			logger.info("文章信息编辑查询成功");
 		} catch (Exception e) {
 			logger.error("文章信息编辑查询", e.getMessage());
		}
 		result.addObject("opt", "edit");
 		result.addObject("aikNutritionLesson", aikNutritionLesson);
        return result;
    }
    
    

    /**
     * 文章信息明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("article/articleView");
        AikNutritionLesson aikNutritionLesson = new AikNutritionLesson();
		try {
			aikNutritionLesson = articleManageService.findById(id);
			logger.info("文章信息明细查询成功");
		}catch (Exception e) {
 			logger.error("文章信息明细查询", e);
		}
		result.addObject("opt", "view");
		result.addObject("aikNutritionLesson", aikNutritionLesson);
        return result;
    }

    /**
     * 文章删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	articleManageService.deleteByPrimaryKey(id);
        	data.put("code", "1");
        	data.put("info", "文章删除成功!");
			logger.info("文章删除成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "文章删除失败!原因未知异常");
			logger.error("文章删除失败!原因未知异常", e);
		}
        result.put("data", data);
        return result;
    }

    /**
     * 文章新增
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(AikNutritionLesson aikNutritionLesson, @RequestParam MultipartFile file) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	String imageName = Calendar.getInstance().getTimeInMillis()
                    + file.getName();

            String fileUri = "article" + File.separator + imageName;
            String uploadUrl = uploadRootUri + fileUri;
            AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);
            aikNutritionLesson.setImage(imageName);
            articleManageService.save(aikNutritionLesson);
        	data.put("code", "1");
        	data.put("info", "文章新增成功!");
			logger.info("文章新增成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "文章新增失败!原因未知异常");
			logger.error("文章新增失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("aikNutritionLesson", aikNutritionLesson);
        return result;
    }
    
    /**
     * 文章修改
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelMap update(AikNutritionLesson aikNutritionLesson, @RequestParam MultipartFile file) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	
        	String imageName = Calendar.getInstance().getTimeInMillis()
                    + file.getName();

            String fileUri = "article" + File.separator + imageName;
            String uploadUrl = uploadRootUri + fileUri;
            AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);
            aikNutritionLesson.setImage(imageName);
            articleManageService.update(aikNutritionLesson);
        	data.put("code", "1");
        	data.put("info", "文章修改成功!");
			logger.info("文章修改成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "文章修改失败!原因未知异常");
			logger.error("文章修改失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("aikNutritionLesson", aikNutritionLesson);
        return result;
    }
    
    
    
    
    
}
