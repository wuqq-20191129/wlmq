<%--
    Document   : signcard_return_list
    Created on : 2017-6-16
    Author     : zhongziqi
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>记名卡退款退卡查询</title>
<script language="javascript" type="text/javascript" charset="utf-8"
	src="js/common_form.js"></script>
<script language="javascript" type="text/javascript" charset="utf-8"
	src="js/Validator.js"></script>
<script language="JavaScript" src="js/CalendarPop.js"></script>
<link rel="stylesheet" type="text/css" href="css/frame/simple.css"
	title="Style" />
<script language="javascript">
	function selCheck() {
		var checkBtn = document.getElementById('q_balaCheck');
		if (checkBtn.checked == true) {
			document.getElementById('q_rtnOper').disabled = false;
			document.getElementById('q_rtnBala').disabled = false;
		} else {
			document.getElementById('q_rtnOper').disabled = true;
			document.getElementById('q_rtnBala').disabled = true;
			document.getElementById('q_rtnBala').value = "0";
		}
	}
	function formInit() {
		//                 alert(document.getElementById('q_balaCheckFlag').value);
		if (document.getElementById('q_balaCheckFlag').value == "true") {
			document.getElementById('q_balaCheck').value = "1";
			document.getElementById('q_balaCheck').checked = true;
		}
		if (document.getElementById('q_balaCheck').checked) {
			document.getElementById('q_rtnOper').disabled = false;
			document.getElementById('q_rtnBala').disabled = false;
		}
		if (document.getElementById('q_rtnBala').value == 'null') {
			document.getElementById('q_rtnBala').value = "0";
		}
	}
