/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.filter;
import com.goldsign.frame.bo.UserBo;
import com.goldsign.frame.dao.UserDao;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.frame.vo.User;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import sun.misc.*;
import java.net.*;


/**
 *
 * @author hejj
 */
public class AuthenticateFilter extends HttpServlet implements Filter{
     private FilterConfig filterConfig;
  private static final String CONTENT_TYPE = "text/html; charset=GBK";
  public static String AUTHENTICATE_COOKIE_NAME="AuthenToken";
  String configFile = "";
  private static HashMap FILTER_PROPERTIES = new  HashMap();
  private static String NON_AUTHENTICATION_MIME_LIST_KEY ="NON_AUTHENTICATION_MIME_LIST";
  private static String NON_AUTHENTICATION_URL_LIST_KEY ="NON_AUTHENTICATION_URL_LIST";
  private static String IS_IN_CLUSTER_KEY ="IS_IN_CLUSTER";
  private static String LOGIN_URL ="/index.htm";
  private static String ERROR_URL ="/jsp/showErrorMessage.jsp";
  private boolean isInCluster = false;




  //Handle the passed-in FilterConfig
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
    configFile = filterConfig.getInitParameter("configFile");
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
  //Process the request/response pair
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
   HttpServletResponse resp = (HttpServletResponse)response;
   HttpServletRequest req = (HttpServletRequest)request;
 //  resp.setContentType("text/html;charset=uft8");
   String contextUrl = req.getContextPath();
   String relativeUrl = req.getServletPath();
   String reqURI = req.getRequestURI();
   String reqURL = req.getRequestURL().toString();
   //System.out.println("reqURL="+reqURL);
   if(relativeUrl.length()==0)
     relativeUrl = this.getRelativeURl(contextUrl,reqURI);


    try{

      if(!this.isNeedAuth(req)||this.AuthenticateTokenInHeader(req)){
        //System.out.println("isNeedAuth=false");
        filterChain.doFilter(request, response);
        return;
      }
      else{
         //System.out.println("isNeedAuth=true");
        resp.sendRedirect(contextUrl + this.LOGIN_URL);
      }
     }catch(Exception e){
       //e.printStackTrace();
       try{
         resp.sendRedirect(contextUrl + this.ERROR_URL+"?"+"Error="+e.getMessage());
       }catch(IOException ie){
         ie.printStackTrace();
       }
     }
   }

  //Clean up resources
  public void destroy() {
    this.filterConfig = null;
  }
  private boolean isNeedAuth(HttpServletRequest req) throws Exception{
  //  return false;

   String relativeUrl = req.getServletPath();
   FrameUtil util = new FrameUtil();
   if(relativeUrl.length() ==0)
     relativeUrl = this.getRelativeURl(req.getContextPath(),req.getRequestURI());
  // HashMap filterProp = this.getFilterProperties(configFile);
   HashMap filterProp = util.getConfigPropertiesByAppPath(req,configFile);
   String nonAuthUrlList =(String) filterProp.get(this.NON_AUTHENTICATION_URL_LIST_KEY);
   String nonAuthMimeList =(String) filterProp.get(this.NON_AUTHENTICATION_MIME_LIST_KEY);
 //  System.out.println("nonAuthUrlList="+nonAuthUrlList);
 //  System.out.println("nonAuthMimeList="+nonAuthMimeList);
 //  System.out.println("relativeUrl="+relativeUrl);

   isInCluster = new Boolean((String)filterProp.get(this.IS_IN_CLUSTER_KEY)).booleanValue();
   int index = nonAuthUrlList.indexOf(relativeUrl);


   if(index != -1)//不在在不认证的URLl列表中

     return false;
   index = relativeUrl.lastIndexOf(".");
    String sufix = "";
    if(index == -1)//不包含“。“

      return true;
    sufix = relativeUrl.substring(index+1);
    index = nonAuthMimeList.indexOf(sufix);
    if(index !=-1)
      return false;
    //System.out.println("isNeedAuth return true");

    return true;

 }

