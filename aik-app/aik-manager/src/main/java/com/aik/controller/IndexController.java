package com.aik.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aik.model.AccUserAccount;
import com.aik.service.UserManageService;



/**
 * @author daixiangning
 * @message 用户管理
 */
@RestController
public class IndexController {
	
	private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserManageService userManageService;
    
    /**
     * 癌康之家后台
     * @return
     */
    @RequestMapping(value={"", "/"})
    public ModelAndView manage() {
    	 ModelAndView result = new ModelAndView("login");
 		 logger.info("欢迎登陆癌康之家后台");
         return result;
    }
    
    /**
     * 癌康之家后台
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("index");
 		 logger.info("登陆癌康之家后台");
         return result;
    }
    
    
    /**
     * 用户信息新增
     * @return
     */
    @RequestMapping(value = "/login")
    public ModelAndView login() {
    	 ModelAndView result = new ModelAndView("index");
 		 logger.info("登陆癌康之家后台");
         return result;
    }
    
    
    /**
     * 癌康之家后台
     * @return
     */
    @RequestMapping("logout")
    public ModelAndView logout() {
    	 ModelAndView result = new ModelAndView("login");
 		 logger.info("癌康之家后台退出成功");
         return result;
    }
    
    
    @RequestMapping({ "/extra_lock" })
	public ModelAndView lockScreen(HttpServletRequest request,
			HttpServletResponse response) {
    	ModelAndView result = new ModelAndView("extra_lock");
    	result.addObject("op_title", "该用户在其他地点登录，您被迫下线！");
		return result;
	}
    
}
