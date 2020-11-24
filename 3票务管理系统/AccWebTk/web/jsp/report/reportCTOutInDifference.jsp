<%-- 
    Document   : ReportCTOutInDifference
    Created on : 2017-7-27, 20:15:54
    Author     : Yangze
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--设置报表title-->
        <c:set var="reportTitle" scope="request" value="数量误差统计"/>
        
        <title>${reportTitle}</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="
                initDocument('queryOp','');
                setControlsDefaultValue('queryOp');
                setSelectValues('queryOp','p_storage_id','p_area_id','commonVariable1');
                setSelectValues('queryOp','p_card_main_type','p_card_sub_type','commonVariable');
                setControlsDefaultValue('queryOp');
          ">
                <!--setLinkTextForRT('queryOp','');-->

        <!--<body >-->
        <!--报表title-->
        <c:set var="colspan" scope="request" value="4"/>
        <c:import url="/jsp/common/common_template_report.jsp?template_name=common_template_report_title_table" />

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <form method="post" name="queryOp" id="queryOp" action="ticketStorageReportManage">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <c:set var="ParameterNames" scope="request" value="p_storage_id#p_area_id#p_card_main_type#p_card_sub_type#p_bill_no#p_adjust_reason_id#p_begin_day#p_end_day"/>
            <c:set var="QueryDateNames" scope="request" value="p_begin_day#p_end_day"/>
            <!--查询条件-->
            <c:set var="ControlNames" scope="request" value="p_storage_id#p_area_id#p_card_main_type#p_card_sub_type#p_adjust_reason_id#p_begin_day#p_end_day#bufferFlag"/>
            <c:import url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_report" />
            
            <table class="table_edit">
                <tr class="table_edit_tr">
                    <!--仓库-->
                    <c:import url="/jsp/common/common_template_report.jsp?template_name=common_template_report_qry_con_p_storage_id_isOnChange" />
                    
                    <!--票区-->
                    <c:import url="/jsp/common/common_template_report.jsp?template_name=common_template_report_qry_con_p_area_id" />
                    
                    <!--票卡主类型-->
                    <c:import url="/jsp/common/common_template_report.jsp?template_name=common_template_report_qry_con_p_card_main_type" />
                    
                    <!--票卡子类型-->
                    <c:import url="/jsp/common/common_template_report.jsp?template_name=common_template_report_qry_con_p_card_sub_type" />
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">单号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="p_bill_no" name="p_bill_no" value="" size="12" maxlength="12" require="false" dataType="LimitB" min="12" max="12" msg="单号应为12位" />
                    </td>
                    <!--调账原因-->
                    <c:import url="/jsp/common/common_template_report.jsp?template_name=common_template_report_qry_con_p_adjust_reason_id" />

                    <!--起始日期-->
                    <c:import url="/jsp/common/common_template_report.jsp?template_name=common_template_report_qry_con_p_begin_day" />
                    
                    <!--结束日期-->
                    <c:import url="/jsp/common/common_template_report.jsp?template_name=common_template_report_qry_con_p_end_day" />
                </tr>
                <tr class="table_edit_tr">
                    <!--取上次查询生成的报表-->
                    <c:import url="/jsp/common/common_template_report.jsp?template_name=common_template_report_qry_con_bufferFlag" />
                    
                    <td class="table_edit_tr_td_label">
                        <c:set var="formOp" scope="request" value="queryOp"/>
                        <c:import url="/jsp/common/common_template_report.jsp?template_name=common_template_report_button_list_reportQuery" />
                        
                        <!--<input type="button" id="btReportQuery" name="ReportQuery"  value="查询" class="buttonStyle" onclick="${clickMethod};" />-->
                    </td>
                    <td colspan="5"></td>
                </tr>
                <tr class="table_edit_tr">
                    <td>
                        <div align="right">备注:</div>
                    </td>
                    <td colspan="7">
                        <div align="left">查询条件的日期为单据的审核日期</div>
                    </td>
                </tr>
            </table>
        </form>

        <!-- 表头 通用模板 -->
        <!--查询有记录才显示-->
        <c:if test="${Display == '1'}">
            <c:set var="pTitleName" scope="request" value="列表"/>
            <c:set var="pTitleWidth" scope="request" value="50"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        </c:if>    
        
        <!--数据列表-->  <!--查询有记录才显示-->
        <c:import url="/jsp/common/common_template_report.jsp?template_name=common_template_report_data_table" />

        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>