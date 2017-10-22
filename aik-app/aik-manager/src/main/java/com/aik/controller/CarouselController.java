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

import com.aik.model.AikCarousel;
import com.aik.model.DietFoodCategory;
import com.aik.service.CarouselManageService;
import com.aik.service.FoodCategoryManageService;
import com.aik.util.AikFileUtils;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 轮播图管理
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {
	
	private Logger logger = LoggerFactory.getLogger(CarouselController.class);
	
	 @Value("${file.upload-root-uri}")
	 private String uploadRootUri;

    @Autowired
    private CarouselManageService carouselManageService;

    
    /**
     * 轮播图管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("carousel/carouselManage");
         
         return result;
    }
    
    /**
     * 轮播图列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,AikCarousel aikCarousel,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("carousel/carouselList");
    	Page<AikCarousel> aikCarousels = new Page<AikCarousel>();
		try {
			Integer size = null;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	aikCarousels = carouselManageService.findPage(aikCarousel, pageRequest);
			logger.info("食物信息列表获取成功");
		} catch (Exception e) {
			logger.error("食物信息列表获取失败", e);
		}
    	PageInfo<AikCarousel> pageInfo = new PageInfo<AikCarousel>(aikCarousels);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    /**
     * 轮播图新增
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {
    	 ModelAndView result = new ModelAndView("carousel/carouselAdd");
    	 AikCarousel aikCarousel = new AikCarousel();
 		 result.addObject("aikCarousel", aikCarousel);
 		 logger.info("轮播图新增查询成功");
         return result;
    }
    
    
    
    /**
     * 轮播图编辑
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
    	ModelAndView result = new ModelAndView("carousel/carouselView");
    	AikCarousel aikCarousel = new AikCarousel();
 		try {
 			aikCarousel = carouselManageService.findById(id);
 			logger.info("轮播图编辑查询成功");
 		} catch (Exception e) {
 			logger.error("轮播图编辑查询", e.getMessage());
		}
 		result.addObject("opt", "edit");
 		result.addObject("aikCarousel", aikCarousel);
        return result;
    }
    
    

    /**
     * 轮播图明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Long id) {
        ModelAndView result = new ModelAndView("carousel/carouselView");
        AikCarousel aikCarousel = new AikCarousel();
		try {
			aikCarousel = carouselManageService.findById(id);
			logger.info("轮播图明细查询成功");
		}catch (Exception e) {
 			logger.error("轮播图明细查询", e);
		}
		result.addObject("opt", "view");
		result.addObject("aikCarousel", aikCarousel);
        return result;
    }

    /**
     * 轮播图删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Long id) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	carouselManageService.deleteByPrimaryKey(id);
        	data.put("code", "1");
        	data.put("info", "轮播图删除成功!");
			logger.info("轮播图删除成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "轮播图删除失败!原因未知异常");
			logger.error("轮播图删除失败!原因未知异常", e);
		}
        result.put("data", data);
        return result;
    }

    /**
     * 轮播图新增
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(AikCarousel aikCarousel, @RequestParam MultipartFile file) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	String imageName = Calendar.getInstance().getTimeInMillis()
                    + file.getName();

            String fileUri = "carousel" + File.separator + imageName;
            String uploadUrl = uploadRootUri + fileUri;
            AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);
            aikCarousel.setImage(imageName);
            carouselManageService.save(aikCarousel);
        	data.put("code", "1");
        	data.put("info", "轮播图新增成功!");
			logger.info("轮播图新增成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "轮播图新增失败!原因未知异常");
			logger.error("轮播图新增失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("aikCarousel", aikCarousel);
        return result;
    }
    
    /**
     * 轮播图修改
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelMap update(AikCarousel aikCarousel, @RequestParam MultipartFile file) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	
        	String imageName = Calendar.getInstance().getTimeInMillis()
                    + file.getName();

            String fileUri = "carousel" + File.separator + imageName;
            String uploadUrl = uploadRootUri + fileUri;
            AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);
            aikCarousel.setImage(imageName);
            carouselManageService.update(aikCarousel);
        	data.put("code", "1");
        	data.put("info", "轮播图修改成功!");
			logger.info("轮播图修改成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "轮播图修改失败!原因未知异常");
			logger.error("轮播图修改失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("aikCarousel", aikCarousel);
        return result;
    }
    
    
    
    
    
}
