<%-- 
    Document   : report_qry_template
    Created on : 2017-7-9
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>线路 AG 寄存器数据月报表</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
            <body onload="initDocument('queryOp', '');
                    setControlsDefaultValue('queryOp');
                    setLinkText('queryOp','2','lineID');"
            >
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">设备寄存器报表--线路 AG 寄存器数据月报表
                </td>
            </tr>
        </table>


        <!-- 琛ㄥご 閫氱敤妯℃澘 -->

        <%--<c:set var="pTitleName" scope="request" value="鏌ヨ"/>--%>
        <%--<c:set var="pTitleWidth" scope="request" value="50"/>--%>
        <%--<c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />--%>

        <!--<form method="post" name="queryOp" id="queryOp" action="DevAgmMon">-->
        <form method="post" name="queryOp" id="queryOp" action="Report">
            <!-- 椤甸潰鐢ㄥ埌鐨勫彉閲� 閫氱敤妯℃澘 -->
            <input type="hidden" name="actionName" value="DevAgmMon"/>

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <%--<c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=saveReportCodeMapping" />--%>
            <c:import  url="/jsp/report/report_qry_template.jsp?template_name=op_report_saveReportCodeMapping" />

            <table  class="table_edit">
                <!--<tr class="table_edit_tr">-->
                <!--</tr>-->
                <tr class="table_edit_tr">
                    <c:import url="/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_year_month" /> 
<c:import url="/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_lineID" /> 
<c:import url="/jsp/report/report_qry_template.jsp?template_name=op_report_qry_con_isBalanceDate" /> 

                    
                    <!--<td class="table_edit_tr_td_label" rowspan="1">-->
                    <td class="table_edit_tr_td_label">
                        <c:set var="formName" scope="request" value="queryOp"/>
                        <c:set var="reportType" scope="request" value="2"/>
                        <c:set var="controlNames" scope="request" value="lineID"/>
                        <%--<c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>--%>
                        <c:import url="/jsp/report/report_qry_template.jsp?template_name=op_report_button_list_all" />
                    </td>
                </tr>
            </table>

        </form>


        <c:set var="note" scope="request" value="线路 AG 寄存器数据月报表"/>
        <c:import url="/jsp/report/report_qry_template.jsp?template_name=op_report_table" />


        <!-- 鐘舵�佹爮 閫氱敤妯℃澘 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
