package com.aik.Interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aik.model.AccUserAccount;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by NERV on 2016/12/16.
 */
public class CommonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        
    	//返回上下文
    	String path = httpServletRequest.getContextPath();
        String scheme = httpServletRequest.getScheme();
        String serverName = httpServletRequest.getServerName();
        int port = httpServletRequest.getServerPort();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        httpServletRequest.setAttribute("basePath", basePath);
        
        //如果是登陆 不拦截
        String url = httpServletRequest.getServletPath();
        if("/".equals(url) || "".equals(url) || "/login".equals(url) || "/logout".equals(url)){
        	return true;
        }
        
        //登陆拦截  没有登陆跳转到登陆界面
        Object obj = httpServletRequest.getSession().getAttribute("user");
        if (obj == null || !(obj instanceof AccUserAccount)) {
        	httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
            return false;
        }
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
