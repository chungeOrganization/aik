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

import com.aik.model.AikQuestion;
import com.aik.model.AikQuestionOrder;
import com.aik.model.StoUserOrder;
import com.aik.model.StoUserOrderDetail;
import com.aik.service.GoodsOrderDetailManageService;
import com.aik.service.GoodsOrderManageService;
import com.aik.service.QuestionManageService;
import com.aik.service.QuestionOrderManageService;
import com.aik.util.PageUtils;
import com.aik.vo.AikQuestionOrderVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 问题订单库管理
 */
@RestController
@RequestMapping("/questionOrders")
public class QuestionOrderController {
	
	private Logger logger = LoggerFactory.getLogger(QuestionOrderController.class);

    @Autowired
    private QuestionOrderManageService questionOrderManageService;
    
    @Autowired
    private QuestionManageService questionManageService;

    
    /**
     * 问题订单管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("questionOrder/questionOrderManage");
         
         return result;
    }
    
    /**
     * 问题订单信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,AikQuestionOrder aikQuestionOrder,@PathVariable Integer num) {
       
    	
    	ModelAndView mv = new ModelAndView("questionOrder/questionOrderList");
    	Page<AikQuestionOrderVo> aikQuestionOrderList = new Page<AikQuestionOrderVo>();
		try {
			Integer size = 3;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	aikQuestionOrderList = questionOrderManageService.findPage(aikQuestionOrder, pageRequest);
			logger.info("问题订单信息列表获取成功");
		} catch (Exception e) {
			logger.error("问题订单信息列表获取失败", e);
		}
    	PageInfo<AikQuestionOrderVo> pageInfo = new PageInfo<AikQuestionOrderVo>(aikQuestionOrderList);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    /**
     * 问题订单信息新增
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {
    	 ModelAndView result = new ModelAndView("questionOrder/questionOrderAdd");
    	 StoUserOrder stoUserOrder = new StoUserOrder();
 		 result.addObject("stoUserOrder", stoUserOrder);
 		 logger.info("问题订单信息新增查询成功");
         return result;
    }
    
    
    
    /**
     * 问题订单信息编辑
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
    	ModelAndView result = new ModelAndView("questionOrder/questionOrderView");
    	AikQuestionOrder aikQuestionOrder = new AikQuestionOrder();
    	List<AikQuestion> aikQuestions = new ArrayList();
 		try {
 			aikQuestionOrder = questionOrderManageService.findById(id);
 			AikQuestion aikQuestion = new AikQuestion();
 			aikQuestion.setOrderId(aikQuestionOrder.getId());
 			aikQuestions =  questionManageService.findAll(aikQuestion);
 			logger.info("问题订单信息编辑查询成功");
 		} catch (Exception e) {
 			logger.error("问题订单信息编辑查询", e.getMessage());
		}
 		result.addObject("opt", "edit");
 		result.addObject("aikQuestionOrder", aikQuestionOrder);
 		result.addObject("aikQuestions", aikQuestions);
        return result;
    }
    
    

    /**
     * 问题订单信息明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("questionOrder/questionOrderView");
        AikQuestionOrder aikQuestionOrder = new AikQuestionOrder();
    	List<AikQuestion> aikQuestions = new ArrayList();

		try {
			aikQuestionOrder = questionOrderManageService.findById(id);
 			AikQuestion aikQuestion = new AikQuestion();
 			aikQuestion.setOrderId(aikQuestionOrder.getId());
 			aikQuestions =  questionManageService.findAll(aikQuestion);
			logger.info("问题订单信息明细查询成功");
		}catch (Exception e) {
 			logger.error("问题订单信息明细查询", e);
		}
		result.addObject("opt", "view");
		result.addObject("aikQuestionOrder", aikQuestionOrder);
 		result.addObject("aikQuestions", aikQuestions);

        return result;
    }

    /**
     * 问题订单删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	questionOrderManageService.deleteByPrimaryKey(id);
        	data.put("code", "1");
        	data.put("info", "问题订单删除成功!");
			logger.info("问题订单删除成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "问题订单删除失败!原因未知异常");
			logger.error("问题订单删除失败!原因未知异常", e);
		}
        result.put("data", data);
        return result;
    }

    /**
     * 问题订单新增
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(AikQuestionOrder aikQuestionOrder) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	questionOrderManageService.save(aikQuestionOrder);
        	data.put("code", "1");
        	data.put("info", "问题订单新增成功!");
			logger.info("问题订单新增成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "问题订单新增失败!原因未知异常");
			logger.error("问题订单新增失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("aikQuestionOrder", aikQuestionOrder);
        return result;
    }
    
    /**
     * 问题订单修改
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelMap update(AikQuestionOrder aikQuestionOrder) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	questionOrderManageService.update(aikQuestionOrder);
        	data.put("code", "1");
        	data.put("info", "问题订单修改成功!");
			logger.info("问题订单修改成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "问题订单修改失败!原因未知异常");
			logger.error("问题订单修改失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("aikQuestionOrder", aikQuestionOrder);
        return result;
    }
    
    
    
    
    
}
