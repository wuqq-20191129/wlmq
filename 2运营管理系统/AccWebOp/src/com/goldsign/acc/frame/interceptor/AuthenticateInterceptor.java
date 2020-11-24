/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.interceptor;

import com.goldsign.acc.frame.constant.ConfigConstant;
import com.goldsign.acc.frame.filter.AuthenticateFilter;
import com.goldsign.login.vo.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sun.misc.BASE64Encoder;

/**
 *
 * @author hejj
 */
public class AuthenticateInterceptor extends HandlerInterceptorAdapter{
    public static String AUTHENTICATE_COOKIE_NAME="AuthenToken";
    public static String NON_AUTHENTICATION_MIME_LIST_KEY ="NON_AUTHENTICATION_MIME_LIST";
    public static String NON_AUTHENTICATION_URL_LIST_KEY ="NON_AUTHENTICATION_URL_LIST";
    public static String IS_IN_CLUSTER_KEY ="IS_IN_CLUSTER";
    public static String PHYLOGIC_UPLOAD_PATH ="PHYLOGIC_UPLOAD_PATH";
    private static String LOGIN_URL ="/index";
    private static String ERROR_URL ="/jsp/showErrorMessage.jsp";
    private boolean isInCluster = false;
    
//    @Autowired
//    private SysOperatorMapper sysOperatorMapper;
    @Override
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception { 
        System.out.println("preHandle 执行"); 
        
        boolean result = true;
        try {
            //验证sesssion
            result = authenSession(request);

            //验证认证
            result = authenticate(request);
            
            //失败跳转
            if(!result){
                System.out.println("请求未通过认证！");
                redirect(request, response, LOGIN_URL);
            }
        } catch (Exception e) {
            try{
                //异常跳转
                String url = this.ERROR_URL+"?"+"Error="+e.getMessage();
                redirect(request, response, url);
                result = false;
            }catch(IOException ie){
                ie.printStackTrace();
            }
        } finally {
            return result;
        }
    }
    