public String getAuthenticateTokenInHeader(HttpServletRequest request){
  String cookies = request.getHeader("Cookie");
  //System.out.println("getAuthenticateTokenInHeader cookies="+cookies );
  if(cookies == null)
    return "";
  int index = cookies.indexOf(this.AUTHENTICATE_COOKIE_NAME);
  int index1 =cookies.indexOf("=",index);
  int index2 = cookies.indexOf(";",index);
  if(index2 ==-1)
    index2 = cookies.length();
  if(index==-1)
    return "";
  return cookies.substring(index1+1,index2);
}
 private boolean AuthenticateToken(HttpServletRequest request) throws Exception{
   Cookie[] cs =request.getCookies();
   if(cs == null)
     return false;
   Cookie c = null;
   String authToken = this.generateAuthenticateToken(request);
   //System.out.println("authToken="+authToken);
   for(int i=0;i<cs.length;i++){
     c = cs[i];
     if(!c.getName().trim().equals(this.AUTHENTICATE_COOKIE_NAME))
       continue;
     if(authToken.equals(c.getValue()))
       return true;
   }
   //System.out.println("没有认证COOKIE");
   return false;
 }
 public boolean AuthenticateTokenInHeader(HttpServletRequest request) throws Exception{
   String cookies = this.getAuthenticateTokenInHeader(request);
   User user = null;
   //System.out.println("cookies="+cookies);
   if(cookies.length() ==0)
     return false;

   String authToken = this.generateAuthenticateToken(request);
   //System.out.println("authToken="+authToken);

   if(cookies.equals(authToken))
     return true;
   else{
     if(this.isInCluster) {//服务器群集时，仅判断是否有会话的用户属性

       try{
         user =(User)request.getSession().getAttribute("User");
       }catch(IllegalStateException e){
         return false;

       }
       if(user != null){
         System.out.println("用户"+user.getAccount()+"从不工作群集服务器自动转至本服务器,并设置用户登陆信息至本服务器，下一次用户登陆重新加载用户缓存");
         new UserBo().setUserLoginInfo(user,request);
         //下一次用户登陆重新加载用户缓存

         UserDao.initFlag = false;

         return true;
       }
     }
   }
   //System.out.println("AuthenticateTokenInHeader return false");
   return false;
 }

 public String generateAuthenticateToken(HttpServletRequest request) throws Exception{
   String sessionID = request.getSession().getId();

   BASE64Encoder encoder = new BASE64Encoder();
   String authValue = "";
   try{
     authValue =encoder.encode(sessionID.getBytes("GBK"));
     authValue = URLEncoder.encode(authValue,"ISO-8859-1");
 //    //System.out.println("authValue="+authValue);
   }catch(Exception e){
 //    e.printStackTrace();
    throw e;

   }
   return authValue;
 }


public void setAuthenticateCookie(HttpServletRequest request,HttpServletResponse response) throws Exception{
 String authValue = this.generateAuthenticateToken(request);
 //System.out.print("authValue"+authValue);
 //System.out.print("authValue end");
 Cookie  c = new Cookie(AuthenticateFilter.AUTHENTICATE_COOKIE_NAME,authValue);
 c.setMaxAge(-1);
 c.setPath("/");
 String headCookie = AuthenticateFilter.AUTHENTICATE_COOKIE_NAME+"="+authValue+";"+"path=/";
 //System.out.println("setAuthenticateCookie Cookie= "+headCookie);
 //response.addCookie(c);
 response.setHeader("Set-Cookie",headCookie);

}
  public void inValidateAuthenticateCookie(HttpServletRequest request,HttpServletResponse response) throws Exception{
 String authValue = this.generateAuthenticateToken(request);
 //System.out.print("authValue"+authValue);
 //System.out.print("authValue end");
 Cookie  c = new Cookie(AuthenticateFilter.AUTHENTICATE_COOKIE_NAME,authValue);
 c.setMaxAge(0);
 c.setPath("/");
 String headCookie = AuthenticateFilter.AUTHENTICATE_COOKIE_NAME+"="+authValue+";"+"path=/";
 //System.out.println("setAuthenticateCookie Cookie= "+headCookie);
 //response.addCookie(c);
 response.setHeader("Set-Cookie",headCookie);

}



 //Clean up resources

  private String getAuthenticateCookie(HttpServletRequest request,HttpServletResponse response) {
     Cookie[] cs = request.getCookies();
     if(cs == null)
       return "没有COOKIE";
     String result = "";
     Cookie c = null;
     for(int i=0;i<cs.length;i++){
       c = cs[i];
       result +=c.getName()+"="+c.getValue()+":";
     }
     if(result.length() !=0)
       result = result.substring(0,result.length()-1);
     return result;
  }

    
}
