<%--
    Document   : ticket_card_deal_his
    Created on : 2017-6-8
    Author     : zhongziqi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易清单查询</title>
<script language="javascript" type="text/javascript" charset="utf-8"
	src="js/common_form.js"></script>
<script language="javascript" type="text/javascript" charset="utf-8"
	src="js/Validator.js"></script>
<script language="JavaScript" src="js/CalendarPop.js"></script>
<!-- <script src="js/jquery-3.2.1.min.js"></script> -->
<link rel="stylesheet" type="text/css" href="css/frame/simple.css"
	title="Style" />
<script language="javascript">
	function selCheck() {
		var checkBtn = document.getElementById('balaCheck');
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

		//alert(document.getElementById('balaCheckFlag').value);
		if (document.getElementById('balaCheckFlag').value == "true") {
			document.getElementById('balaCheck').value = "1";
			document.getElementById('balaCheck').checked = true;
		}
		//alert(document.getElementById('balaCheck').checked);
		if (document.getElementById('balaCheck').checked) {
			document.getElementById('q_rtnOper').disabled = false;
			document.getElementById('q_rtnBala').disabled = false;
		}
		//            alert(document.getElementById('q_rtnBala').value);
		if (document.getElementById('q_rtnBala').value == 'null')
			document.getElementById('q_rtnBala').value = "0";

	}
</script>

