/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.web;

import com.goldsign.frame.bo.ModuleDistrBo;
import com.goldsign.frame.bo.PriviledgeBo;
import com.goldsign.frame.bo.UserBo;
import com.goldsign.frame.constant.FrameCodeConstant;
import com.goldsign.frame.filter.AuthenticateFilter;
import com.goldsign.frame.util.FrameDBUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.frame.vo.AuditResult;
import com.goldsign.frame.vo.EditResult;
import com.goldsign.frame.vo.PriviledgeOperatorVo;
import com.goldsign.frame.vo.User;
import com.goldsign.rule.env.RuleConstant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


/**
 *
 * @author hejj
 */
public class LoginManager extends Action {

    private static Logger logger = Logger.getLogger(LoginManager.class.getName());
    private static HashMap CONFIGS = new HashMap();
    private static int SESSION_TIMEOUT = 36000;
    private static boolean IS_STARTED_CONNNECTION_LISTENER = false;

    public ActionForward execute(ActionMapping mapping, ActionForm baseForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DynaActionForm form = (DynaActionForm) baseForm;
        HttpSession session = request.getSession();
        FrameDBUtil dbUtil = new FrameDBUtil();
        String flowID = "";
        FrameUtil util = new FrameUtil();
        ActionErrors errors = new ActionErrors();

        String account = (String) form.get("Account");
        String password = (String) form.get("Password");
        String newpassword = (String) form.get("NewPassword");
        String repassword = (String) form.get("RePassword");
        String loginFlag = (String) form.get("Flag");

        UserBo userBO = new UserBo();
        ModuleDistrBo mdb = new ModuleDistrBo();
        ActionForward af = null;


        AuditResult auditResult = userBO.login(account, password, request, response);
        if (auditResult.getResult()) {
            //login success
            User user = userBO.getUserByAccount(account);
            //获得用户的模块权限

            //登陆成功后，清理失败次数
            this.restoreOperator(user);


            //      handleCluster(request,response,user);
            //设置用户登陆信息到库表

            flowID = dbUtil.logLoginInfo(request, user.getAccount());

            //设置用户及登陆流水号到会话中
            session.setAttribute("User", user);
            session.setAttribute("flowID", flowID);
            //      session.setMaxInactiveInterval(this.SESSION_TIMEOUT);
            //设置用户权限到会话中
            this.setPriviledgeToSession(account, request);
            //设置系统属性

            this.setSystemProperties(request);
            //设置决策支持图片路径
            //this.setDecisionImagePath();
            //返回认证TOKEN
            new AuthenticateFilter().setAuthenticateCookie(request, response);
            //   new DBUtil().logoutHandle(request);
            //showSystemPropertes();
            //返回包含用户ID的COOKIE
            this.setOperatorIDToCookie(request, response);
            //获得登陆成功后，转发URL
            af = this.getLoginForward(request, mapping);



            //  handleScreenViewOperation();
            request.getSession().setAttribute("LoginMsg", "");
            return af;
        } else {
            String strMsg = auditResult.getMsg();
            request.getSession().setAttribute("LoginMsg", strMsg);

            //yjh 2007-07-18 新增
            if (strMsg.equals("密码已过期，请修改")) {
                if (loginFlag.equals("1")) {
                    if (newpassword.equals("") && repassword.equals("")) {
                        request.getSession().setAttribute("LoginMsg", "新密码不能为空！");
                        return (mapping.findForward("editpwd"));
                    }

                    if (password.equals(newpassword)) {
                        request.getSession().setAttribute("LoginMsg", "新旧密码不能相同！");
                        return (mapping.findForward("editpwd"));
                    }

                    if (newpassword.equals(repassword)) {
                        //调用修改密码
                        //修改成功            				
                        PriviledgeBo pmb = new PriviledgeBo();
                        PriviledgeOperatorVo po = new PriviledgeOperatorVo();
                        po.setOldPassword(password);
                        po.setPassword(newpassword);
                        po.setOperatorID(account);
                        //System.out.println("account:"+account);
                        EditResult er = new EditResult();
                        er = pmb.EditUserPwd(request, po);

                        if (!er.getResult()) {
                            request.getSession().setAttribute("LoginMsg", er.getMsg());
                            return (mapping.findForward("editpwd"));
                        }

                        //修改密码，转向登陆成功

                        //login success
                        User user = userBO.getUserByAccount(account);
                        //获得用户的模块权限

                        //登陆成功后，清理失败次数
                        this.restoreOperator(user);
                        //设置用户登陆信息到库表

                        flowID = dbUtil.logLoginInfo(request, user.getAccount());
                        //设置用户及登陆流水号到会话中
                        session.setAttribute("User", user);
                        session.setAttribute("flowID", flowID);
                        //设置用户权限到会话中
                        this.setPriviledgeToSession(account, request);
                        //设置系统属性

                        this.setSystemProperties(request);

                        //返回认证TOKEN
                        new AuthenticateFilter().setAuthenticateCookie(request, response);
                        // new DBUtil().logoutHandle(request);
                        //返回包含用户ID的COOKIE
                        this.setOperatorIDToCookie(request, response);
                        //获得登陆成功后，转发URL
                        af = this.getLoginForward(request, mapping);
                        request.getSession().setAttribute("LoginMsg", "");
                        return af;
                    } else {
                        request.getSession().setAttribute("LoginMsg", "新旧密码不符！");
                        return (mapping.findForward("editpwd"));
                    }
                } else {

                    User user = userBO.getUserByAccount(account);
                    //启动消息接收线程,发送用户登陆消息给群集服务器

                   //modify by hejj 屏蔽集群
                    //new Util().handleClusterForUserLogin(request, response, user);
                    if (!userBO.isInUsed(user, request, response)) {
                        // auditResult.setResult(false);
                        //auditResult.setResult(true);
                        //auditResult.setMsg("帐户："+user.getAccount()+"正在使用");
                        //return auditResult;
                    }


                    //获得用户的模块权限

                    //密码认证登陆成功后，清理失败次数            			            			
                    this.restoreOperator(user);
                    //handleCluster(request,response,user);
                    //设置用户登陆信息到库表

                    flowID = dbUtil.logLoginInfo(request, user.getAccount());
                    //      UserSessionListener userSessionListener = new UserSessionListener(flowID,request);
                    //       session.setAttribute("UserSessionListener",userSessionListener);
                    //设置用户及登陆流水号到会话中
                    session.setAttribute("User", user);
                    session.setAttribute("flowID", flowID);
                    //      session.setMaxInactiveInterval(this.SESSION_TIMEOUT);
                    //设置用户权限到会话中
                    this.setPriviledgeToSession(account, request);
                    //设置系统属性

                    this.setSystemProperties(request);
                    //设置决策支持图片路径
                    //this.setDecisionImagePath();
                    //返回认证TOKEN
                    new AuthenticateFilter().setAuthenticateCookie(request, response);
                    //   new DBUtil().logoutHandle(request);
                    //showSystemPropertes();
                    //返回包含用户ID的COOKIE
                    this.setOperatorIDToCookie(request, response);

                    //System.out.println("密码已过期，请修改aaaaaaa");
                    request.getSession().setAttribute("LoginMsg", "密码已过期,请录入旧密码进行修改");
                    af = mapping.findForward("editpwd");
                    //System.out.println(mapping.findForward("editpwd"));            			            			
                    return af;
                }

            } else {
                auditResult.setMsg("");
                return (mapping.findForward("loginFailure"));
            }

        }
    }