</script>
</head>
<body
	onload="preLoadVal('q_beginDatetime', 'q_endDatetime');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            formInit();
            setTableRowBackgroundBlock('DataTable')">
	<table class="table_title">
		<tr align="center" class="trTitle">
			<td colspan="4">客服明细查询 记名卡退款退卡查询</td>
		</tr>
	</table>

	<!-- 表头 通用模板 -->
	<c:set var="pTitleName" scope="request" value="查询" />
	<c:set var="pTitleWidth" scope="request" value="50" />
	<c:import
		url="/jsp/common/common_template.jsp?template_name=common_table_title" />
	<form method="post" name="queryOp" id="queryOp"
		action="SignCardReturnQuery">
		<!-- 页面用到的变量 通用模板 -->
		<c:set var="divideShow" scope="request" value="1" />
		<c:import
			url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
		<table class="table_edit">
			<tr class="table_edit_tr">
				<td class="table_edit_tr_td_label">逻辑卡号:</td>
				<td class="table_edit_tr_td_input"><input type="text"
					name="q_cardLogicalId" id="q_cardLogicalId" size="25"
					maxLength="20" require="false" dataType="integer"
					msg="逻辑卡号只能为数字，最大长度为20位" /></td>
				<td class="table_edit_tr_td_label">票卡主类型:</td>
				<td class="table_edit_tr_td_input"><select id="q_cardMainId"
					name="q_cardMainId"
					onchange="setSelectValues('queryOp', 'q_cardMainId', 'q_cardSubId', 'commonVariable1');">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
				</select></td>
				<td class="table_edit_tr_td_label">票卡子类型:</td>
				<td class="table_edit_tr_td_input"><select id="q_cardSubId"
					name="q_cardSubId">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
				</select> <c:set var="pVarName" scope="request" value="commonVariable1" /> <c:import
						url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
				</td>
			</tr>
			<tr class="table_edit_tr">
				<td class="table_edit_tr_td_label">业务凭证号：</td>
				<td class="table_edit_tr_td_input"><input type="text"
					name="q_receiptId" id="q_receiptId" size="30" maxLength="25"
					require="false" dataType="NumAndMinus" max="25"
					msg="业务凭证号最大25位,仅能输入数字和-(减号)" /></td>

				<td class="table_edit_tr_td_label">退款线路:</td>
				<td class="table_edit_tr_td_input"><select id="q_returnLineId"
					name="q_returnLineId"
					onChange="setSelectValues('queryOp', 'q_returnLineId', 'q_returnStationId', 'commonVariable');">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
				</select></td>
				<td class="table_edit_tr_td_label"><div align="right">退款车站:</div></td>
				<td class="table_edit_tr_td_input"><select
					id="q_returnStationId" name="q_returnStationId">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
				</select> <c:set var="pVarName" scope="request" value="commonVariable" /> <c:import
						url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
				</td>
			</tr>
			<tr class="table_edit_tr">
			<td class="table_edit_tr_td_label">开始时间:</td>
				<td class="table_edit_tr_td_input"><input type="text"
					id="q_beginDatetime" name="q_beginDatetime" size="10"
					dataType="ICCSDate" msg="开始日期格式为[yyyy-mm-dd]" /> <a
					href="javascript:openCalenderDialogByID('q_beginDatetime','false');">
						<img src="./images/calendar.gif" width="15" height="15" border="0"
						style="" />
				</a></td>
				<td class="table_edit_tr_td_label">证件类型：</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_identifyType" name = "q_identifyType">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_identifyType" />
                        </select>
                    </td>
				<td class="table_edit_tr_td_label">证件号码：</td>
				<td class="table_edit_tr_td_input"><input type="text"
					name="q_identityId" id="q_identityId" size="20" maxLength="18"
					require="false" max="18" dataType="NumAndEng"
					msg="证件号最大18位，仅允许输入数字或字母" /></td>


			</tr>
			<tr class="table_edit_tr">
			<td class="table_edit_tr_td_label">结束时间:</td>
				<td class="table_edit_tr_td_input"><input type="text"
					id="q_endDatetime" name="q_endDatetime" size="10"
					dataType="ICCSDate|ThanDate" to="q_beginDatetime"
					msg="结束日期格式为[yyyy-mm-dd]且大于等于开始日期" /> <a
					href="javascript:openCalenderDialogByID('q_endDatetime','false');">
						<img src="./images/calendar.gif" width="15" height="15" border="0"
						style="" />
				</a></td>
				<td class="table_edit_tr_td_label">处理状态:</td>
				<td class="table_edit_tr_td_input"><select id="q_hdlFlag"
					name="q_hdlFlag">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_signCardHandleType" />
				</select></td>
				<td class="table_edit_tr_td_label">理论应退金额(元):</td>
				<td class="table_edit_tr_td_input"><input type="hidden"
					id="q_balaCheckFlag" name="q_balaCheckFlag"
					value="${balaCheckFlag}" /> <input type="checkbox"
					id="q_balaCheck" name="q_balaCheck" value="0" onclick="selCheck();" />
					<select id="q_rtnOper" name="q_rtnOper" disabled="true">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_operationChar" />
				</select> <input type="text" id="q_rtnBala" name="q_rtnBala" disabled="true"
					dataType="Double" msg="理论应退金额必须为实数" value="0" style="width: 70px" />
				</td>

				<td class="table_edit_tr_td_label" rowspan="2"><c:import
						url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
					<c:set var="query" scope="request" value="1" /> <c:set
						var="addClickMethod" scope="request"
						value="setControlNames('queryOp','q_cardLogicalId#q_cardMainId#q_cardSubId#q_receiptId#q_returnLineId#q_returnStationId#q_identifyType#q_identityId#q_beginDatetime#q_endDatetime#q_hdlFlag#q_rtnBala#q_balaCheck#q_rtnOper');setLineCardNames('queryOp','q_returnLineId','q_returnStationId','commonVariable','q_cardMainId','q_cardSubId','commonVariable1');" />
					<c:set var="clickMethod" scope="request"
						value="btnClick('queryOp','clearStart','detail');" /> <c:import
						url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
				</td>
			</tr>
		</table>

	</form>
	<!-- 状态栏 通用模板 -->
	<c:import
		url="/jsp/common/common_template.jsp?template_name=common_status_table" />
	<!-- 表头 通用模板 -->
	<c:set var="pTitleName" scope="request" value="列表" />
	<c:set var="pTitleWidth" scope="request" value="50" />
	<c:import
		url="/jsp/common/common_template.jsp?template_name=common_table_title" />
	<div id="clearStartBlock" class="divForTableBlock">
		<div id="clearStartHead" class="divForTableBlockHead"
			style="width: 2000px;">
			<table class="table_list_block" id="DataTableHead">
				<!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
				<tr class="table_list_tr_head_block" id="ignore">
					<td id="orderTd" style="width: 130px"
						class="table_list_tr_col_head_block" isDigit=true index="0"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">逻辑卡号</td>
					<td id="orderTd" style="width: 180px"
						class="table_list_tr_col_head_block" isDigit=false index="1"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">凭证号</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=false index="2"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">证件类型</td>
					<td id="orderTd" style="width: 130px"
						class="table_list_tr_col_head_block" isDigit=false index="3"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">证件号码</td>
					<td id="orderTd" style="width: 90px"
						class="table_list_tr_col_head_block" isDigit=false index="4"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">票卡主类型</td>
					<td id="orderTd" style="width: 90px"
						class="table_list_tr_col_head_block" isDigit=false index="5"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">票卡子类型</td>
					<td id="orderTd" style="width: 90px"
						class="table_list_tr_col_head_block" isDigit=false index="6"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">乘客姓名</td>
					<td id="orderTd" style="width: 110px"
						class="table_list_tr_col_head_block" isDigit=true index="7"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">理论应退款金额(元)</td>
					<td id="orderTd" style="width: 110px"
						class="table_list_tr_col_head_block" isDigit=true index="8"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">实际退款金额(元)</td>
					<td id="orderTd" style="width: 130px"
						class="table_list_tr_col_head_block" isDigit=false index="9"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">申请日期</td>
					<td id="orderTd" style="width: 150px"
						class="table_list_tr_col_head_block" isDigit=false index="10"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">处理状态</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=false index="11"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">线路代码</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=false index="12"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">车站代码</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=false index="13"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">退款线路</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=false index="14"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">退款车站</td>
					<td id="orderTd" style="width: 250px"
						class="table_list_tr_col_head_block" isDigit=false index="15"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">备注</td>
				</tr>
			</table>
		</div>
		<div id="clearStart" class="divForTableBlockData" style="width: 2000px;">
			<table class="table_list_block" id="DataTable">
				<c:forEach items="${ResultSet}" var="rs">
					<tr class="table_list_tr_data"
						onMouseOver="overResultRow('detailOp', this);"
						onMouseOut="outResultRow('detailOp', this);"
						onclick="clickResultRow('detailOp', this, '');
                                    setPageControl('detailOp');"
						id="${rs.waterNo}">
						<c:choose>
							<c:when test="${rs.nonHandleFlag == '1'}">
								<td id="cardLogicalId" class="table_list_tr_col_data_block"
									style="width: 130px"><font color="red"> <b>${rs.cardLogicalId}</b>
								</font></td>
								<td id="receiptId" class="table_list_tr_col_data_block"
									style="width: 180px"><font color="red"> <b>${rs.businessReceiptId}</b>
								</font></td>
								<td id="identityType" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="red"> <b>${rs.identityTypeText}</b>
								</font></td>
								<td id="identityId" class="table_list_tr_col_data_block"
									style="width: 130px"><font color="red"> <b>${rs.identityId}</b>
								</font></td>
								<td id="cardMainId" class="table_list_tr_col_data_block"
									style="width: 90px"><font color="red"> <b>${rs.cardMainText}</b>
								</font></td>
								<td id="cardSubId" class="table_list_tr_col_data_block"
									style="width: 90px"><font color="red"> <b>${rs.cardSubText}</b>
								</font></td>
								<td id="applyName" class="table_list_tr_col_data_block"
									style="width: 90px"><font color="red"> <b>${rs.applyName}</b>
								</font></td>
								<td id="returnBala" class="table_list_tr_col_data_block"
									style="width: 110px"><font color="red"> <b>${rs.returnBala}</b>
								</font></td>
								<td id="actualReturnBala" class="table_list_tr_col_data_block"
									style="width: 110px"></td>
								<td id="applyDatetime" class="table_list_tr_col_data_block"
									style="width: 130px"><font color="red"> <b>${rs.applyDatetime}</b>
								</font></td>
								<td id="hdlFlag" class="table_list_tr_col_data_block"
									style="width: 150px"><font color="red"> <b>${rs.hdlText}</b>
								</font></td>
								<td id="lineId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="red"> <b>${rs.lineText}</b>
								</font></td>
								<td id="stationId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="red"> <b>${rs.stationText}</b>
								</font></td>

								<td id="returnLineId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="red"> <b>${rs.returnLineText}</b>
								</font></td>
								<td id="returnStationId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="red"> <b>${rs.returnStationText}</b>
								</font></td>
								<td id="remark" class="table_list_tr_col_data_block"
									style="width: 250px"><font color="red"> <b>${rs.remark}</b>
								</font></td>
							</c:when>
							<c:when test="${rs.nonHandleFlag == '2'}">
								<td id="cardLogicalId" class="table_list_tr_col_data_block"
									style="width: 130px"><font color="fuchsia"> <b>${rs.cardLogicalId}</b>
								</font></td>
								<td id="receiptId" class="table_list_tr_col_data_block"
									style="width: 180px"><font color="fuchsia"> <b>${rs.businessReceiptId}</b>
								</font></td>
								<td id="identityType" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="fuchsia"> <b>${rs.identityTypeText}</b>
								</font></td>
								<td id="identityId" class="table_list_tr_col_data_block"
									style="width: 130px"><font color="fuchsia"> <b>${rs.identityId}</b>
								</font></td>
								<td id="cardMainId" class="table_list_tr_col_data_block"
									style="width: 90px"><font color="fuchsia"> <b>${rs.cardMainText}</b>
								</font></td>
								<td id="cardSubId" class="table_list_tr_col_data_block"
									style="width: 90px"><font color="fuchsia"> <b>${rs.cardSubText}</b>
								</font></td>
								<td id="applyName" class="table_list_tr_col_data_block"
									style="width: 90px"><font color="fuchsia"> <b>${rs.applyName}</b>
								</font></td>
								<td id="returnBala" class="table_list_tr_col_data_block"
									style="width: 110px"><font color="fuchsia"> <b>${rs.returnBala}</b>
								</font></td>
								<td id="actualReturnBala" class="table_list_tr_col_data_block"
									style="width: 110px"></td>
								<td id="applyDatetime" class="table_list_tr_col_data_block"
									style="width: 130px"><font color="fuchsia"> <b>${rs.applyDatetime}</b>
								</font></td>
								<td id="hdlFlag" class="table_list_tr_col_data_block"
									style="width: 150px"><font color="fuchsia"> <b>${rs.hdlText}</b>
								</font></td>
								<td id="lineId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="fuchsia"> <b>${rs.lineText}</b>
								</font></td>
								<td id="stationId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="fuchsia"> <b>${rs.stationText}</b>
								</font></td>

								<td id="returnLineId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="fuchsia"> <b>${rs.returnLineText}</b>
								</font></td>
								<td id="returnStationId" class="table_list_tr_col_data_block"
									style="width: 80px"><font color="fuchsia"> <b>${rs.returnStationText}</b>
								</font></td>
								<td id="remark" class="table_list_tr_col_data_block"
									style="width: 250px"><font color="fuchsia"> <b>${rs.remark}</b>
								</font></td>
							</c:when>
							<c:otherwise>
								<td id="cardLogicalId" class="table_list_tr_col_data_block"
									style="width: 130px">${rs.cardLogicalId}</td>
								<td id="receiptId" class="table_list_tr_col_data_block"
									style="width: 180px">${rs.businessReceiptId}</td>
								<td id="identityType" class="table_list_tr_col_data_block"
									style="width: 80px">${rs.identityTypeText}</td>
								<td id="identityId" class="table_list_tr_col_data_block"
									style="width: 130px">${rs.identityId}</td>
								<td id="cardMainId" class="table_list_tr_col_data_block"
									style="width: 90px">${rs.cardMainText}</td>
								<td id="cardSubId" class="table_list_tr_col_data_block"
									style="width: 90px">${rs.cardSubText}</td>
								<td id="applyName" class="table_list_tr_col_data_block"
									style="width: 90px">${rs.applyName}</td>
								<td id="returnBala" class="table_list_tr_col_data_block"
									style="width: 110px">${rs.returnBala}</td>
                                <c:choose>
                                    <c:when test="${rs.displayFlag == '1'}">
                                        <td id="actualReturnBala" class="table_list_tr_col_data_block"
                                            style="width: 110px">${rs.actualReturnBala}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td id="actualReturnBala" class="table_list_tr_col_data_block"
                                            style="width: 110px"></td>
                                    </c:otherwise>
                                </c:choose>
								<td id="applyDatetime" class="table_list_tr_col_data_block"
									style="width: 130px">${rs.applyDatetime}</td>
								<td id="hdlFlag" class="table_list_tr_col_data_block"
									style="width: 150px">${rs.hdlText}</td>
								<td id="lineId" class="table_list_tr_col_data_block"
									style="width: 80px">${rs.lineText}</td>
								<td id="stationId" class="table_list_tr_col_data_block"
									style="width: 80px">${rs.stationText}</td>

								<td id="returnLineId" class="table_list_tr_col_data_block"
									style="width: 80px">${rs.returnLineText}</td>
								<td id="returnStationId" class="table_list_tr_col_data_block"
									style="width: 80px">${rs.returnStationText}</td>
									<td id="remark" class="table_list_tr_col_data_block"
									style="width: 250px">${rs.remark}</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<FORM method="post" name="detailOp" id="detailOp"
		action="SignCardReturnQuery">
		<input type="hidden" id="d_balaCheckFlag" name="d_balaCheckFlag"
			value="${balaCheckFlag}" />
		<!-- 表头 通用模板 -->
		<!--导出全部参数 -->
