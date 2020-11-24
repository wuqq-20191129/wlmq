/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.controller;

import com.goldsign.acc.app.password.entity.SysOperator;
import com.goldsign.acc.app.password.mapper.SysOperatorMapper;
import com.goldsign.acc.frame.constant.ConfigConstant;
import com.goldsign.acc.frame.ds.GoldsignDataSource;
import com.goldsign.acc.frame.interceptor.AuthenticateInterceptor;
import com.goldsign.acc.frame.util.DateUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.LoginVo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.login.bo.ILoginBo;
import com.goldsign.login.bo.LoginBo;
import com.goldsign.login.result.AuthResult;
import com.goldsign.login.result.EditResult;
import com.goldsign.login.vo.AuthInParam;
import com.goldsign.login.vo.User;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author hejj
 */
@Controller
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    SysOperatorMapper sysOprMapper;
//    @Autowired
//    BasicDataSource basicDataSource;
    @Autowired
    GoldsignDataSource goldsignDataSource;

//    @RequestMapping("/login")
    public ModelAndView loginManage(HttpServletRequest request, HttpServletResponse response) {
        LoginVo loginVo = this.getLoginVo(request, response);
        ILoginBo loginBo = new LoginBo();
        DbHelper dbHelper = null;
        ModelAndView mav = null;
        try {
            //dbHelper = new DbHelper(DBUtil.getDataSource(request, response));
            dbHelper = new DbHelper(goldsignDataSource);
            AuthResult auditResult = loginBo.authorization(loginVo.getAccount(), loginVo.getPassword(), loginVo.getSysFlag(), dbHelper);
            if (auditResult.getReturnCode().equals(ILoginBo.SUCCESS_AUTH)) {
                mav = this.loginSucess(request, response, auditResult);

            }

        } catch (Exception e) {

        } finally {
            //20180817 moqf 关闭连接
            PubUtil.finalProcess(dbHelper, logger);
        }

        return mav;
    }
    
    @ResponseBody
    @RequestMapping(value = "/logon")
    public String logonAjax(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo) {
        DbHelper dbHelper = null;
        ILoginBo loginBo = new LoginBo();
        String result = null;
        try {
           // dbHelper = new DbHelper(DBUtil.getDataSource(request, response));
           
           
            dbHelper = new DbHelper(goldsignDataSource);
//            for (int i =0;i<10; i++) {
//            //tmppppppppp 20180817 moqf 
//            dbHelper = new DbHelper(basicDataSource);
//                
//            }
            
            AuthInParam authInParam = new AuthInParam();
            String ip = request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")?"127.0.0.1":request.getRemoteAddr();
            authInParam.setIp(ip);
            AuthResult auditResult = loginBo.authorization(loginVo.getAccount(), loginVo.getPassword(), loginVo.getSysFlag(), authInParam, dbHelper);
            
            
            //修改密码
            if(loginVo.getLoginFlag().equals("edit") && !loginVo.getNewpassword().equals("")){
                //密码过期
                if(auditResult.getReturnCode().equals(ILoginBo.ERROR_PASSWORD_NOT_IN_VALID_DATE)){
                   // dbHelper = new DbHelper(DBUtil.getDataSource(request, response));
                   
            
                   dbHelper = new DbHelper(goldsignDataSource);
                    EditResult editResult = loginBo.editPassword(loginVo.getAccount(), loginVo.getPassword(), loginVo.getNewpassword(), dbHelper);
                    
                    if(editResult.getResult()){
                       // dbHelper = new DbHelper(DBUtil.getDataSource(request, response));
                       
           
                       dbHelper = new DbHelper(goldsignDataSource);
                       
                        auditResult = loginBo.authorization(loginVo.getAccount(), loginVo.getNewpassword(), loginVo.getSysFlag(), authInParam, dbHelper);
                        
                    }else{
                        auditResult.setReturnCode("307");//密码修改失败
                    }
                }
            }
            if (auditResult.getReturnCode().equals(ILoginBo.SUCCESS_AUTH)) {
                this.loginSucess(request, response, auditResult);
            }
            result = auditResult.getReturnCode();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //20180817 moqf 关闭连接 loginCenter包也有关闭
            PubUtil.finalProcess(dbHelper, logger);
        }
        return result;
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        //清除缓存session
        if(user!=null && ConfigConstant.SESSION_ID.containsKey(user.getAccount())){
            ConfigConstant.SESSION_ID.remove(user.getAccount());
            ILoginBo loginBo = new LoginBo();
//            DbHelper dbHelper;
            DbHelper dbHelper = null;
            try {
                dbHelper = new DbHelper(goldsignDataSource);
                loginBo.logLogoutInfo((String) session.getAttribute("flowID"), user.getAccount(), dbHelper);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                //20180817 moqf 关闭连接，loginBo.logLogoutInfo未关闭
                PubUtil.finalProcess(dbHelper, logger);
            }
        }
        return "/index";
    }
    
    @RequestMapping("/topFrame")
    public ModelAndView frameManage(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("/jsp/frame/frame.htm");

        return mav;
    }

    @RequestMapping("/topMenu")
    public ModelAndView topManage(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("/jsp/frame/top.jsp");

        return mav;
    }

    @RequestMapping("/leftMenu")
    public ModelAndView leftManage(HttpServletRequest request, HttpServletResponse response) {
       // String topMenuId =request.getParameter("TopMenuId");
        
        ModelAndView mav = new ModelAndView("/jsp/frame/left.jsp");
       // mav.addObject("TopMenuId", topMenuId);

        return mav;
    }

    @RequestMapping("/mainContent")
    public ModelAndView contentManage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("mainContent");
        ModelAndView mav = new ModelAndView("/jsp/frame/contents.htm");

        return mav;
    }

    private LoginVo getLoginVo(HttpServletRequest request, HttpServletResponse response) {
        LoginVo vo = new LoginVo();
        vo.setAccount(FormUtil.getParameter(request, "Account"));
        vo.setPassword(FormUtil.getParameter(request, "Password"));
        vo.setSysFlag(FormUtil.getParameter(request, "sys_flag"));
        return vo;
    }

    private ModelAndView loginSucess(HttpServletRequest request, HttpServletResponse response, AuthResult auditResult) throws Exception {
        HttpSession session = request.getSession();
        User user = auditResult.getUser();
        user.setSessionID(session.getId());
        session.setAttribute("LeftDays", Long.toString(user.getLeftDays()));
        session.setAttribute("User", user);//设置用户及登陆流水号到会话中
        session.setAttribute("flowID", auditResult.getFlowId());
        session.setAttribute("ModulePrilivedges", auditResult.getModules());//设置用户权限到会话中
        session.setAttribute("LoginMsg", "");
        this.setOperatorIDToCookie(request, response); //返回包含用户ID的COOKIE
        this.setUserPassWordDays(request, response, user, "ValidDays");//设置用户密码有效天数

        //更新缓存session
        ConfigConstant.SESSION_ID.put(user.getAccount(), user.getSessionID());
        //更新登录账户session id
        SysOperator sysOpr = new SysOperator();
        transSysOperator(user,sysOpr);
        sysOprMapper.updateByPrimaryKeySelective(sysOpr);
        
        //限制单一用户单一会话，放到拦截器中

        //设置系统属性放到内存，定时刷新内存
        //返回认证TOKEN
        new AuthenticateInterceptor().setAuthenticateCookie(request, response);
        //获得登陆成功后，转发URL
        ModelAndView mav = new ModelAndView("/jsp/frame/frame.htm");

        return mav;
    }

    private void setOperatorIDToCookie(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("User");
        String OperatorID = user.getAccount();
        Cookie c = new Cookie("OperatorID", OperatorID);
        int maxAge = 720 * 30 * 24 * 3600;
        c.setPath("/");
        c.setMaxAge(maxAge);
        resp.addCookie(c);

    }
    private void setUserPassWordDays(HttpServletRequest req, HttpServletResponse resp,User user,String paraName) {
        String days = this.getUserPassWordDays(user);
        req.getSession().setAttribute(paraName, days);
        

    }
    //  新增 提示用户修改密码，有效期剩7天开始提醒
    private String getUserPassWordDays(User user) {
        String strDays;

        Timestamp time = new Timestamp(System.currentTimeMillis());
        String date = time.toString().substring(0, 10);
        String dbDate = new DateUtil().convertDateToDBFormat(date);


        String editDate = user.getEditPassWordDate().trim();
        if (editDate.length() < 8) {
            return "1";
        }

        long iDays = user.getEditPassWordDays();

        String year = editDate.substring(0, 4);
        String month = editDate.substring(4, 6);
        String day = editDate.substring(6, 8);

        String curYear = date.substring(0, 4);
        String curMonth = date.substring(5, 7);
        String curDay = date.substring(8, 10);

        int iYear = new Integer(year).intValue();
        int iMonth = new Integer(month).intValue();
        int iDay = new Integer(day).intValue();

        int iCurYear = new Integer(curYear).intValue();
        int iCurMonth = new Integer(curMonth).intValue();
        int iCurDay = new Integer(curDay).intValue();


        GregorianCalendar gExpired = new GregorianCalendar(iYear, iMonth, iDay);
        GregorianCalendar gCur = new GregorianCalendar(iCurYear, iCurMonth, iCurDay);

        long days = -1;
        days = (gCur.getTimeInMillis() - gExpired.getTimeInMillis()) / (24 * 60 * 60 * 10 * 10 * 10);
        long iRemainDays = iDays - days;
        strDays = String.valueOf(iRemainDays);
        return strDays;
    }

    private void transSysOperator(User user, SysOperator sysOpr) {
        sysOpr.setSysOperatorId(user.getAccount());
        sysOpr.setSessionId(user.getSessionID());
    }

}