    public void restoreOperator(User user) throws Exception {
        PriviledgeBo bo = new PriviledgeBo();
        //登陆成功后，清理登陆失败的次数

        bo.restoreOperator(user.getAccount());

        user.setFailedNum(0);
    }

    public ActionForward getLoginForward(HttpServletRequest request, ActionMapping mapping) {
        ActionForward af = null;

        af = mapping.findForward("success");

        return af;
    }



    public void setOperatorIDToCookie(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("User");
        String OperatorID = user.getAccount();
        Cookie c = new Cookie("OperatorID", OperatorID);
        int maxAge = 720 * 30 * 24 * 3600;
        c.setPath("/");
        c.setMaxAge(maxAge);
        resp.addCookie(c);

    }

    public void setPriviledgeToSession(String account, HttpServletRequest req) throws Exception {
        ModuleDistrBo mdb = new ModuleDistrBo();
        Vector modulePrilivedges = mdb.getThirdModulesByOperator(account);
        HttpSession session = req.getSession();
        session.setAttribute("ModulePrilivedges", modulePrilivedges);

    }

    public void setSystemProperties(HttpServletRequest req) throws Exception {
//        if (!this.CONFIGS.isEmpty()) {
//            return;
//        }
        String configFile = "webAppConfig.properties";
        HashMap configProps = null;
        //      configProps =new Util().getConfigProperties(req,configFile);
        //configProps =new Util().getConfigPropertiesByAppPath(req,configFile);
        //2011-09-08 hejj 改由数据库中读取
        configProps = FrameDBUtil.getConfigPropertiesFromDb(req, FrameCodeConstant.CONFIG_TYPE_SYS);
        this.CONFIGS.clear();
        this.CONFIGS.putAll(configProps);
        Set keys = configProps.keySet();
        Iterator it = keys.iterator();
        String key = null;
        String value = null;
        while (it.hasNext()) {
            key = (String) it.next();
            value = (String) configProps.get(key);
            // System.out.println("key="+key+" value="+value);
            if (key.equals("query.maxrow.number")) {
                FrameCodeConstant.MAX_DB_ROW_NUMBER = new Integer(value)
                        .intValue();
                continue;
            }
            if (key.equals("blacklist.max")) {
                FrameCodeConstant.MAX_BLACKLIST_NUMBER = new Integer(value)
                        .intValue();
                continue;
            }
            if (key.equals("blacklist.section.max")) {
                FrameCodeConstant.MAX_BLACKLIST_SECTION_NUMBER = new Integer(
                        value).intValue();
                continue;
            }
            //add by hejj 2011-09-15
            if (key.equals("blacklist.metro.max")) {
                FrameCodeConstant.MAX_METRO_BLACKLIST_NUMBER = new Integer(
                        value).intValue();
                continue;
            }
            if (key.equals("page.max")) {
                FrameCodeConstant.MAX_PAGE_NUMBER = new Integer(value)
                        .intValue();
                continue;

            }
            if (key.equals("out.max")) {
                FrameCodeConstant.MAX_OUT_NUMBER = new Integer(value)
                        .intValue();
                continue;

            }
            if (key.equals("application.flag")) {
                FrameCodeConstant.ApplicationFlag = new Integer(value)
                        .intValue();
                continue;

            }

            System.setProperty(key, value);
            
            FrameDBUtil util = new FrameDBUtil();
            //配置表信息
            util.getPubFlags();
            
            //设置站点，线路信息
            setLineStation(util);
        }


    }

    public void showSystemPropertes() {
        Properties props = System.getProperties();
        Set keys = props.keySet();
        Iterator it = keys.iterator();
        String key = null;
        String value = null;
        while (it.hasNext()) {
            key = (String) it.next();
            value = props.getProperty(key);
            System.out.println("key=" + key + " value=" + value);
        }
    }

    public void editpassword() {
    }

    //设置站点，线路信息
    private void setLineStation(FrameDBUtil util) {
        try {
            
            RuleConstant.LINES = util.getParamTableFlags("op_prm_line", "line_id", "line_name");
            RuleConstant.STATIONS = util.getParamTableFlags("op_prm_station", "line_id", "station_id", "chinese_name");
            
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
}
