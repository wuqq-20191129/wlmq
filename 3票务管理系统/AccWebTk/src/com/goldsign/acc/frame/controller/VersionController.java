/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.frame.constant.WebConstant;

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

        ModelAndView mav = new ModelAndView("/login.html");
        return mav;
    }

    @RequestMapping("/SysVersion")
    @ResponseBody
    public String getSysVersion(){
        return WebConstant.SYS_VERSION;
    }
}
