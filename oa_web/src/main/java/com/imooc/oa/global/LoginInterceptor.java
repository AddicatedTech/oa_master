package com.imooc.oa.global;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //要在void运行之前进行处理
        //登录相关的功能是怎么样都是应该不受到拦截的，进行放行
        //登录的控制器，处理登录的方法类，还有login.jsp\]
        String url= request.getRequestURI();
        //查找里面有没有login的关键字来判明是否为登录操作，有则放行，但是url中有大写跟小写分别，统一按照小写进行判断

        if (url.toLowerCase().indexOf("login")>0){
            return true;
        }
        //不包含login的有可能是没进行登录，要判断登录状态
        HttpSession session = request.getSession();
        if (session.getAttribute("employee")!=null){
            //session对象不为空则说明已经登录，可放行
            return true;
        }
        response.sendRedirect("/to_login"); //没有的情况下重新导航到登录界面去登录
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