    @Override
    public void postHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler,  
            ModelAndView modelAndView) throws Exception {
//        System.out.println("业务完成后的处理：设置权限、页面控制等相关信息"); 
       // BaseController bc = new BaseController();
       // bc.baseHandler(request, response, modelAndView);
        
    } 
    
     @Override  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex)  
            throws Exception {  
        System.out.println("afterCompletion 执行");  
    }  

    //session id是否一致
    private boolean authenSession(HttpServletRequest request) throws IOException {
//        WOpSysOperator sysOpr = new WOpSysOperator();
        User user = new User();
        user = (User)request.getSession().getAttribute("User");
        if(user!=null){
            //不查数据库
            String tmp = null;
//            sysOpr = sysOperatorMapper.selectByOperatorId(user.getAccount());
//            tmp = sysOpr.getSessionId();
            //查缓存
            tmp = ConfigConstant.SESSION_ID.containsKey(user.getAccount())?ConfigConstant.SESSION_ID.get(user.getAccount()):null;
            if(!user.getSessionID().equals(tmp)){
                request.getSession().invalidate();
                return false;
            }
        }else{
            return false;
        }
        return true;
    }

    //认证
    private boolean authenticate(HttpServletRequest request) throws Exception {
        
        return !isNeedAuth(request)||authenticateTokenInHeader(request);
    }
    
    
    //增加跳转
    private void redirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open ('"+request.getContextPath()+url+"','_top')");
        out.println("</script>"); 
        out.println("</html>");
    }
    
    public String getRelativeURl(String contextUrl,String reqUrl){
        if(reqUrl.length() ==0)
            return reqUrl;
        int index =reqUrl.indexOf(contextUrl);
        int len = contextUrl.length();
        if(index==-1)
            return reqUrl;
        return reqUrl.substring(index+len);
    }
    

    private boolean isNeedAuth(HttpServletRequest request) throws Exception {

        String relativeUrl = request.getServletPath();
        System.out.println("relativeUrl:"+relativeUrl);
        if (relativeUrl.length() == 0) {
            relativeUrl = this.getRelativeURl(request.getContextPath(), request.getRequestURI());
        }
        String nonAuthUrlList = (String) ConfigConstant.FILTER_PROPERTIES.get(this.NON_AUTHENTICATION_URL_LIST_KEY);
        String nonAuthMimeList = (String) ConfigConstant.FILTER_PROPERTIES.get(this.NON_AUTHENTICATION_MIME_LIST_KEY);
        isInCluster = new Boolean((String) ConfigConstant.FILTER_PROPERTIES.get(this.IS_IN_CLUSTER_KEY)).booleanValue();
        int index = nonAuthUrlList.indexOf(relativeUrl);

        if (index != -1)//不在在不认证的URLl列表中
        {
            return false;
        }
        index = relativeUrl.lastIndexOf(".");
        String sufix = "";
        if (index == -1)//不包含“。“
        {
            return true;
        }
        sufix = relativeUrl.substring(index + 1);
        index = nonAuthMimeList.indexOf(sufix);
        if (index != -1) {
            return false;
        }

        return true;
    }

    public String getAuthenticateTokenInHeader(HttpServletRequest request) {
        String cookies = request.getHeader("Cookie");
        if (cookies == null) {
            return "";
        }
        int index = cookies.indexOf(this.AUTHENTICATE_COOKIE_NAME);
        int index1 = cookies.indexOf("=", index);
        int index2 = cookies.indexOf(";", index);
        if (index2 == -1) {
            index2 = cookies.length();
        }
        if (index == -1) {
            return "";
        }
        return cookies.substring(index1 + 1, index2);
    }

    public boolean authenticateTokenInHeader(HttpServletRequest request) throws Exception {
        String cookies = this.getAuthenticateTokenInHeader(request);
        User user = null;
        if (cookies.length() == 0) {
            return false;
        }

        String authToken = this.generateAuthenticateToken(request);
//        System.out.println("authToken:"+authToken);

        if (cookies.equals(authToken)) {
            return true;
        } else {
            if (this.isInCluster) {//服务器群集时，仅判断是否有会话的用户属性

                try {
                    user = (User) request.getSession().getAttribute("User");
                } catch (IllegalStateException e) {
                    return false;

                }
//                if (user != null) {
//                    System.out.println("用户" + user.getAccount() + "从不工作群集服务器自动转至本服务器,并设置用户登陆信息至本服务器，下一次用户登陆重新加载用户缓存");
//                    new UserBo().setUserLoginInfo(user, request);
//                    //下一次用户登陆重新加载用户缓存
//
//                    UserDao.initFlag = false;
//
//                    return true;
//                }
            }
        }
        return false;
    }

    public String generateAuthenticateToken(HttpServletRequest request) throws Exception {
        String sessionID = request.getSession().getId();
//        System.out.println("sessionID:"+sessionID);
        BASE64Encoder encoder = new BASE64Encoder();
        String authValue = "";
        try {
            authValue = encoder.encode(sessionID.getBytes("GBK"));
            authValue = URLEncoder.encode(authValue, "ISO-8859-1");
        } catch (Exception e) {
            throw e;

        }
        return authValue;
    }

    public void setAuthenticateCookie(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authValue = this.generateAuthenticateToken(request);
//        System.out.println("authValue:"+authValue);
        Cookie c = new Cookie(AuthenticateFilter.AUTHENTICATE_COOKIE_NAME, authValue);
        c.setMaxAge(-1);
        c.setPath("/");
        String headCookie = AuthenticateFilter.AUTHENTICATE_COOKIE_NAME + "=" + authValue + ";" + "path=/";
        response.setHeader("Set-Cookie", headCookie);

    }

    public void inValidateAuthenticateCookie(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authValue = this.generateAuthenticateToken(request);
        Cookie c = new Cookie(AuthenticateFilter.AUTHENTICATE_COOKIE_NAME, authValue);
        c.setMaxAge(0);
        c.setPath("/");
        String headCookie = AuthenticateFilter.AUTHENTICATE_COOKIE_NAME + "=" + authValue + ";" + "path=/";
        response.setHeader("Set-Cookie", headCookie);

    }

}
