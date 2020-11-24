/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.frame.constant.WebConstant;
import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
@Controller
public class VersionController {

    @RequestMapping("/index")
    public ModelAndView userManage(HttpServletRequest request, HttpServletResponse response) {
        //设置session,解决跨域访问丢失session问题
        Cookie c = new Cookie("JSESSIONID", request.getSession().getId());
        int maxAge = 720 * 30 * 24 * 3600;
        c.setPath("/");
        c.setMaxAge(maxAge);
        response.addCookie(c);
        
        ModelAndView mav = new ModelAndView("/login.html");
        return mav;
    }

    @RequestMapping("/SysVersion")
    @ResponseBody
    public String getSysVersion(){
        return WebConstant.SYS_VERSION;
    }
}
