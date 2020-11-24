<%@page import="com.goldsign.acc.frame.constant.ReportConstant"%>
<%@ page session="true"%>
<%@ page import="com.goldsign.acc.frame.util.ReportUtil"%>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    String reportName = request.getParameter("reportName");
    String fileType = request.getParameter("fileType");
    String paramNames = request.getParameter("paramNames");
    String dbUser = request.getParameter("dbUser");
    String dbPass = request.getParameter("dbPass");

    ReportUtil util = new ReportUtil();

    try {
        /*正式环境
         */
        util.genReportFileByThread("report", "password", ReportConstant.REPORT_SERVER_NAME, reportName,
                request, response, getServletConfig().getServletContext(),
                fileType, paramNames, (long) 600000,
                dbUser,dbPass);

    } catch (Exception e) {
        throw e;
    }

%>
