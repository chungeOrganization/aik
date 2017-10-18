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

import com.aik.model.StoGoods;
import com.aik.service.GoodsManageService;
import com.aik.util.AikFileUtils;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 商品库管理
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
	
	private Logger logger = LoggerFactory.getLogger(GoodsController.class);
	
	@Value("${file.upload-root-uri}")
	 private String uploadRootUri;

    @Autowired
    private GoodsManageService goodsManageService;

    
    /**
     * 商品管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("goods/goodsManage");
         
         return result;
    }
    
    /**
     * 商品信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,StoGoods stoGoods,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("goods/goodsList");
    	Page<StoGoods> stoGoodsList = new Page<StoGoods>();
		try {
			Integer size = null;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	stoGoodsList = goodsManageService.findPage(stoGoods, pageRequest);
			logger.info("商品信息列表获取成功");
		} catch (Exception e) {
			logger.error("商品信息列表获取失败", e);
		}
    	PageInfo<StoGoods> pageInfo = new PageInfo<StoGoods>(stoGoodsList);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    /**
     * 商品信息新增
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {
    	 ModelAndView result = new ModelAndView("goods/goodsAdd");
    	 StoGoods stoGoods = new StoGoods();
 		 result.addObject("stoGoods", stoGoods);
 		 logger.info("商品信息新增查询成功");
         return result;
    }
    
    
    
    /**
     * 商品信息编辑
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
    	ModelAndView result = new ModelAndView("goods/goodsView");
    	StoGoods stoGoods = new StoGoods();
 		try {
 			stoGoods = goodsManageService.findById(id);
 			logger.info("商品信息编辑查询成功");
 		} catch (Exception e) {
 			logger.error("商品信息编辑查询", e.getMessage());
		}
 		result.addObject("opt", "edit");
 		result.addObject("stoGoods", stoGoods);
        return result;
    }
    
    

    /**
     * 商品信息明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("goods/goodsView");
        StoGoods stoGoods = new StoGoods();
		try {
			stoGoods = goodsManageService.findById(id);
			logger.info("商品信息明细查询成功");
		}catch (Exception e) {
 			logger.error("商品信息明细查询", e);
		}
		result.addObject("opt", "view");
		result.addObject("stoGoods", stoGoods);
        return result;
    }

    /**
     * 商品删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	goodsManageService.deleteByPrimaryKey(id);
        	data.put("code", "1");
        	data.put("info", "商品删除成功!");
			logger.info("商品删除成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "商品删除失败!原因未知异常");
			logger.error("商品删除失败!原因未知异常", e);
		}
        result.put("data", data);
        return result;
    }

    /**
     * 商品新增
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(StoGoods stoGoods, @RequestParam MultipartFile file) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	String imageName = Calendar.getInstance().getTimeInMillis()
                    + file.getName();

            String fileUri = "goods" + File.separator + imageName;
            String uploadUrl = uploadRootUri + fileUri;
            AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);
            stoGoods.setImage(imageName);
        	goodsManageService.save(stoGoods);
        	data.put("code", "1");
        	data.put("info", "商品新增成功!");
			logger.info("商品新增成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "商品新增失败!原因未知异常");
			logger.error("商品新增失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("stoGoods", stoGoods);
        return result;
    }
    
    /**
     * 商品修改
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelMap update(StoGoods stoGoods, @RequestParam MultipartFile file) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	String imageName = Calendar.getInstance().getTimeInMillis()
                    + file.getName();

            String fileUri = "goods" + File.separator + imageName;
            String uploadUrl = uploadRootUri + fileUri;
            AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);
            stoGoods.setImage(imageName);
        	goodsManageService.update(stoGoods);
        	data.put("code", "1");
        	data.put("info", "商品修改成功!");
			logger.info("商品修改成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "商品修改失败!原因未知异常");
			logger.error("商品修改失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("stoGoods", stoGoods);
        return result;
    }
    
    
    
    
    
}
