/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.test.controller;

import com.goldsign.acc.test.entity.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.test.mapper.UserMapper;
import java.util.Vector;

/**
 *
 * @author hejj
 */
@Controller
@RequestMapping("/parainfo/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/manage")
    public ModelAndView userManage(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = userMapper.selectUsers();
        this.printUsers(users);
        String company = "mh";
        ModelAndView mav = new ModelAndView("/test/listUser.jsp");
        mav.addObject("users", users);
        mav.addObject("company", company);
        return mav;
    }
    
    @RequestMapping("/list/selectUserByID")
    public ModelAndView listById(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("q_sysOperatorId");//
        String userName = request.getParameter("q_sysOperatorName");
        User user = userMapper.selectUserByID(userId);
        this.printUser(user);
        String company = "mh";
        ModelAndView mav = new ModelAndView("test/listUser");
        Vector<User> users = new Vector();
        users.add(user);
        mav.addObject("users", users);
        mav.addObject("company", company);
        return mav;
    }

    public void printUser(User user) {
        System.out.println("id:" + user.getSys_operator_id()+ ",name:" + user.getSys_operator_name()+ ",employeeid:" + user.getSys_employee_id()+ ",status:" + user.getSys_status());
    }

    public void printUsers(List<User> users) {
        for (User user : users) {
            this.printUser(user);
        }
    }

}