</head>
<body
	onload="
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');

            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            formInit();
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

	<table class="table_title">
		<tr align="center" class="trTitle">
			<td colspan="4">交易清单查询 储值卡余额差异查询</td>
		</tr>
	</table>

	<!-- 表头 通用模板 -->

	<c:set var="pTitleName" scope="request" value="查询" />
	<c:set var="pTitleWidth" scope="request" value="50" />
	<c:import
		url="/jsp/common/common_template.jsp?template_name=common_table_title" />

	<form method="post" name="queryOp" id="queryOp"
		action="CardCheckResult">
		<!-- 页面用到的变量 通用模板 -->

		<c:set var="divideShow" scope="request" value="1" />
		<c:import
			url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

		<table class="table_edit">
			<tr class="table_edit_tr">
				<td width="10%">差异金额(元):</td>
				<td width="25%" colspan="2"><input type="hidden"
					id="balaCheckFlag" name="balaCheckFlag" value="${balaCheckFlag}" />
					<input type="checkbox" id="balaCheck" name="balaCheck" value="0"
					onclick="selCheck();" /> <select id="q_rtnOper" name="q_rtnOper"
					disabled="true">
						<option value="1">&gt;</option>
						<option value="2">&gt;=</option>
						<option value="3">=</option>
						<option value="4">&lt;=</option>
						<option value="5">&lt;</option>
				</select> <input type="text" id="q_rtnBala" name="q_rtnBala" width="50"
					disabled="true" dataType="Double" msg="差异金额必须为实数" value="0" /></td>
				<td class="table_edit_tr_td_label">逻辑卡号:</td>
				<td class="table_edit_tr_td_input"><input type="text"
					name="q_tkLogicNo" id="q_tkLogicNo" size="20" maxlength="20"
					require="false" dataType="Require|integer|LimitB" min="16" max="20"
					msg="逻辑卡号必填,格式应为数字字符(至少16位)。" /></td>
				<td class="table_edit_tr_td_label">票卡主类型:</td>
				<td class="table_edit_tr_td_input"><select id="q_cardMainID"
					name="q_cardMainID"
					onchange="setSelectValues('queryOp', 'q_cardMainID', 'q_cardSubID', 'commonVariable1');"
					style="width: 140px">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
				</select></td>
				<td class="table_edit_tr_td_label">票卡子类型:</td>
				<td class="table_edit_tr_td_input"><select id="q_cardSubID"
					name="q_cardSubID" style="width: 140px">
						<c:import
							url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
				</select> <c:set var="pVarName" scope="request" value="commonVariable1" /> <c:import
						url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
				</td>
			</tr>
			<tr class="table_edit_tr">
				<td class="table_edit_tr_td_label" style="text-algin: left"><c:import
						url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
					<c:set var="query" scope="request" value="1" /> <c:set
						var="addClickMethod" scope="request"
						value="setControlNames('queryOp','q_rtnOper#q_rtnBala#q_tkLogicNo#q_cardMainID#q_cardSubID');
                               setLineCardNames('queryOp','','','','q_cardMainID','q_cardSubID','commonVariable1');" />
					<c:set var="clickMethod" scope="request"
						value="btnClick('queryOp','clearStart','detail');" /> <c:import
						url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
				</td>

			</tr>
		</table>

	</form>

	<!-- 表头 通用模板 -->
	<c:set var="pTitleName" scope="request" value="列表" />
	<c:set var="pTitleWidth" scope="request" value="50" />
	<c:import
		url="/jsp/common/common_template.jsp?template_name=common_table_title" />
	<div id="clearStartBlock" class="divForTableBlock">
		<div id="clearStartHead" class="divForTableBlockHead">
			<table class="table_list_block" id="DataTableHead">
				<!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
				<tr class="table_list_tr_head_block" id="ignore">
					<td class="table_list_tr_col_head_block"><input
						type="checkbox" name="rectNoAll" id="rectNoAll"
						onclick="selectAllRecord('detailOp','rectNoAll','rectNo','clearStart',0);" />
					</td>
					<td id="orderTd" style="width: 100px"
						class="table_list_tr_col_head_block" isDigit=true index="0"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">票卡子类型</td>
					<td id="orderTd" style="width: 150px"
						class="table_list_tr_col_head_block" isDigit=false index="1"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">逻辑卡号</td>
					<td id="orderTd" style="width: 100px"
						class="table_list_tr_col_head_block" isDigit=false index="2"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">充值总笔数</td>
					<td id="orderTd" style="width: 100px"
						class="table_list_tr_col_head_block" isDigit=false index="3"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">充值总金额</td>
					<td id="orderTd" style="width: 100px"
						class="table_list_tr_col_head_block" isDigit=false index="4"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">消费总笔数</td>
					<td id="orderTd" style="width: 90px"
						class="table_list_tr_col_head_block" isDigit=false index="5"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">消费总金额</td>
					<td id="orderTd" style="width: 90px"
						class="table_list_tr_col_head_block" isDigit=false index="6"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">系统余额</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=false index="7"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">卡余额</td>
					<td id="orderTd" style="width: 80px"
						class="table_list_tr_col_head_block" isDigit=false index="8"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">差异笔数</td>
					<td id="orderTd" style="width: 70px"
						class="table_list_tr_col_head_block" isDigit=false index="9"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">差异金额</td>
					<td id="orderTd" style="width: 100px"
						class="table_list_tr_col_head_block" isDigit=false index="10"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">清算流水号</td>
					<td id="orderTd" style="width: 70px"
						class="table_list_tr_col_head_block" isDigit=false index="11"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">预赋金额</td>
					<td id="orderTd" style="width: 66px"
						class="table_list_tr_col_head_block" isDigit=false index="12"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">充值序号</td>
					<td id="orderTd" style="width: 70px"
						class="table_list_tr_col_head_block" isDigit=false index="13"
						sortedby="asc" onclick="sortForTableBlock('clearStart');">消费序号</td>
				</tr>
			</table>

		</div>
		<div id="clearStart" class="divForTableBlockData">
			<table class="table_list_block" id="DataTable">
				<c:forEach items="${ResultSet}" var="rs">

					<tr class="table_list_tr_data"
						onMouseOver="overResultRow('detailOp', this);"
						onMouseOut="outResultRow('detailOp', this);"
						onclick="clickOneResultRow('detailOp', this, 'detail','rectNo');
			setPageControl('detailOp');"
						id="${rs.cardLogicalId}">
						<td id="rectNo1" class="table_list_tr_col_data_block"><input
							type="checkbox" name="rectNo"
							onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
							value="${rs.cardLogicalId}#${rs.balanceWaterNo}"> </input></td>

						<td id="cardSubId" class="table_list_tr_col_data_block"
							style="width: 100px">${rs.cardSubId}</td>
						<td id="cardLogicalId" class="table_list_tr_col_data_block"
							style="width: 150px">${rs.cardLogicalId}</td>
						<td id="totalChargeNum" class="table_list_tr_col_data_block"
							style="width: 100px">${rs.totalChargeNum}</td>
						<td id="totalChargeFee" class="table_list_tr_col_data_block"
							style="width: 100px">${rs.totalChargeFee}</td>
						<td id="totalConsumeNum" class="table_list_tr_col_data_block"
							style="width: 100px">${rs.totalConsumeNum}</td>
						<td id="totalConsumeFee" class="table_list_tr_col_data_block"
							style="width: 90px">${rs.totalConsumeFee}</td>
						<td id="systemBalanceFee" class="table_list_tr_col_data_block"
							style="width: 90px">${rs.systemBalanceFee}</td>
						<td id="cardBalanceFee" class="table_list_tr_col_data_block"
							style="width: 80px">${rs.cardBalanceFee}</td>
						<td id="diffCardSysNum" class="table_list_tr_col_data_block"
							style="width: 80px">${rs.diffCardSysNum}</td>
						<td id="diffCardSysFee" class="table_list_tr_col_data_block"
							style="width: 70px">${rs.diffCardSysFee}</td>
						<td id="balanceWaterNo" class="table_list_tr_col_data_block"
							style="width: 100px">${rs.balanceWaterNo}</td>
						<td id="cardMoney" class="table_list_tr_col_data_block"
							style="width: 70px">${rs.cardMoney}</td>
						<td id="cardChargeSeq" class="table_list_tr_col_data_block"
							style="width: 70px">${rs.cardChargeSeq}</td>
						<td id="cardConsumeSeq" class="table_list_tr_col_data_block"
							style="width: 70px">${rs.cardConsumeSeq}</td>
					</tr>

				</c:forEach>

			</table>
		</div>
	</div>

	<FORM method="post" name="detailOp" id="detailOp"
		action="CardCheckResult">
		<!-- 		<input type="hidden" name="expAllTableHeadWidth" -->
		<!-- 			id="expAllTableHeadWidth" value="" /> <input type="hidden" -->
		<!-- 			name="expAllTableHeadName" id="expAllTableHeadName" value="" />  -->
