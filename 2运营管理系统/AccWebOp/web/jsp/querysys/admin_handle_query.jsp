<%--
    Document   : admin_handle_query
    Created on : 2017-6-15
    Author     : zhongziqi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>行政处理查询</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="preLoadVal('q_beginDatetime','q_endDatetime',30);
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    客服明细查询 行政处理查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="AdminHandleQuery">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">开始时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_beginDatetime" name="q_beginDatetime"   size="10"  dataType="ICCSDate"  msg="开始日期格式为[yyyy-mm-dd]"/>
                         <a href="javascript:openCalenderDialogByID('q_beginDatetime','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                    </td>
                    <td class="table_edit_tr_td_label">结束时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_endDatetime" name="q_endDatetime"   size="10"  dataType="ICCSDate|ThanDate" to="q_beginDatetime" msg="结束日期格式为[yyyy-mm-dd]且大于等于开始日期"/>
                         <a href="javascript:openCalenderDialogByID('q_endDatetime','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                    </td>
                    <td class="table_edit_tr_td_label">行政处理原因:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_adminHandleReason" name = "q_adminHandleReason">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_adminHandleReason" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">票卡主类型</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_cardMainID" name = "q_cardMainID" onChange="setSelectValues('queryOp', 'q_cardMainID', 'q_cardSubID', 'commonVariable');">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票卡子类型</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_cardSubID" name = "q_cardSubID">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_beginDatetime#q_endDatetime#q_adminHandleReason#q_cardMainID#q_cardSubID');setLineCardNames('queryOp','','','','q_cardMainID','q_cardSubID','commonVariable');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>

        </form>
		<!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead" style=" width: 1400px " >
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                        <td id="orderTd"   style =" width: 90px" class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"   style =" width: 90px" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"   style =" width: 90px" class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >事务类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备代码</td>
                        <td id="orderTd"   style =" width: 130px" class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >日期时间</td>
                        <td id="orderTd"    style =" width: 80px" class="table_list_tr_col_head_block"  isDigit=true  index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >退还金额(分)</td>
						<td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true  index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >罚金(分)</td>
						<td id="orderTd"   style =" width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style =" width: 80px">行政处理</td>
						<td id="orderTd"    style =" width: 220px" class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >行政处理描述</td>
						<td id="orderTd"     class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >乘客姓名</td>
						<td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >线路</td>
						<td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >车站</td>
                    </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData" style=" width: 1400px ">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="clickResultRow('detailOp', this, '');
                                    setPageControl('detailOp');"
                            id="${rs.waterNo}">
                            <td  id="waterNo" class="table_list_tr_col_data_block">
                                ${rs.waterNo}
                            </td>
                            <td  id="cardMainId" class="table_list_tr_col_data_block" style =" width: 90px">
                                ${rs.cardMainText}
                            </td>
                            <td  id="cardSubId" class="table_list_tr_col_data_block" style =" width: 90px">
                                ${rs.cardSubText}
                            </td>
                            <td  id="adminWayId" class="table_list_tr_col_data_block" style =" width: 90px">
                                ${rs.adminWayText}
                            </td>
                            <td  id="devTypeId" class="table_list_tr_col_data_block">
                                ${rs.devTypeText}
                            </td>
                            <td  id="deviceId" class="table_list_tr_col_data_block">
                                ${rs.deviceId}
                            </td>
                            <td  id="adminDatetime" class="table_list_tr_col_data_block" style =" width: 130px">
                                ${rs.adminDatetime}
                            </td>
                            <td  id="returnFee" class="table_list_tr_col_data_block" style =" width: 80px">
                                ${rs.returnFee}
                            </td>
                            <td  id="penaltyFee" class="table_list_tr_col_data_block">
                                ${rs.penaltyFee}
                            </td>
                            <td  id="adminReasonId" class="table_list_tr_col_data_block" style =" width: 120px">
                                ${rs.adminReasonText}
                            </td>
                            <td  id="describe" class="table_list_tr_col_data_block" style =" width: 220px">
                                ${rs.describe}
                            </td>
                            <td  id="passengerName" class="table_list_tr_col_data_block">
                                ${rs.passengerName}
                            </td>
                            <td  id="lineId" class="table_list_tr_col_data_block">
                                ${rs.lineText}
                            </td>
                            <td  id="stationId" class="table_list_tr_col_data_block">
                                ${rs.stationText}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <FORM method="post" name="detailOp" id="detailOp" action="AdminHandleQuery" >
        <!-- 表头 通用模板 -->
			<!--导出全部参数 -->
<!--             <input type="hidden" name="expAllFields" id="d_expAllFields" value="WATER_NO,CARD_MAIN_ID,CARD_SUB_ID,ADMIN_WAY_ID,DEV_TYPE_ID,DEVICE_ID,ADMIN_DATETIME,RETURN_FEE,PENALTY_FEE,ADMIN_REASON_ID,DESCRIBE,PASSENGER_NAME,LINE_ID,STATION_ID" /> -->
			<input type="hidden" name="expAllFields" id="d_expAllFields" value="getWaterNo,getCardMainText,getCardSubText,getAdminWayText,getDevTypeText,getDeviceId,getAdminDatetime,getReturnFee,getPenaltyFee,getAdminReasonText,getDescribe,getPassengerName,getLineText,getStationText" />
			<input type="hidden" name="expFileName" id="d_expFileName" value="行政处理查询.xlsx" />
			<input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
			<input type="hidden" name="methodUrl" id="d_methodUrl" value="/AdminHandleQueryExportAll" />
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1"/>
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
