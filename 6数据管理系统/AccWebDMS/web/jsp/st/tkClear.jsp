<%-- 
    Document   : tkclear
    Created on : 2017-9-18, 16:30:21
    Author     : limj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>票务管理系统 清理日志</title>
    </head>
    <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
    <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
    <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
    <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
    <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    <body onload="
            setDiffTimeToCurTime('q_begin_clear_datetime',15);
            initDate('q_end_clear_datetime');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');

            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
        <table class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">票务管理系统 清理日志</td>
            </tr>
        </table>
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title"></c:import>
        <form method="post" name="queryOp" id="queryOp" action="tkClear">
            <!-- 页面用到的变量 通用模板-->
            <c:set var="divideShow" scope="request" value="1" />
            <c:import url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable"/>
            <table class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">源表表名：</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_origin_table_name" id="q_origin_table_name" size="35" require="false"
                               maxLength="40" dataType="NotEmpty"/>
                    </td>
                    <td class="table_edit_tr_td_label">目的表名：</td>
                    <td class="table_edit_tr_td_input">
                        <input  name="q_dest_table_name" id="q_dest_table_name" type="text" require="false" dataType="string"/>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">开始时间：</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" dataType="ICCSDateTime" name="q_begin_clear_datetime" 
                               id="q_begin_clear_datetime" size="20" require="true"
                               msg="请选择开始时间，日期格式为(yyyy-mm-dd hh:mm:ss)"/>
                        <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" onclick="openDetailCalenderDialogByID('q_begin_clear_datetime','false')" />
                    </td>
                    <td class="table_edit_tr_td_label">结束时间：</td>
                    <td>
                        <input type="text" dataType="ICCSDateTime|ThanDate|dateDiff" name="q_end_clear_datetime"
                                id="q_end_clear_datetime" size="20" require="true" to="q_begin_clear_datetime"
                                msg="请选择结束时间，格式为(yyyy-mm-dd hh:mm:ss)且大于等于开始时间，查询范围为90天!"/>
                        <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" onclick="openDetailCalenderDialogByID('q_end_clear_datetime','false')" />
                    </td> 
                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_origin_table_name#q_dest_table_name#q_begin_clear_datetime#q_end_clear_datetime')"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>
        </form>
        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        <!--表头 通用模板-->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title"/>
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 133%">
                <table>
                    <!--说明：列的序号从0开始 isDigit:false 表示按字符串排序：true 表示按数值排序 sortedby: asc表示升序 dec表示降序-->
                    <tr class="table_list_tr_head_block" id="ignore">
                        <td class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" 
                                   onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');"/>
                        </td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="1"
                            onclick="sortForTableBlock('clearStart');" style="width: 190px">源表表名</td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="2"
                            onclick="sortForTableBlock('clearStart');" style="width: 190px">目的表名</td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="3"
                            onclick="sortForTableBlock('clearStart');" style="width: 100px">起始票务流水号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="4"
                            onclick="sortForTableBlock('clearStart');" style="width: 100px">结束票务流水号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="5"
                            onclick="sortForTableBlock('clearStart');" style="width: 120px">开始时间</td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="6"
                            onclick="sortForTableBlock('clearStart');" style="width: 120px">结束时间</td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=true index="7"
                            onclick="sortForTableBlock('clearStart');" style="width: 100px">数量</td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="8"
                            onclick="sortForTableBlock('clearStart');" style="width: 380px">信息</td>
                    </tr>
                </table>
            </div>
            <div id="clearStart" class="divForTableBlockData" style="width: 133%">
                <table class="table_list_block" id="DataTable">
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data" onmouseover="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickOneResultRow('detailOp', this, '');
                                        setPageControl('detailOp');"
                            id="${rs.originTableName}#${rs.destTableName}#${rs.beginBillNo}#${rs.endBillNo}#${rs.beginClearDateTime}#${rs.endClearDateTime}">
                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.originTableName}"/>
                            </td>
                            <td id="originTableName" class="table_list_tr_col_data_block" style="width: 190px">
                                ${rs.originTableName}
                            </td>
                            <td id="destTableName" class="table_list_tr_col_data_block" style="width: 190px">
                                ${rs.destTableName}
                            </td>
                            <td id="beginBillNo" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.beginBillNo}
                            </td>
                            <td id="endBillNo" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.endBillNo}
                            </td>
                            <td id="beginClearDateTime" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.beginClearDateTime}
                            </td>
                            <td id="endClearDateTime" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.endClearDateTime}
                            </td>
                            <td id="clearRecdCount" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.clearRecdCount}
                            </td>
                            <td id="errDiscribe" class="table_list_tr_col_data_block" style="width: 390px">
                                ${rs.errDiscribe}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <c:set var="pTitleName" scope="request" value="明细" />
        <c:set var="pTitleWidth" scope="request" value="50" />
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        
        <FORM method="post" name="detailOp" id="detailOp" action="tkClear" >
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"  value="getOriginTableName,getDestTableName,getBeginBillNo,getEndBillNo,getBeginClearDateTime,getEndClearDateTime,getClearRecdCount,getErrDiscribe" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票务管理系统清理日志.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl"  value="/tkClearExportAll"/>
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">                  
                </table>
            </div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        
    </body>
</html>
