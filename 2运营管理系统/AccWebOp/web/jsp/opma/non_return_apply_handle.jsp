<%--
    Document   : non_return_apply_handle
    Created on : 2017-6-26
    Author     : zhongziqi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>非即时退款申请处理</title>
<script language="javascript" type="text/javascript" charset="utf-8"
	src="js/common_form.js"></script>
<script language="javascript" type="text/javascript" charset="utf-8"
	src="js/Validator.js"></script>
<script language="javascript" type="text/javascript" charset="utf-8"
	src="js/non_return_apply_handle.js"></script>
<script language="JavaScript" src="js/CalendarPop.js"></script>
<link rel="stylesheet" type="text/css" href="css/frame/simple.css"
	title="Style" />
</head>
<body
	onload="preLoadVal('q_beginDatetime', 'q_endDatetime', 10);
            initDocument('queryOp', '');
            initDocument('detailOp', '');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            ickInit();
            setTableRowBackgroundBlock('DataTable1');
            setTableRowBackgroundBlock('DataTable');
            afterClickARow();
            setDefaultSelect();
          ">
	<table class="table_title">
		<tr align="center" class="trTitle">
			<td colspan="4">退票退款管理 非即时退款申请处理</td>
		</tr>
	</table>

	<!-- 表头 通用模板 -->

	<c:set var="pTitleName" scope="request" value="查询" />
	<c:set var="pTitleWidth" scope="request" value="50" />
	<c:import
		url="/jsp/common/common_template.jsp?template_name=common_table_title" />

	<form method="post" name="queryOp" id="queryOp"
		action="NonReturnApplyHandle">
		<!-- 页面用到的变量 通用模板 -->
		<!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="q_expAllFields" value="BUSINESS_RECEIPT_ID,CARD_LOGICAL_ID,CARD_PRINT_ID,STATION_ID,APPLY_DATETIME,HDL_FLAG,AUDIT_FLAG,APPLY_DAYS,APPLY_NAME,TEL_NO" />
			<input type="hidden" name="expFileName" id="q_expFileName" value="非即时退款申请处理.xlsx" />
			<input type="hidden" name="divId" id="q_divId" value="clearStartHead1" />
			<input type="hidden" name="methodUrl" id="q_methodUrl" value="/NonReturnApplyHandleExportAll" />
		<c:set var="divideShow" scope="request" value="1" />
		<c:import
			url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
		<table class="table_edit">
			<tr class="table_edit_tr">
				<td class="table_edit_tr_td_label">线路:</td>
				<td class="table_edit_tr_td_input"><select id="q_lineId"
					name="q_lineId"
					onChange="setSelectValues('queryOp', 'q_lineId', 'q_stationId', 'commonVariable');">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
				</select></td>
				<td class="table_edit_tr_td_label">车站:</td>
				<td class="table_edit_tr_td_input"><select id="q_stationId"
					name="q_stationId">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
				</select> <c:set var="pVarName" scope="request" value="commonVariable" /> <c:import
						url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
				</td>
				<td class="table_edit_tr_td_label">开始时间:</td>
				<td class="table_edit_tr_td_input"><input type="text"
					id="q_beginDatetime" name="q_beginDatetime" size="10"
					dataType="ICCSDate" msg="开始日期格式为[yyyy-mm-dd]" /> <a
					href="javascript:openCalenderDialogByID('q_beginDatetime','false');">
						<img src="./images/calendar.gif" width="15" height="15" border="0"
						style="" />
				</a></td>


			</tr>
			<tr class="table_edit_tr">
				<td class="table_edit_tr_td_label">查询条件:</td>
				<td class="table_edit_tr_td_input"><select
					id="q_queryCondition" name="q_queryCondition">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_nonRetunQueryCondition" />
				</select></td>
				<td class="table_edit_tr_td_label">印刻卡号或<br>交易凭证号:
				</td>
				<td class="table_edit_tr_td_input"><input type="text"
					name="q_businessReceiptId" id="q_businessReceiptId" size="30"
					maxLength="25" require="false" dataType="NumAndMinus"
					msg="最大25位,仅能输入数字和-(减号)" /></td>

				<td class="table_edit_tr_td_label">结束时间:</td>
				<td class="table_edit_tr_td_input"><input type="text"
					id="q_endDatetime" name="q_endDatetime" size="10"
					dataType="ICCSDate|ThanDate" to="q_beginDatetime"
					msg="结束日期格式为[yyyy-mm-dd]且大于等于开始日期" /> <a
					href="javascript:openCalenderDialogByID('q_endDatetime','false');">
						<img src="./images/calendar.gif" width="15" height="15" border="0"
						style="" />
				</a></td>


			</tr>
			<tr class="table_edit_tr">
				<td class="table_edit_tr_td_label">处理状态:</td>
				<td class="table_edit_tr_td_input"><select id="q_handleType"
					name="q_handleType">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_nonRetunHandleType" />
				</select></td>
				<td class="table_edit_tr_td_label" rowspan="1"><c:import
						url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
						<c:set var="expAll" scope="request" value="1" />
					<c:set var="export" scope="request" value="1" /> <c:set
						var="addClickMethod" scope="request"
						value="setControlNames('queryOp','q_businessReceiptId#q_beginDatetime#q_endDatetime#q_handleType#q_queryCondition#q_lineId#q_stationId');setLineCardNames('queryOp','q_lineId','q_stationId','commonVariable','','','');" />
					<c:set var="clickMethod" scope="request"
						value="btnClick('queryOp','clearStart1','detail','','clearStartHead1');" />
					<c:import
						url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
				</td>
				<td></td>
				<td class="table_edit_tr_td_label" rowspan="1"><c:import
						url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
					<c:set var="query" scope="request" value="1" /> <%--<c:set var="export" scope="request" value="1"/>--%>
					<c:set var="addClickMethod" scope="request"
						value="setControlNames('queryOp','q_businessReceiptId#q_beginDatetime#q_endDatetime#q_handleType#q_queryCondition#q_lineId#q_stationId');setLineCardNames('queryOp','q_lineId','q_stationId','commonVariable','','','');" />
					<c:set var="clickMethod" scope="request"
						value="btnClick('queryOp','clearStart1','detail','','clearStartHead1');" />
					<c:import
						url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
				</td>

			</tr>
		</table>
		<!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
		<!--标志位 用于判断数据是否被点击-->
		<input type="hidden" id="selectRowFlag" name="selectRowFlag"
			value="false" />
		<!--点击的时候保存三个信息-->
		<input type="hidden" id="selectReceiptId" name="selectReceiptId" /> <input
			type="hidden" id="selectLogicalId" name="selectLogicalId" /> <input
			type="hidden" id="selectApplyTime" name="selectApplyTime" />
		<div id="clearStartBlock1" class="divForTableBlock"
			style="height: 150px;">
			<div id="clearStartHead1" class="divForTableBlockHead"
				style="width: 1200px">
				<table class="table_list_block" id="DataTableHead1">
					<!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
					<tr class="table_list_tr_head_block" id="ignore">
						<td id="orderTd" style="width: 160px"
							class="table_list_tr_col_head_block" isDigit=false index="0"
							sortedby="asc" onclick="sortForTableBlock('clearStart1');">业务凭证号</td>
						<td id="orderTd" style="width: 130px"
							class="table_list_tr_col_head_block" isDigit=true index="1"
							sortedby="asc" onclick="sortForTableBlock('clearStart1');">逻辑卡号</td>
						<td id="orderTd" style="width: 130px"
							class="table_list_tr_col_head_block" isDigit=true index="2"
							sortedby="asc" onclick="sortForTableBlock('clearStart1');">印刻号</td>
						<td id="orderTd" style="width: 80px"
							class="table_list_tr_col_head_block" isDigit=false index="3"
							sortedby="asc" onclick="sortForTableBlock('clearStart1');">申请车站</td>
						<td id="orderTd" style="width: 130px"
							class="table_list_tr_col_head_block" isDigit=false index="4"
							sortedby="asc" onclick="sortForTableBlock('clearStart1');">申请日期</td>
						<td id="orderTd" style="width: 150px"
							class="table_list_tr_col_head_block" isDigit=false index="5"
							sortedby="asc" onclick="sortForTableBlock('clearStart1');">处理状态</td>
						<td id="orderTd" style="width: 60px"
							class="table_list_tr_col_head_block" isDigit=false index="6"
							sortedby="asc" onclick="sortForTableBlock('clearStart1');">审核状态</td>
						<td id="orderTd" style="width: 60px"
							class="table_list_tr_col_head_block" isDigit=true index="7"
							sortedby="asc" onclick="sortForTableBlock('clearStart1');">申请天数</td>
						<td id="orderTd" style="width: 80px"
							class="table_list_tr_col_head_block" isDigit=false index="8"
							sortedby="asc" onclick="sortForTableBlock('clearStart1');">乘客姓名</td>
						<td id="orderTd" style="width: 100px"
							class="table_list_tr_col_head_block" isDigit=false index="9"
							sortedby="asc" onclick="sortForTableBlock('clearStart1');">乘客电话</td>
					</tr>
				</table>
			</div>
			<div id="clearStart1" class="divForTableBlockData"
				style="height: 100px; overflow: auto; width: 1200px">
				<table class="table_list_block" id="DataTable1">
					<c:forEach items="${ResultSet}" var="rs">
						<tr class="table_list_tr_data"
							onMouseOver="overResultRow('detailOp', this);"
							onMouseOut="outResultRow('detailOp', this);"
							onclick="clickResultRow('detailOp', this, '');selectedRow1(this, 'queryOp');"
							id="${rs.businessReceiptId}">
							<c:choose>
								<c:when test="${rs.nonHandleFlag == '1'}">
									<td id="businessReceiptId" class="table_list_tr_col_data_block"
										style="width: 160px"><font color="red"> <b>${rs.businessReceiptId}</b>
									</font></td>
									<td id="cardLogicalId" class="table_list_tr_col_data_block"
										style="width: 130px"><font color="red"> <b>${rs.cardLogicalId}</b>
									</font></td>
									<td id="cardPrintId" class="table_list_tr_col_data_block"
										style="width: 130px"><font color="red"> <b>${rs.cardPrintId}</b>
									</font></td>
									<td id="stationId" class="table_list_tr_col_data_block"
										style="width: 80px"><font color="red"> <b>${rs.stationText}</b>
									</font></td>
									<td id="applyDatetime" class="table_list_tr_col_data_block"
										style="width: 130px"><font color="red"> <b>${rs.applyDatetime}</b>
									</font></td>
									<td id="hdlFlag" class="table_list_tr_col_data_block"
										style="width: 150px"><font color="red"> <b>${rs.hdlFlagText}</b>
									</font></td>
									<td id="auditFlag" class="table_list_tr_col_data_block"
										style="width: 60px"><font color="red"> <b>${rs.auditFlagText}</b>
									</font></td>
									<td id="applyDays" class="table_list_tr_col_data_block"
										style="width: 60px"><font color="red"> <b>${rs.applyDays}</b>
									</font></td>
									<td id="applyName" class="table_list_tr_col_data_block"
										style="width: 80px"><font color="red"> <b>${rs.applyName}</b>
									</font></td>
									<td id="telNo" class="table_list_tr_col_data_block"
										style="width: 100px"><font color="red"> <b>${rs.telNo}</b>
									</font></td>
								</c:when>
								<c:when test="${rs.nonHandleFlag == '2'}">
									<td id="businessReceiptId" class="table_list_tr_col_data_block"
										style="width: 160px"><font color="fuchsia"> <b>${rs.businessReceiptId}</b>
									</font></td>
									<td id="cardLogicalId" class="table_list_tr_col_data_block"
										style="width: 130px"><font color="fuchsia"> <b>${rs.cardLogicalId}</b>
									</font></td>
									<td id="cardPrintId" class="table_list_tr_col_data_block"
										style="width: 130px"><font color="fuchsia"> <b>${rs.cardPrintId}</b>
									</font></td>
									<td id="stationId" class="table_list_tr_col_data_block"
										style="width: 80px"><font color="fuchsia"> <b>${rs.stationText}</b>
									</font></td>
									<td id="applyDatetime" class="table_list_tr_col_data_block"
										style="width: 130px"><font color="fuchsia"> <b>${rs.applyDatetime}</b>
									</font></td>
									<td id="hdlFlag" class="table_list_tr_col_data_block"
										style="width: 150px"><font color="fuchsia"> <b>${rs.hdlFlagText}</b>
									</font></td>
									<td id="auditFlag" class="table_list_tr_col_data_block"
										style="width: 60px"><font color="fuchsia"> <b>${rs.auditFlagText}</b>
									</font></td>
									<td id="applyDays" class="table_list_tr_col_data_block"
										style="width: 60px"><font color="fuchsia"> <b>${rs.applyDays}</b>
									</font></td>
									<td id="applyName" class="table_list_tr_col_data_block"
										style="width: 80px"><font color="fuchsia"> <b>${rs.applyName}</b>
									</font></td>
									<td id="telNo" class="table_list_tr_col_data_block"
										style="width: 100px"><font color="fuchsia"> <b>${rs.telNo}</b>
									</font></td>
								</c:when>
								<c:otherwise>
									<td id="businessReceiptId" class="table_list_tr_col_data_block"
										style="width: 160px">${rs.businessReceiptId}</td>
									<td id="cardLogicalId" class="table_list_tr_col_data_block"
										style="width: 130px">${rs.cardLogicalId}</td>
									<td id="cardPrintId" class="table_list_tr_col_data_block"
										style="width: 130px">${rs.cardPrintId}</td>
									<td id="stationId" class="table_list_tr_col_data_block"
										style="width: 80px">${rs.stationText}</td>
									<td id="applyDatetime" class="table_list_tr_col_data_block"
										style="width: 130px">${rs.applyDatetime}</td>
									<td id="hdlFlag" class="table_list_tr_col_data_block"
										style="width: 150px">${rs.hdlFlagText}</td>
									<td id="auditFlag" class="table_list_tr_col_data_block"
										style="width: 60px">${rs.auditFlagText}</td>
									<td id="applyDays" class="table_list_tr_col_data_block"
										style="width: 60px">${rs.applyDays}</td>
									<td id="applyName" class="table_list_tr_col_data_block"
										style="width: 80px">${rs.applyName}</td>
									<td id="telNo" class="table_list_tr_col_data_block"
										style="width: 100px">${rs.telNo}</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</form>

	<!-- 表头 通用模板 -->
	<c:set var="pTitleName" scope="request" value="历史交易数据(红色表示最后一条进站数据，蓝色表示最后一笔退款数据，黄色表示错误数据)" />
	<c:set var="pTitleWidth" scope="request" value="1200" />
	<c:import
		url="/jsp/common/common_template.jsp?template_name=common_table_title" />
	<div id="clearStartBlock" class="divForTableBlock"
		style="height: 150px;">
		<div id="clearStartHead" class="divForTableBlockHead" style="width: 1200px; ">
			<table class="table_list_block" id="DataTableHead" >
				<!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
				<tr class="table_list_tr_head_block" id="ignore">
					<td id="orderTd" style="width: 130px"
						class="table_list_tr_col_head_block" isDigit=true index="0"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">逻辑卡号</td>
					<td id="orderTd" style="width: 130px"
						class="table_list_tr_col_head_block" isDigit=false index="1"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">交易日期</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=false index="2"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">交易类型</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=false index="3"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">交易线路</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=false index="4"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">交易车站</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=false index="5"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">交易设备</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=true index="6"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">交易金额(元)</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=true index="7"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">余额(元)</td>
					<td id="orderTd" style="width: 100px"
						class="table_list_tr_col_head_block" isDigit=false index="8"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">清算日期</td>
				</tr>
			</table>

		</div>
		<div id="clearStart" class="divForTableBlockData"
			style="height: 100px; overflow: auto;width: 1200px; ">
			<table class="table_list_block" id="DataTable">
				<c:forEach items="${hisResult}" var="hr">
					<c:choose>
						<c:when test="${hr.redFlag=='1'}">
							<tr class="table_list_tr_data">
								<td id="cardLogicalId" class="table_list_tr_col_data_block"
									style="width: 130px"><font color="red">
										${hr.cardLogicalId} </font></td>
								<td id="dealDatetime" class="table_list_tr_col_data_block"
									style="width: 130px"><font color="red">
										${hr.dealDatetime} </font></td>
								<td id="dealType" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="red">
										${hr.dealTypeText} </font></td>
								<td id="lineId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="red">
										${hr.lineText} </font></td>
								<td id="stationId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="red">
										${hr.stationText} </font></td>
								<td id="deviceId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="red">
										${hr.deviceId} </font></td>
								<td id="dealFee" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="red">
										${hr.dealFee} </font></td>
								<td id="dealBalanceFee" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="red">
										${hr.dealBalanceFee} </font></td>
								<td id="balanceWaterNo" class="table_list_tr_col_data_block"
									style="width: 100px"><font color="red">
										${hr.balanceWaterNo} </font></td>

							</tr>
						</c:when>
						<c:when test="${hr.redFlag =='2'}">
							<tr class="table_list_tr_data">
								<td id="cardLogicalId" class="table_list_tr_col_data_block"
									style="width: 130px"><font color="blue">
										${hr.cardLogicalId} </font></td>
								<td id="dealDatetime" class="table_list_tr_col_data_block"
									style="width: 130px"><font color="blue"> <font
										color="blue"> ${hr.dealDatetime} </font></td>
								<td id="dealType" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="blue">
										${hr.dealTypeText} </font></td>
								<td id="lineId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="blue">
										${hr.lineText} </font></td>
								<td id="stationId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="blue">
										${hr.stationText} </font></td>
								<td id="deviceId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="blue">
										${hr.deviceId} </font></td>
								<td id="dealFee" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="blue">
										${hr.dealFee} </font></td>
								<td id="dealBalanceFee" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="blue">
										${hr.dealBalanceFee} </font></td>
								<td id="balanceWaterNo" class="table_list_tr_col_data_block"
									style="width: 100px"><font color="blue">
										${hr.balanceWaterNo} </font></td>
							</tr>
							<input type="hidden" id="returnFlag" name="returnFlag" size="1"
								value="1" />
						</c:when>
						<c:when test="${hr.redFlag=='3'}">
							<tr class="table_list_tr_data" style="background: yellow">
								<td id="cardLogicalId" class="table_list_tr_col_data_block"
									style="width: 130px">${hr.cardLogicalId}</td>
								<td id="dealDatetime" class="table_list_tr_col_data_block"
									style="width: 130px">${hr.dealDatetime}</td>
								<td id="dealType" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.dealTypeText}</td>
								<td id="lineId" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.lineText}</td>
								<td id="stationId" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.stationText}</td>
								<td id="deviceId" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.deviceId}</td>
								<td id="dealFee" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.dealFee}</td>
								<td id="dealBalanceFee" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.dealBalanceFee}</td>
								<td id="balanceWaterNo" class="table_list_tr_col_data_block"
									style="width: 100px">${hr.balanceWaterNo}</td>

							</tr>
						</c:when>
						<c:otherwise>
							<tr class="table_list_tr_data">
								<td id="cardLogicalId" class="table_list_tr_col_data_block"
									style="width: 130px">${hr.cardLogicalId}</td>
								<td id="dealDatetime" class="table_list_tr_col_data_block"
									style="width: 130px">${hr.dealDatetime}</td>
								<td id="dealType" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.dealTypeText}</td>
								<td id="lineId" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.lineText}</td>
								<td id="stationId" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.stationText}</td>
								<td id="deviceId" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.deviceId}</td>
								<td id="dealFee" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.dealFee}</td>
								<td id="dealBalanceFee" class="table_list_tr_col_data_block"
									style="width: 80px">${hr.dealBalanceFee}</td>
								<td id="balanceWaterNo" class="table_list_tr_col_data_block"
									style="width: 100px">${hr.balanceWaterNo}</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</table>
		</div>
	</div>
	<FORM method="post" name="detailOp" id="detailOp"
		action="NonReturnApplyHandle">
		<!-- 表头 通用模板 -->
		<%--<c:set var="divideShow" scope="request" value="1"/>--%>
		<c:import
			url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
		<DIV id="detail" class="divForTableDataDetail">
			<table class="table_edit_detail">
				<tr class="table_edit_tr">
					<!--权限控制-->
					<td class="table_edit_tr_td_label">逻辑卡号:</td>
					<td class="table_edit_tr_td_input"><input type="hidden"
						id="hidden_confirmAuditRight" name="hidden_confirmAuditRight"
						value="${confirmAuditRight}" /> <input type="hidden"
						id="hidden_minReturnDate" name="hidden_minReturnDate"
						value="${selectVo.minReturnDate}" /> <input type="hidden"
						id="hidden_auditFlag" name="hidden_auditFlag"
						value="${selectVo.auditFlag}" /> <input type="hidden"
						id="hidden_appliedFlag" name="hidden_appliedFlag"
						value="${selectVo.appliedFlag}" /> <input type="hidden"
						id="hidden_hisRecordFlag" name="hidden_hisRecordFlag"
						value="${hisRecordFlag}" /> <input type="hidden"
						id="cardLogicalId" name="cardLogicalId"
						value="${selectVo.cardLogicalId}" /> <input type="text"
						name="d_cardLogicalId" id="d_cardLogicalId" size="25"
						disabled="true" value="${selectVo.cardLogicalId}" /></td>
					<td class="table_edit_tr_td_label">业务凭证号:</td>
					<td class="table_edit_tr_td_input"><input type="hidden"
						id="detailReceiptId" name="detailReceiptId"
						value="${selectVo.businessReceiptId}" /> <strong>
							${selectVo.businessReceiptId} </strong></td>
					<td class="table_edit_tr_td_label">卡状态:</td>

					<td class="table_edit_tr_td_input">
						<!--拒绝退款时用上--> <input type="hidden" id="blackCardFlag"
						name="blackCardFlag" value="${selectVo.blackCardFlag}" /> <c:if
							test="${selectVo.blackCardFlag=='1'}">
							<font color="red"> <strong> 黑名单 </strong>
							</font>
						</c:if> <c:if test="${selectVo.blackCardFlag=='0'}">
                                    正常
                            </c:if>
					</td>
				</tr>

				<tr class="table_edit_tr">
					<td class="table_edit_tr_td_label">系统余额(元):</td>
					<td class="table_edit_tr_td_input"><input type="hidden"
						id="systemBalanceFee" name="systemBalanceFee"
						value="${selectVo.systemBalanceFee}" /> <c:if
							test="${selectVo.systemBalanceFee eq selectVo.cardBalanceFee}">
                                ${selectVo.systemBalanceFee}
                            </c:if> <c:if
							test="${selectVo.systemBalanceFee ne selectVo.cardBalanceFee}">
							<font color="red"> <strong>
									${selectVo.systemBalanceFee} </strong>
						</c:if></td>
					<td class="table_edit_tr_td_label">票卡余额(元):</td>
					<td class="table_edit_tr_td_input"><input type="hidden"
						id="cardBalanceFee" name="cardBalanceFee"
						value="${selectVo.cardBalanceFee}" /> <input type="text"
						name="d_cardBalanceFee" id="d_cardBalanceFee" size="10"
						disabled="true" value="${selectVo.cardBalanceFee}" /></td>
					<td class="table_edit_tr_td_label">是否折损:</td>
					<td class="table_edit_tr_td_input"><c:if
							test="${selectVo.isBroken=='01'}">
							<font color="red"> <strong> 折损 </strong>
							</font>
						</c:if> <c:if test="${selectVo.isBroken=='00'}">
                                未折损
                            </c:if></td>

				</tr>
				<tr class="table_edit_tr">
					<td class="table_edit_tr_td_label">押金(元):</td>
					<td class="table_edit_tr_td_input"><input type="hidden"
						id="cardBalanceFee" name="depositFee"
						value="${selectVo.depositFee}" /> <input type="text"
						name="d_depositFee" id="d_depositFee" size="10" disabled="true"
						value="${selectVo.depositFee}" /></td>
					<td class="table_edit_tr_td_label">欠车程费(元)</td>
					<td class="table_edit_tr_td_input"><input type="hidden"
						id="penaltyFee" name="penaltyFee" value="${selectVo.penaltyFee}" />
						<input type="text" name="d_penaltyFee" id="d_penaltyFee" size="15"
						dataType="double" value="${selectVo.penaltyFee}" msg="欠车程费只能为正实数" />

					</td>
					<td class="table_edit_tr_td_label">实际应退还余额(元):</td>
					<td class="table_edit_tr_td_input"><input type="text"
						name="d_actualReturnBala" id="d_actualReturnBala" disabled="true"
						size="15" value="${selectVo.actualReturnBala}" /></td>
				</tr>
				<tr class="table_edit_tr">
					<td class="table_edit_tr_td_label">当前退款状态:</td>
					<td class="table_edit_tr_td_input">
						<!--权限判断--> <input type="hidden" id="hidden_hdlFlag"
						name="hidden_hdlFlag" value="${selectVo.hdlFlag}" /> <font
						color="red"> <strong> ${selectVo.hdlFlagText} </strong>
					</font>
					</td>
					<td class="table_edit_tr_td_label">扣款原因:</td>
					<td class="table_edit_tr_td_input"><input type="hidden"
						id="hidden_penatlyReason" name="hidden_penatlyReason"
						value="${selectVo.penaltyReason}" /> <select id="d_penatlyReason"
						name="d_penatlyReason" dataType="CheckPenatlyReason" from = "d_penaltyFee" msg="欠车程费大于0元时需要选择扣款原因,0元则不需要选择扣款原因！" >
							<c:import
								url="/jsp/common/common_template.jsp?template_name=option_list_pub_penatlyReason" />
					</select></td>
					<td class="table_edit_tr_td_label">修改处理状态:</td>
					<td class="table_edit_tr_td_input"><select id="d_handleFlag"
						name="d_handleFlag">
							<c:import
								url="/jsp/common/common_template.jsp?template_name=option_list_pub_handleFlag" />
					</select></td>
				</tr>
				<tr class="table_edit_tr">
					<td class="table_edit_tr_td_label">备注:</td>
					<td class="table_edit_tr_td_input" colspan=5><input
						type="text" name="d_remark" id="d_remark" size="90"
						value="${selectVo.remark}" dataType="LimitContainChinese" min="0"
						max="75" msg="备注最多为75个字符或者25个中文字" /></td>
				</tr>
			</table>
		</DIV>

		<c:import
			url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
			<!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="CARD_LOGICAL_ID,DEAL_DATETIME,DEAL_TYPE,LINE_ID,STATION_ID,DEVICE_ID,DEAL_FEE,DEAL_BALANCE_FEE,BALANCE_WATER_NO" />
			<input type="hidden" name="expFileName" id="d_expFileName" value="非即时退款历史记录.xlsx" />
			<input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
			<input type="hidden" name="methodUrl" id="d_methodUrl" value="/NonReturnApplyHandleHisExportAll" />
		<c:set var="btRefundOk" scope="request" value="1" />
		<c:set var="btRefundNo" scope="request" value="1" />
		<c:set var="btRefundMd" scope="request" value="1" />
		<c:set var="btRefundCk" scope="request" value="1" />

		<c:set var="addClickMethod" scope="request"
			value="beforeConfirmRefund();" />
		<c:set var="addClickMethod1" scope="request"
			value="beforeRefusedRefund();" />
		<c:set var="addClickMethod2" scope="request"
			value="beforeConfirmModify();" />
		<c:set var="addClickMethod3" scope="request"
			value="beforeConfirmAudit();" />
		<%--<c:set var="print" scope="request" value="1"/>--%>
		<c:set var="export" scope="request" value="1" />
		<c:set var="expAll" scope="request" value="1" />
		<%--<c:set var="btNext" scope="request" value="1"/>--%>
		<%--<c:set var="btNextEnd" scope="request" value="1"/>--%>
		<%--<c:set var="btBack" scope="request" value="1"/>--%>
		<%--<c:set var="btBackEnd" scope="request" value="1"/>--%>
		<c:set var="clickMethod" scope="request"
			value="btnClick('detailOp','clearStart','detail','','clearStartHead');" />
		<c:import
			url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
		<br />
	</FORM>
</body>
</html>
