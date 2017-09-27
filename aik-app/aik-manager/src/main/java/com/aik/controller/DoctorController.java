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

import com.aik.model.AccDoctorAccount;
import com.aik.model.AccUserAccount;
import com.aik.service.DoctorManageService;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 医生管理
 */
@RestController
@RequestMapping("/doctors")
public class DoctorController {
	
	private Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private DoctorManageService doctorManageService;
    
    
    /**
     * 医生信息管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("doctor/doctorManage");
         
         return result;
    }

    
    /**
     * 医生信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,AccDoctorAccount accDoctorAccount,@PathVariable Integer num) {
       
    	ModelAndView mv = new ModelAndView("doctor/doctorList");
    	Page<AccDoctorAccount> accDoctorAccounts = new Page<AccDoctorAccount>();
		try {
			Integer size = 3;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
	    	accDoctorAccounts = doctorManageService.findPage(accDoctorAccount, pageRequest);
			logger.info("医生信息列表获取成功");
		} catch (Exception e) {
			logger.error("医生信息列表获取失败", e);
		}
    	PageInfo<AccDoctorAccount> pageInfo = new PageInfo<AccDoctorAccount>(accDoctorAccounts);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    /**
     * 医生信息新增
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {
    	 ModelAndView result = new ModelAndView("doctor/doctorAdd");
    	 AccDoctorAccount accDoctorAccount = new AccDoctorAccount();
 		 result.addObject("accDoctorAccount", accDoctorAccount);
 		 logger.info("医生信息新增查询成功");
         return result;
    }
    
    
    
    /**
     * 医生信息编辑
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
    	ModelAndView result = new ModelAndView("doctor/doctorView");
    	AccDoctorAccount accDoctorAccount = new AccDoctorAccount();
 		try {
 			accDoctorAccount = doctorManageService.findByPrimaryKey(id);
 			logger.info("医生信息编辑查询成功");
 		} catch (Exception e) {
 			logger.error("医生信息编辑查询失败", e);
 		}
 		result.addObject("opt", "edit");
 		result.addObject("accDoctorAccount", accDoctorAccount);
        return result;
    }
    
    

    /**
     * 用户信息明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("doctor/doctorView");
        AccDoctorAccount accDoctorAccount = new AccDoctorAccount();
		try {
			accDoctorAccount = doctorManageService.findByPrimaryKey(id);
			logger.info("医生信息明细查询成功");
		} catch (Exception e) {
			logger.error("医生信息明细查询失败", e);
		}
		result.addObject("opt", "view");
		result.addObject("accDoctorAccount", accDoctorAccount);
        return result;
    }

    /**
     * 医生删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	doctorManageService.deleteByPrimaryKey(id);
        	data.put("code", "1");
        	data.put("info", "医生删除成功!");
			logger.info("医生删除成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "医生删除失败!原因未知异常");
			logger.error("医生删除失败!原因未知异常", e);
		}
        result.put("data", data);
        return result;
    }

    /**
     * 医生新增
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(AccDoctorAccount accDoctorAccount) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	doctorManageService.save(accDoctorAccount);
        	data.put("code", "1");
        	data.put("info", "医生新增成功!");
			logger.info("医生新增成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "医生新增失败!原因未知异常");
			logger.error("医生新增失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("accDoctorAccount", accDoctorAccount);
        return result;
    }
    
    /**
     * 医生修改
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelMap update(AccDoctorAccount accDoctorAccount) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	doctorManageService.update(accDoctorAccount);
        	data.put("code", "1");
        	data.put("info", "医生修改成功!");
			logger.info("医生修改成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "医生修改失败!原因未知异常");
			logger.error("医生修改失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("accDoctorAccount", accDoctorAccount);
        return result;
    }
    
    
    
    
    
}
