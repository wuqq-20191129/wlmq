/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.filter;

import com.goldsign.acc.frame.util.FileUtil;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.misc.BASE64Encoder;

/**
 *
 * @author hejj
 */
public class AuthenticateFilter extends HttpServlet implements Filter {

    private FilterConfig filterConfig;
    String configFile = "";
    private static String NON_AUTHENTICATION_MIME_LIST_KEY = "NON_AUTHENTICATION_MIME_LIST";
    private static String NON_AUTHENTICATION_URL_LIST_KEY = "NON_AUTHENTICATION_URL_LIST";
    private static String IS_IN_CLUSTER_KEY = "IS_IN_CLUSTER";
    private static String LOGIN_URL = "/index.htm";
    private static String ERROR_URL = "/jsp/showErrorMessage.jsp";
    private boolean isInCluster = false;
    public static String AUTHENTICATE_COOKIE_NAME = "AuthenToken";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        configFile = filterConfig.getInitParameter("configFile");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        //  resp.setContentType("text/html;charset=uft8");
        String contextUrl = req.getContextPath();
        String relativeUrl = req.getServletPath();
        String reqURI = req.getRequestURI();
        String reqURL = req.getRequestURL().toString();
        //System.out.println("reqURL="+reqURL);
        if (relativeUrl.length() == 0) {
            relativeUrl = this.getRelativeURl(contextUrl, reqURI);
        }

        try {

            if (!this.isNeedAuth(req) || this.AuthenticateTokenInHeader(req)) {
                //System.out.println("isNeedAuth=false");
                filterChain.doFilter(request, response);
                return;
            } else {
                //System.out.println("isNeedAuth=true");
                resp.sendRedirect(contextUrl + this.LOGIN_URL);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            if (!"/import_phy_logic.do".equals(relativeUrl)) {
                try {
                    resp.sendRedirect(contextUrl + this.ERROR_URL + "?" + "Error=" + e.getMessage());
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }

    private String getRelativeURl(String contextUrl, String reqUrl) {
        if (reqUrl.length() == 0) {
            return reqUrl;
        }
        int index = reqUrl.indexOf(contextUrl);
        int len = contextUrl.length();
        if (index == -1) {
            return reqUrl;
        }
        return reqUrl.substring(index + len);

    }

    private boolean isNeedAuth(HttpServletRequest req) throws Exception {
        //  return false;

        String relativeUrl = req.getServletPath();
        FileUtil util = new FileUtil();
        if (relativeUrl.length() == 0) {
            relativeUrl = this.getRelativeURl(req.getContextPath(), req.getRequestURI());
        }
        // HashMap filterProp = this.getFilterProperties(configFile);
        HashMap filterProp = util.getConfigPropertiesByAppPath(req, configFile);
        String nonAuthUrlList = (String) filterProp.get(this.NON_AUTHENTICATION_URL_LIST_KEY);
        String nonAuthMimeList = (String) filterProp.get(this.NON_AUTHENTICATION_MIME_LIST_KEY);
        //  System.out.println("nonAuthUrlList="+nonAuthUrlList);
        //  System.out.println("nonAuthMimeList="+nonAuthMimeList);
        //  System.out.println("relativeUrl="+relativeUrl);

        isInCluster = new Boolean((String) filterProp.get(this.IS_IN_CLUSTER_KEY)).booleanValue();
        int index = nonAuthUrlList.indexOf(relativeUrl);

        if (index != -1)//�
        {
            return false;
        }
        index = relativeUrl.lastIndexOf(".");
        String sufix = "";
        if (index == -1)//������������
        {
            return true;
        }
        sufix = relativeUrl.substring(index + 1);
        index = nonAuthMimeList.indexOf(sufix);
        if (index != -1) {
            return false;
        }
        //System.out.println("isNeedAuth return true");

        return true;

    }
     public boolean AuthenticateTokenInHeader(HttpServletRequest request) throws Exception {
        String cookies = this.getAuthenticateTokenInHeader(request);
      //  User user = null;
        //System.out.println("cookies="+cookies);
        if (cookies.length() == 0) {
            return false;
        }

        String authToken = this.generateAuthenticateToken(request);
        //System.out.println("authToken="+authToken);

        if (cookies.equals(authToken)) {
            return true;
        } 
        return false;
        
     }
     public String generateAuthenticateToken(HttpServletRequest request) throws Exception {
        String sessionID = request.getSession().getId();

        BASE64Encoder encoder = new BASE64Encoder();
        String authValue = "";
        try {
            authValue = encoder.encode(sessionID.getBytes("GBK"));
            authValue = URLEncoder.encode(authValue, "ISO-8859-1");
            //    //System.out.println("authValue="+authValue);
        } catch (Exception e) {
            //    e.printStackTrace();
            throw e;

        }
        return authValue;
    }
      public String getAuthenticateTokenInHeader(HttpServletRequest request) {
        String cookies = request.getHeader("Cookie");
        //System.out.println("getAuthenticateTokenInHeader cookies="+cookies );
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
        

}
