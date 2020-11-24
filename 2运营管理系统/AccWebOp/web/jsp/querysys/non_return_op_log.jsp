<%--
    Document   : NonReturnOpLog
    Created on : 2017-6-21
    Author     : zhongziqi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>非即时退款操作日志</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="preLoadVal('q_beginDatetime', 'q_endDatetime');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    日志查询 非即时退款操作日志
                </td>
            </tr>
        </table>


        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="NonReturnOpLog">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">业务凭证号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_businessReceiptId" id="q_businessReceiptId" size="30"  maxLength="25" require="false"   max = "25" dataType="NumAndMinus"  msg="业务凭证号最大25位,仅能输入数字和-(减号)"/>
                    </td>
                    <td class="table_edit_tr_td_label">印刻号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_cardPrintId" id="q_cardPrintId" size="20"  maxLength="16" require="false"  dataType="integer"  max = "16" msg="车票id为最大16位的数字"/>
                    </td>
                    <td class="table_edit_tr_td_label">操作员:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_operatorId" name = "q_operatorId">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_operatorName" />
                        </select>
                    </td>

                </tr>
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

                    <td class="table_edit_tr_td_label">操作类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_oprationType" name = "q_oprationType">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_operationType" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_businessReceiptId#q_cardPrintId#q_beginDatetime#q_endDatetime#q_operatorId#q_oprationType');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 1800px">
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore" >
                        <td id="orderTd"  style="width: 80px"  class="table_list_tr_col_head_block"  isDigit=true index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >操作员</td>
                        <td id="orderTd"  style="width: 150px"  class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >操作时间</td>
                        <td id="orderTd"  style="width: 80px"  class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >操作类型</td>
                        <td id="orderTd"  style="width: 180px"  class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >业务凭证号</td>
                        <td id="orderTd"  style="width: 150px"  class="table_list_tr_col_head_block"  isDigit=true index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >印刻号</td>
                        <td id="orderTd"  style="width: 80px"  class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡余额</td>
                        <td id="orderTd"  style="width: 80px"  class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >是否折损</td>
                        <td id="orderTd"  style="width: 80px"  class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >押金</td>
                        <td id="orderTd"  style="width: 80px"  class="table_list_tr_col_head_block"  isDigit=true index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >欠乘车费</td>
                        <td id="orderTd"  style="width: 200px"  class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >扣款原因</td>
                        <td id="orderTd"  style="width: 80px"  class="table_list_tr_col_head_block"  isDigit=true index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >退款金额</td>
                        <td id="orderTd"  style="width: 160px"  class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >处理状态</td>
                        <td id="orderTd"  style="width: 300px"  class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >备注</td>
                    </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData" style="width: 1800px">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="clickResultRow('detailOp', this, '');
                                    setPageControl('detailOp');"
                            id="${rs.receiptId}#${rs.operateTime}">
                            <td  id="operatorId" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.operatorName}
                            </td>
                            <td  id="operateTime" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.operateTime}
                            </td>
                            <td  id="operateType" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.operateTypeText}
                            </td>
                            <td  id="receiptId" class="table_list_tr_col_data_block" style="width: 180px">
                                ${rs.receiptId}
                            </td>
                            <td  id="cardPrintId" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.cardPrintId}
                            </td>
                            <td  id="cardBalanceFee" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.cardBalanceFee}
                            </td>
                             <td  id="isBroken" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.brokenText}
                            </td>
                             <td  id="depositFee" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.depositFee}
                            </td>
                             <td  id="penaltyFee" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.penaltyFee}
                            </td>
                             <td  id="penaltyReason" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.penaltyReasonText}
                            </td>
                             <td  id="actualReturnBala" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.actualReturnBala}
                            </td>
                             <td  id="hdlFlag" class="table_list_tr_col_data_block" style="width: 160px">
                                ${rs.hdlFlagText}
                            </td>
                             <td  id="remark" class="table_list_tr_col_data_block" style="width: 300px">
                                ${rs.remark}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <FORM method="post" name="detailOp" id="detailOp" action="NonReturnOpLog" >
            <!-- 表头 通用模板 -->
			<!--导出全部参数 -->
<!--             <input type="hidden" name="expAllFields" id="d_expAllFields" value="OPERATOR_ID,OPERATE_TIME,OPERATE_TYPE,RECEIPT_ID,CARD_PRINT_ID,CARD_BALANCE_FEE,IS_BROKEN,DEPOSIT_FEE,PENALTY_FEE,PENALTY_REASON,ACTUAL_RETURN_BALA,HDL_FLAG,REMARK" /> -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="getOperatorName,getOperateTime,getOperateTypeText,getReceiptId,getCardPrintId,getCardBalanceFee,getBrokenText,getDepositFee,getPenaltyFee,getPenaltyReasonText,getActualReturnBala,getHdlFlagText,getRemark" />
			<input type="hidden" name="expFileName" id="d_expFileName" value="非即时退款操作日志.xlsx" />
			<input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
			<input type="hidden" name="methodUrl" id="d_methodUrl" value="/NonReturnOpLogExportAll" />
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