<!--             <input type="hidden" name="expAllFields" id="d_expAllFields" value="CARD_LOGICAL_ID,BUSINESS_RECEIPT_ID,IDENTITY_TYPE,IDENTITY_ID,CARD_MAIN_ID,CARD_SUB_ID,APPLY_NAME,RETURN_BALA,ACTUAL_RETURN_BALA,APPLY_DATETIME,HDL_FLAG,LINE_ID,STATION_ID,RETURN_LINE_ID,RETURN_STATION_ID,REMARK" /> -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="getCardLogicalId,getBusinessReceiptId,getIdentityTypeText,getIdentityId,getCardMainText,getCardSubText,getApplyName,getReturnBala,getActualReturnBala,getApplyDatetime,getHdlText,getLineText,getStationText,getReturnLineText,getReturnStationText,getRemark" />
			<input type="hidden" name="expFileName" id="d_expFileName" value="记名卡退款退卡查询.xlsx" />
			<input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
			<input type="hidden" name="methodUrl" id="d_methodUrl" value="/SignCardReturnQueryExportAll" />
            <c:set var="divideShow" scope="request" value="1"/>
		<c:set var="divideShow" scope="request" value="1" />
		<c:import
			url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
		<c:import
			url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
		<c:set var="print" scope="request" value="1" />
		<c:set var="export" scope="request" value="1" />
		<c:set var="expAll" scope="request" value="1"/>
		<c:set var="btNext" scope="request" value="1" />
		<c:set var="btNextEnd" scope="request" value="1" />
		<c:set var="btBack" scope="request" value="1" />
		<c:set var="btBackEnd" scope="request" value="1" />
		<c:set var="btGoPage" scope="request" value="1"/>
		<c:set var="clickMethod" scope="request"
			value="btnClick('detailOp','clearStart','detail','','clearStartHead');" />
		<c:import
			url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
		<br />
	</FORM>

</body>
</html>
