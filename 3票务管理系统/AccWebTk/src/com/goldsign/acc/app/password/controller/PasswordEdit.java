/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.password.controller;

import com.goldsign.acc.app.password.entity.SysOperator;

import com.goldsign.login.util.Encryption;
import com.goldsign.login.vo.User;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.app.password.mapper.SysOperatorMapper;
import com.goldsign.acc.frame.controller.BaseController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 刘粤湘
 * @date 2017-5-25 16:34:45
 * @version V1.0
 * @desc 密码修改
 */
@Controller
public class PasswordEdit extends BaseController {

    private static Logger logger = Logger.getLogger(PasswordEdit.class.getName());

    @Autowired
    SysOperatorMapper sysOprMapper;

    @RequestMapping("/passwordEdit")
    public ModelAndView passwordEdit(HttpServletRequest request, HttpServletResponse response) {
        try {
            String type = (String) request.getParameter("type");
            String action = (String) request.getParameter("action");

            ModelAndView mv = new ModelAndView("/jsp/password/passwordEdit.jsp");
            this.baseHandler(request, response, mv);
//        mv.addObject("versions", versions);
//        mv.addObject("action", action);

            return mv;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @RequestMapping("/StartChangePassword")
    public ModelAndView startChangePassword(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/password/passwordEdit.jsp");
        try {
            //取得页面传过来密码 
            String oldPassword = request.getParameter("oldPassword");
            String password = request.getParameter("password");
            String againPassword = request.getParameter("againPassword");

            if (password == null || againPassword == null) {
                request.setAttribute("Error", "新密码没有输入");
                return mv;
            }
            if (password.trim().equals("") || againPassword.trim().equals("")) {
                request.setAttribute("Error", "新密码没有输入");
                return mv;
            }
            if (password.length() < 6 || againPassword.length() < 6 || oldPassword.length() < 6) {
                request.setAttribute("Error", "旧或新密码长度小于6");
                return mv;
            }
            if (!password.equals(againPassword)) {
                request.setAttribute("Error", "两次输入的新密码不一致！");
                return mv;
            }
            Pattern pat = Pattern.compile("^(([A-Z]*|[a-z]*|\\d*|[-_\\~!@#\\$%\\^&\\*\\.\\(\\)\\[\\]\\{\\}<>\\?\\\\\\/\\'\\\"]*)|.{0,5})$|\\s");
            Matcher mat = pat.matcher(password);
            if (mat.matches()) {
                request.setAttribute("Error", "密码应包含字母、数字和特殊符号的一种以上，不允许出现空格，至少6位的长度！");
                return mv;
            }
            Pattern pat1 = Pattern.compile("^[a-zA-Z0-9_]+$");
            Matcher mat1 = pat1.matcher(password);
            if (!mat1.matches()) {
                request.setAttribute("Error", "密码只能包含英文、数字、下划线");
                return mv;
            }

            //取操作员ID
            User user = (User) request.getSession().getAttribute("User");
            String operatorID = user.getAccount();

            //对密码进行加密
            String encOldPassword = Encryption.biEncrypt(oldPassword);
            String encPassword = Encryption.biEncrypt(password);
            String encAgainPassword = Encryption.biEncrypt(againPassword);
            Map<String, Object> map = new HashMap();
            map.put("sysOperatorId", operatorID);
            map.put("sysPasswordHash", encOldPassword);
            SysOperator sysOpr = new SysOperator();
            int i = 0;
            i = sysOprMapper.count(map);
            logger.info("操作员" + operatorID + "匹配旧密码值" + i);
            //旧密码没有匹配上
            if (i == 0) {
                request.setAttribute("Error", "旧密码不正确");
                return mv;
            }

            sysOpr.setSysOperatorId(operatorID);
            sysOpr.setSysPasswordHash(encPassword);
            SimpleDateFormat sy1 = new SimpleDateFormat("yyyyMMdd");
            String passwordEditDate = sy1.format(new Date());
            sysOpr.setPasswordEditDate(passwordEditDate);
            i = 0;
            i = sysOprMapper.updateByPrimaryKeySelective(sysOpr);
            if (i == 1) {
                request.setAttribute("Error", "修改成功");
                return mv;
            } else {
                request.setAttribute("Error", "修改失败");

            }
            return mv;

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
