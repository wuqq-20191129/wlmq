<%


%>

<%@ page session="true"%>
<%@ page import="com.goldsign.acc.report.proxy.ReportProxy"%>
<%@ page import="com.goldsign.acc.report.proxy.util.Encryption"%>
<%@ page language="java" contentType="text/html;charset=GBK" %>

<%
    String reportName = request.getParameter("reportName");
    String fileType = request.getParameter("fileType");
    String serverName = request.getParameter("srvName");
    String serverUser = request.getParameter("srvUser");
    String serverPass = request.getParameter("srvPass");
    String handleServer = request.getParameter("hdlSrv");
    
//String fileType = "xls";
//System.out.println("ReportGenerator - reportName:"+reportName);
//System.out.println("ReportGenerator - fileType:"+fileType);

    if (reportName == null || reportName.length() == 0) {
        System.out.println("ReportGenerator - generate report fail!");
    } else {
        ReportProxy helper = null;
        helper = new ReportProxy();
       // Encryption en = new Encryption();
       // serverPass=en.biDecrypt(ReportProxy.EN_KEY, serverPass);
        // helper.exportCristalReport("report","password","report",reportName,request,response,getServletConfig().getServletContext(),fileType,3600000);
        //helper.exportCristalReport("report","password","iccsrpt",reportName,request,response,getServletConfig().getServletContext(),fileType,3600000);
        System.out.println("serverName:"+serverName+"serverUser:"+serverUser+"serverPass:"+serverPass+"handleServer:"+handleServer);
        if (handleServer.equals("1")) {
            helper.exportCristalReportByRas(serverUser, serverPass, serverName, reportName, request, response, getServletConfig().getServletContext(), fileType, 600000);
        } else {
            helper.exportCristalReport(serverUser, serverPass, serverName, reportName, request, response, getServletConfig().getServletContext(), fileType, 600000);
        }
        //处理部署到JBOSS上的OUT与OUTPUTSTREAM冲突异常
        out.clear();
        out =pageContext.pushBody();
        // helper.exportCristalReportByRasUsingSingleSession("report","iccsrpt","report2",reportName,request,response,getServletConfig().getServletContext(),fileType,3600000);

    }
%>
