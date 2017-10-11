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

import com.aik.model.StoUserOrder;
import com.aik.model.StoUserOrderDetail;
import com.aik.service.GoodsOrderDetailManageService;
import com.aik.service.GoodsOrderManageService;
import com.aik.util.PageUtils;
import com.aik.vo.StoUserOrderVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 商品订单库管理
 */
@RestController
@RequestMapping("/goodsOrders")
public class GoodsOrderController {
	
	private Logger logger = LoggerFactory.getLogger(GoodsOrderController.class);

    @Autowired
    private GoodsOrderManageService goodsOrderManageService;
    
    @Autowired
    private GoodsOrderDetailManageService goodsOrderDetailManageService;

    
    /**
     * 商品订单管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("goodsOrder/goodsOrderManage");
         
         return result;
    }
    
    /**
     * 商品订单信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,StoUserOrder stoUserOrder,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("goodsOrder/goodsOrderList");
    	Page<StoUserOrderVo> stoGoodsOrderList = new Page<StoUserOrderVo>();
		try {
			Integer size = 3;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	stoGoodsOrderList = goodsOrderManageService.findPage(stoUserOrder, pageRequest);
			logger.info("商品订单信息列表获取成功");
		} catch (Exception e) {
			logger.error("商品订单信息列表获取失败", e);
		}
    	PageInfo<StoUserOrderVo> pageInfo = new PageInfo<StoUserOrderVo>(stoGoodsOrderList);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    /**
     * 商品订单信息新增
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {
    	 ModelAndView result = new ModelAndView("goodsOrder/goodsOrderAdd");
    	 StoUserOrder stoUserOrder = new StoUserOrder();
 		 result.addObject("stoUserOrder", stoUserOrder);
 		 logger.info("商品订单信息新增查询成功");
         return result;
    }
    
    
    
    /**
     * 商品订单信息编辑
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
    	ModelAndView result = new ModelAndView("goodsOrder/goodsOrderView");
    	StoUserOrder stoUserOrder = new StoUserOrder();
    	List<StoUserOrderDetail> stoUserOrderDetails = new ArrayList();
 		try {
 			stoUserOrder = goodsOrderManageService.findById(id);
 			StoUserOrderDetail stoUserOrderDetail = new StoUserOrderDetail();
 			stoUserOrderDetail.setOrderId(stoUserOrder.getId());
 			stoUserOrderDetails =  goodsOrderDetailManageService.findAll(stoUserOrderDetail);
 			logger.info("商品订单信息编辑查询成功");
 		} catch (Exception e) {
 			logger.error("商品订单信息编辑查询", e.getMessage());
		}
 		result.addObject("opt", "edit");
 		result.addObject("stoUserOrder", stoUserOrder);
 		result.addObject("stoUserOrderDetails", stoUserOrderDetails);
        return result;
    }
    
    

    /**
     * 商品订单信息明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("goodsOrder/goodsOrderView");
        StoUserOrder stoUserOrder = new StoUserOrder();
    	List<StoUserOrderDetail> stoUserOrderDetails = new ArrayList();

		try {
			stoUserOrder = goodsOrderManageService.findById(id);
			StoUserOrderDetail stoUserOrderDetail = new StoUserOrderDetail();
 			stoUserOrderDetail.setOrderId(stoUserOrder.getId());
 			stoUserOrderDetails =  goodsOrderDetailManageService.findAll(stoUserOrderDetail);
			logger.info("商品订单信息明细查询成功");
		}catch (Exception e) {
 			logger.error("商品订单信息明细查询", e);
		}
		result.addObject("opt", "view");
		result.addObject("stoUserOrder", stoUserOrder);
 		result.addObject("stoUserOrderDetails", stoUserOrderDetails);

        return result;
    }

    /**
     * 商品订单删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	goodsOrderManageService.deleteByPrimaryKey(id);
        	data.put("code", "1");
        	data.put("info", "商品订单删除成功!");
			logger.info("商品订单删除成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "商品订单删除失败!原因未知异常");
			logger.error("商品订单删除失败!原因未知异常", e);
		}
        result.put("data", data);
        return result;
    }

    /**
     * 商品订单新增
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(StoUserOrder stoUserOrder) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	goodsOrderManageService.save(stoUserOrder);
        	data.put("code", "1");
        	data.put("info", "商品订单新增成功!");
			logger.info("商品订单新增成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "商品订单新增失败!原因未知异常");
			logger.error("商品订单新增失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("stoUserOrder", stoUserOrder);
        return result;
    }
    
    /**
     * 商品订单修改
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelMap update(StoUserOrder stoUserOrder) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	goodsOrderManageService.update(stoUserOrder);
        	data.put("code", "1");
        	data.put("info", "商品订单修改成功!");
			logger.info("商品订单修改成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "商品订单修改失败!原因未知异常");
			logger.error("商品订单修改失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("stoUserOrder", stoUserOrder);
        return result;
    }
    
    
    
    
    
}