<!-- 		<input type="hidden" name="expAllFields" id="expAllFields" value="CARD_SUB_ID,CARD_LOGICAL_ID,TOTAL_CHARGE_NUM,TOTAL_CHARGE_FEE,TOTAL_CONSUME_NUM,TOTAL_CONSUME_FEE,SYSTEM_BALANCE_FEE,CARD_BALANCE_FEE,DIFF_CARD_SYS_NUM,DIFF_CARD_SYS_FEE,BALANCE_WATER_NO,CARD_MONEY,CARD_CHARGE_SEQ,CARD_CONSUME_SEQ" /> -->
		<input type="hidden" name="expAllFields" id="expAllFields" value="getCardSubId,getCardLogicalId,getTotalChargeNum,getTotalChargeFee,getTotalConsumeNum,getTotalConsumeFee,getSystemBalanceFee,getCardBalanceFee,getDiffCardSysNum,getDiffCardSysFee,getBalanceWaterNo,getCardMoney,getCardChargeSeq,getCardConsumeSeq" />
		<input type="hidden" name="expFileName" id="expFileName"
			value="储值卡余额差异查询.xlsx" /> <input type="hidden" name="divId"
			id="divId" value="clearStartHead" /> <input type="hidden"
			name="methodUrl" id="methodUrl" value="/CardCheckResultExportAll" />
		<c:set var="divideShow" scope="request" value="1" />
		<c:import
			url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

		<c:import
			url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
		<c:set var="inBlack" scope="request" value="1" />
		<c:set var="print" scope="request" value="1" />
		<c:set var="export" scope="request" value="1" />
		<c:set var="expAll" scope="request" value="1" />
		<c:set var="btNext" scope="request" value="1" />
		<c:set var="btNextEnd" scope="request" value="1" />
		<c:set var="btBack" scope="request" value="1" />
		<c:set var="btBackEnd" scope="request" value="1" />
		<c:set var="btGoPage" scope="request" value="1"/>
		<c:set var="clickMethod" scope="request"
			value="btnClick('detailOp','clearStart','detail','','clearStartHead');" />
		<%-- 		<c:set var="ajaxMethod" scope="request" value="ajaxMethod();" /> --%>
		<c:import
			url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
		<br />
	</FORM>
	<!-- 状态栏 通用模板 -->
	<c:import
		url="/jsp/common/common_template.jsp?template_name=common_status_table" />
</body>

</html>
