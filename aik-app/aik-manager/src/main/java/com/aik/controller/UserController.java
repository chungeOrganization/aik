package com.aik.controller;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.core.util.TimeUtil;

import com.aik.model.AccUserAccount;
import com.aik.service.UserManageService;
import com.aik.util.AikFileUtils;
import com.aik.util.PageUtils;
import com.aik.vo.AccUserAccountVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author daixiangning
 * @message 用户管理
 */
@RestController
@RequestMapping("/users")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	 @Value("${file.upload-root-uri}")
	 private String uploadRootUri;

    @Autowired
    private UserManageService userManageService;
    
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat stampFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		stampFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true,19));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(stampFormat, true, 10)); 
	}
    
    
    /**
     * 用户信息管理
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
    	 ModelAndView result = new ModelAndView("user/userManage");
         
         return result;
    }
    
    
    
    /**
     * 用户信息列表
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/goto/{num}")
    public ModelAndView queryPage(HttpServletRequest request, HttpServletResponse response,AccUserAccountVo accUserAccountVo,@PathVariable Integer num) {
    	ModelAndView mv = new ModelAndView("user/userList");
    	Page<AccUserAccount> accUserAccounts = new Page<AccUserAccount>();
		try {
			Integer size = null;
			Integer page = num;
	    	PageUtils pageRequest = new PageUtils(page, size);
			accUserAccounts = userManageService.findPage(accUserAccountVo, pageRequest);
			logger.info("用户信息列表获取成功");
		} catch (Exception e) {
			logger.error("用户信息列表获取失败", e);
		}
    	PageInfo<AccUserAccount> pageInfo = new PageInfo<AccUserAccount>(accUserAccounts);
    	mv.addObject("result",pageInfo.getList());
		mv.addObject("pageNo", pageInfo.getNextPage());
		mv.addObject("pageSize", pageInfo.getPageSize());
		mv.addObject("total", pageInfo.getTotal());
		mv.addObject("pageInfo", pageInfo);
		return mv;
    }

    /**
     * 用户信息新增
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView add() {
    	 ModelAndView result = new ModelAndView("user/userAdd");
         AccUserAccount accUserAccount = new AccUserAccount();
 		 result.addObject("accUserAccount", accUserAccount);
 		 logger.info("用户信息新增查询成功");
         return result;
    }
    
    
    
    /**
     * 用户信息编辑
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
    	ModelAndView result = new ModelAndView("user/userView");
        AccUserAccount accUserAccount = new AccUserAccount();
 		try {
 			accUserAccount = userManageService.findByPrimaryKey(id);
 			logger.info("用户信息编辑查询成功");
 		} catch (Exception e) {
 			logger.error("用户信息编辑查询失败", e);
 		}
 		result.addObject("opt", "edit");
 		result.addObject("accUserAccount", accUserAccount);
        return result;
    }
    
    

    /**
     * 用户信息明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("user/userView");
        AccUserAccount accUserAccount = new AccUserAccount();
		try {
			accUserAccount = userManageService.findByPrimaryKey(id);
			logger.info("用户信息明细查询成功");
		} catch (Exception e) {
			logger.error("用户信息明细查询失败", e);
		}
		result.addObject("opt", "view");
		result.addObject("accUserAccount", accUserAccount);
        return result;
    }

    /**
     * 用户删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	userManageService.deleteByPrimaryKey(id);
        	data.put("code", "1");
        	data.put("info", "用户删除成功!");
			logger.info("用户删除成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "用户删除失败!原因未知异常");
			logger.error("用户删除失败!原因未知异常", e);
		}
        result.put("data", data);
        return result;
    }

    /**
     * 用户新增
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(AccUserAccount accUserAccount, @RequestParam MultipartFile file) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	String imageName = Calendar.getInstance().getTimeInMillis()
                    + file.getName();

            String fileUri = "user" + File.separator + imageName;
            String uploadUrl = uploadRootUri + fileUri;
            AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);
            accUserAccount.setHeadImg(imageName);
        	userManageService.save(accUserAccount);
        	data.put("code", "1");
        	data.put("info", "用户新增成功!");
			logger.info("用户新增成功!");
		} catch (Exception e) {
			data.put("code", "0");
			data.put("info", "用户新增失败!原因未知异常");
			logger.error("用户新增失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("accUserAccount", accUserAccount);
        return result;
    }
    
    
    /**
     * 用户修改
     * @param accUserAccount
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelMap update(AccUserAccount accUserAccount, @RequestParam MultipartFile file) {
        ModelMap result = new ModelMap();
        Map data = new HashMap();
        try {
        	
        	 String imageName = Calendar.getInstance().getTimeInMillis()
                     + file.getName();

             String fileUri = "user" + File.separator + imageName;
             String uploadUrl = uploadRootUri + fileUri;
             AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);
             accUserAccount.setHeadImg(imageName);
        	userManageService.update(accUserAccount);
        	data.put("code", "1");
        	data.put("info", "用户修改修改!");
			logger.info("用户修改成功!");
		} catch (Exception e) {
			logger.error(e.getMessage());
			data.put("code", "0");
			data.put("info", "用户修改失败!原因未知异常");
			logger.error("用户修改失败!原因未知异常", e);
		}
        result.put("data", data);
        result.put("accUserAccount", accUserAccount);
        return result;
    }
    
    
    
    
    
}
