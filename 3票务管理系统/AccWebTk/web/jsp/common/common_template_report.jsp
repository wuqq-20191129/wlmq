<%-- 
    Document   : common_template
    Created on : 2017-5-17, 11:19:39
    Author     : hejj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String templateName = request.getParameter("template_name");
    if (templateName != null && templateName.length() != 0) {
        request.setAttribute("templateName", templateName);
    }


%>

<c:choose>



    <c:when test="${templateName == 'common_template_report_button_list_reportQuery'}">
        <%--<c:set var="clickMethod" scope="request" value="btnClickForGenReport('queryOp','${ControlNames}','${ParameterNames}','${QueryDateNames}');"/>--%>
        <input type="button" id="btReportQuery" name="ReportQuery"  value="查询" class="buttonStyle" onclick="btnClickForGenReport('${formOp}', '${ControlNames}', '${ParameterNames}', '${QueryDateNames}');" />

    </c:when>

    <c:when test="${templateName == 'common_template_report_title_table'}">
        <table class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="${colspan}">${reportTitle}</td>
            </tr>
        </table>
    </c:when>




    <c:when test="${templateName == 'common_template_report_data_table'}">
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead" >
                <!--id="DataTableHead"-->
                <table class="table_list_block" id="reportList" style="display:${Display}">
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=true index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">序号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="2" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 400px">报表连接</td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="3" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">日期</td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="4" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 300px">备注</td>
                    </tr>
                </table>
            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart" class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs" varStatus="status">
                        <!--class="listTableData" -->
                        <!--id=""-->
                        <tr class="table_list_tr_data" >
                            <td  id="number" class="table_list_tr_col_data_block" style="width: 100px">
                                ${status.index+1}
                            </td>
                            <td  id="listName" class="table_list_tr_col_data_block" style="width: 400px">
                                <a href="${rs.reportURL}" target="_blank">
                                    ${rs.listName}
                                </a>
                            </td>
                            <td  id="queryDate" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.queryDate}
                            </td>
                            <td  id="note" class="table_list_tr_col_data_block" style="width: 300px">
                                ${reportTitle}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_storage_id'}">
        <td class="table_edit_tr_td_label">仓库:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_storage_id" name="p_storage_id" require="false" dataType="NotEmpty" msg="仓库不能为空!">
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
            </select>
        </td>
    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_storage_id_isOnChange'}">
        <!--1仓库与票区关联 使用commonVariable1-->
        <td class="table_edit_tr_td_label">仓库:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_storage_id" name="p_storage_id" require="false" dataType="NotEmpty" msg="仓库不能为空!" 
                    onChange="setSelectValues('queryOp', 'p_storage_id', 'p_area_id', 'commonVariable1');" >
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
            </select>
        </td>
    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_area_id'}">
        <!--2仓库与票区关联 使用commonVariable1-->
        <td class="table_edit_tr_td_label">票区:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_area_id" name="p_area_id" require="false" dataType="NotEmpty" msg="票区不能为空!" >
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
            </select>
            <c:set var="pVarName" scope="request" value="commonVariable1"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone" />
        </td>

    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_card_main_type'}">
        <!--使用 commonVariable-->
        <td class="table_edit_tr_td_label">票卡主类型:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_card_main_type" name="p_card_main_type" require="false" dataType="NotEmpty" msg="票卡主类型不能为空!" 
                    onChange="setSelectValues('queryOp', 'p_card_main_type', 'p_card_sub_type', 'commonVariable');"  >				    
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
            </select>
        </td>

    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_card_main_type_default'}">
        <!--使用 commonVariable-->
        <td class="table_edit_tr_td_label">票卡主类型:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_card_main_type" name="p_card_main_type" require="false" dataType="NotEmpty" msg="票卡主类型不能为空!" 
                    onChange="setSelectValues('queryOp', 'p_card_main_type', 'p_card_sub_type', 'commonVariable');"  >				    
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard_default" />
            </select>
        </td>

    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_card_sub_type'}">
        <!--使用 commonVariable-->
        <td class="table_edit_tr_td_label">票卡子类型:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_card_sub_type" name="p_card_sub_type" require="false" dataType="NotEmpty" msg="票卡子类型不能为空!" >
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
            </select>
            <c:set var="pVarName" scope="request" value="commonVariable"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
        </td>

    </c:when>



    <c:when test="${templateName == 'common_template_report_qry_con_p_out_reason_id'}">
        <td class="table_edit_tr_td_label">出库原因:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_out_reason_id" name="p_out_reason_id" require="false" dataType="NotEmpty" msg="出库原因不能为空!" >
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_outreason_produce" />
            </select>
        </td>

    </c:when>


    <c:when test="${templateName == 'common_template_report_qry_con_p_in_reason_id'}">
        <td class="table_edit_tr_td_label">入库原因:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_in_reason_id" name="p_in_reason_id" require="false" dataType="NotEmpty" msg="入库原因不能为空!" >
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_inreason_in_new" />
            </select>
        </td>
    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_begin_day'}">
        <td class="table_edit_tr_td_label">起始日期: </td>
        <td class="table_edit_tr_td_input">
            <input type="text" name="p_begin_day" id="p_begin_day" value="${CurrentDate}" size="12" require="true" dataType="Date|NotEmpty" format="ymd" msg="起始日期不能为空且格式为(yyyy-mm-dd)!" />
            <a href="javascript:openCalenderDialogByID('p_begin_day','false');">
                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
            </a>
        </td>

    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_end_day'}">
        <td class="table_edit_tr_td_label">结束日期: </td>
        <td class="table_edit_tr_td_input">
            <input type="text" name="p_end_day" id="p_end_day" value="${CurrentDate}" size="12" require="true"  dataType="Date|ThanDate|NotEmpty" format="ymd" to="p_begin_day"
                   msg="结束日期不能为空且格式为(yyyy-mm-dd)且大于等于起始日期!" />
            <a href="javascript:openCalenderDialogByID('p_end_day','false');">
                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
            </a>
        </td>

    </c:when>
        
    <c:when test="${templateName == 'common_template_report_qry_con_p_final_day'}">
        <td class="table_edit_tr_td_label">截止日期: </td>
        <td class="table_edit_tr_td_input">
            <input type="text" name="p_begin_day" id="p_begin_day" value="${CurrentDate}" size="12" require="true" dataType="Date|NotEmpty" format="ymd" msg="截止日期不能为空且格式为(yyyy-mm-dd)!" />
            <a href="javascript:openCalenderDialogByID('p_begin_day','false');">
                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
            </a>
        </td>
    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_begin_year'}">
        <td class="table_edit_tr_td_label">起始年份:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_year" name="p_year" require="true" dataType="NotEmpty" msg="起始年份不能为空!">				    
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_years" />
            </select>
        </td>
    </c:when>



    <c:when test="${templateName == 'common_template_report_qry_con_p_end_year'}">             
        <td class="table_edit_tr_td_label">终止年份:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_year_end" name="p_year_end" require="true" dataType="NotEmpty" msg="年份不能为空!">				    
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_years" />
            </select>
        </td>
    </c:when> 

    <c:when test="${templateName == 'common_template_report_qry_con_p_storage_id_isOnChange1'}">
        <!--仓库与线路关联 使用commonVariable1-->
        <td class="table_edit_tr_td_label">仓库:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_storage_id" name="p_storage_id" require="false" dataType="NotEmpty" msg="仓库不能为空!"
                    onChange="setSelectValues('queryOp', 'p_storage_id', 'p_line_id', 'commonVariable1');"  >	
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
            </select>
        </td>
    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_line_id'}">
        <!--仓库与线路关联 使用commonVariable1-->
        <td class="table_edit_tr_td_label">线路:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_line_id" name="p_line_id" require="false" dataType="NotEmpty" msg="线路不能为空!" >
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
            </select>
            <c:set var="pVarName" scope="request" value="commonVariable1"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage_line_serial" />
        </td>
    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_year_month'}">
        <td class="table_edit_tr_td_label">年月:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_year" name="p_year" require="true" dataType="NotEmpty" msg="年份不能为空!">				    
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_years" />
            </select>

            <select id="p_month" name="p_month" require="true" dataType="NotEmpty" msg="月份不能为空!" >
                <option value="01">01</option>
                <option value="02">02</option>
                <option value="03">03</option>
                <option value="04">04</option>
                <option value="05">05</option>
                <option value="06">06</option>
                <option value="07">07</option>
                <option value="08">08</option>
                <option value="09">09</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
            </select>
        </td>
    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_bufferFlag'}">
        <td>
            <div align="right">
                <input type="checkbox" id="bufferFlag" name="bufferFlag" value="0">
            </div>
        </td>
        <td>
            <div align="left">取上次查询生成的报表</div>
        </td>

    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_bufferFlag1'}">
        <td>
            <div align="right">
                <input type="checkbox" id="bufferFlag" name="bufferFlag" value="0">
            </div>
        </td>
        <td colspan="2">
            <div align="left">取上次查询生成的报表</div>
        </td>

    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_device_id'}">
        <td class="table_edit_tr_td_label">设备ID:</td>
        <td class="table_edit_tr_td_input">
            <input type="text" name="p_device_id" id="p_device_id"  require="false" dataType="Number" msg="设备ID不能为空"/>	
        </td>

    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_worktype_id'}">
        <td class="table_edit_tr_td_label">操作类型:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_worktype_id" name="p_worktype_id" require="false" dataType="NotEmpty" msg="操作类型不能为空!" >
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_worktype" />
            </select>
        </td>

    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_operator_id'}">
        <td class="table_edit_tr_td_label">操作员ID:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_operator_id" name="p_operator_id" require="false" dataType="NotEmpty" msg="操作员ID不能为空!" >
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_esoperator" />
            </select>
        </td>
    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_borrow_unit_id'}">
        <td class="table_edit_tr_td_label">借票单位:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_borrow_unit_id" name="p_borrow_unit_id" require="false" dataType="NotEmpty" msg="借票单位不能为空!" >
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_outreason_borrow_until" />
            </select>
        </td>
    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_year'}">
        <td class="table_edit_tr_td_label">年份:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_year" name="p_year" require="false" dataType="NotEmpty" msg="年份不能为空!" >
                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_years" />
            </select>
        </td>
    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_return_flag'}">
        <td class="table_edit_tr_td_label">是否归还:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_return_flag" name="p_return_flag" require="false" dataType="NotEmpty" msg="是否归还不能为空!" >
                <option value="">=请选择=</option>
                <option value="0">未归还</option>
                <option value="1">已归还</option>
                <option value="2">部分归还</option>
            </select>
        </td>
    </c:when>

    <c:when test="${templateName == 'common_template_report_qry_con_p_adjust_reason_id'}">
        <td class="table_edit_tr_td_label">调账原因:</td>
        <td class="table_edit_tr_td_input">
            <select id="p_adjust_reason_id" name="p_adjust_reason_id" require="false" dataType="NotEmpty" msg="调账原因不能为空!" >
                <option value="">=请选择=</option>
                <option value="0">盘点调增</option>
                <option value="1">盘点调减</option>
                <option value="2">核销入库</option>
                <option value="3">清洗入库</option>
                <option value="4">生产入库</option>
            </select>
        </td>
    </c:when>

    <c:otherwise>

    </c:otherwise>
</c:choose>
