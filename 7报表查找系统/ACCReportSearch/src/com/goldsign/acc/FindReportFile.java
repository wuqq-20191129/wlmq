package com.goldsign.acc;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.goldsign.javacore.util.CharUtil;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class FindReportFile extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

  //Initialize global variables
  public void init() throws ServletException {
  }

  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    String pathName = request.getParameter("pathName");
    String reportNamePrefix = request.getParameter("reportNamePrefix");
    String isBalanceDate = request.getParameter("isBalanceDate");
    if(isBalanceDate == null)
    	isBalanceDate ="0";
    pathName = CharUtil.IsoToGbk(pathName);
 //   pathName = CharUtil.UTF8ToGbk(pathName);
    reportNamePrefix = CharUtil.IsoToGbk(reportNamePrefix);
    System.out.println("pathName="+pathName+" reportNamePrefix="+reportNamePrefix+" isBalanceDate="+isBalanceDate);
    String result = "reportNames=";
    result +=this.findFiles(pathName,reportNamePrefix,isBalanceDate);
    out.println(result);
    out.flush();
  }
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /*
   response.setContentType(CONTENT_TYPE);
   PrintWriter out = response.getWriter();
   String pathName = request.getParameter("pathName");
   String reportNamePrefix = request.getParameter("reportNamePrefix");
   pathName = CharUtil.IsoToGbk(pathName);
   System.out.println("pathName="+pathName+" reportNamePrefix="+reportNamePrefix);
   String result = "reportNames=";
   result +=this.findFiles(pathName,reportNamePrefix);
   out.println(result);
   out.flush();
        */
 }

  private String findFiles(String pathName,String reportNamePrefix,String isBalanceDate){
    File dir = new File(pathName);
    String[] reportNames = null;
    String result = "";
    FilenameFilter rft =null;
     if(!dir.isDirectory()){
        return "";
//       throw new Exception(dir+"不是目录");

     }
     if(isBalanceDate.equals("0"))
    	 rft =new ReportFileFilter(reportNamePrefix);
     else
    	 rft = new ReportFileFilterForBalance(reportNamePrefix);
     reportNames = dir.list(rft);
     if(reportNames ==null || reportNames.length ==0)
       return "";
     for(int i=0;i<reportNames.length;i++)
       result +=reportNames[i]+"#";
     result = result.substring(0,result.length()-1);
     result +=";";
     return result;


  }

  //Clean up resources
  public void destroy() {
  }
}
