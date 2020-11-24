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
         <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
   <body onload="preLoadVal('q_beginDatetime', 'q_endDatetime',80);
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')"">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    交易清单查询 票卡交易记录查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="TicketCardDealHis">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
              <td class="table_edit_tr_td_label">交易日期: </td>
              	 <td class="table_edit_tr_td_input">
                        <input type="text" id="q_beginDatetime" name="q_beginDatetime"   size="10"  dataType="ICCSDate"  msg="开始日期格式为[yyyy-mm-dd]"/>
                        <a href="javascript:openCalenderDialogByID('q_beginDatetime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
               <td class="table_edit_tr_td_input">
                        <input type="text" id="q_endDatetime" name="q_endDatetime"   size="10"  dataType="ICCSDate|ThanDate" to="q_beginDatetime" msg="结束日期格式为[yyyy-mm-dd]且大于等于开始日期"/>
                        <a href="javascript:openCalenderDialogByID('q_endDatetime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label">逻辑卡号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_cardLogicalId" id="q_cardLogicalId" size="25"  maxLength="20" dataType="Require|integer|LimitB" min="16" max="20"   msg="逻辑卡号必填,格式应为数字字符(至少16位)。"/>
                    </td>
                   <td class="table_edit_tr_td_label">交易类型:</td>
                   <td class="table_edit_tr_td_input">
                            <select id="q_dealType" name="q_dealType"  >
                                 <option value="">=请选择=</option>
                                    <c:forEach items="${dealTypes}" var="dealType">
                                        <c:choose>
                                            <c:when test="${dealType.codeText == ''}">
                                                <option value="${dealType.code}">${dealType.code}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${dealType.code}">${dealType.codeText}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                            </select>
                        </td>
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_beginDatetime#q_endDatetime#q_cardLogicalId#q_dealType');"/>
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
   <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >
                <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                <tr  class="table_list_tr_head_block" id="ignore">
                    <td id="orderTd"  style="width: 130px"  class="table_list_tr_col_head_block"  isDigit=true index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >逻辑卡号</td>
                    <td id="orderTd"  style="width: 130px"  class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >交易时间</td>
                    <td id="orderTd"  style="width: 100px"  class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >交易类型</td>
                    <td id="orderTd"  style="width: 66px"  class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >金额(元)</td>
                    <td id="orderTd"  style="width: 66px"  class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >余额(元)</td>
                    <td id="orderTd"  style="width: 90px"  class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                    <td id="orderTd"  style="width: 90px"  class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                    <td id="orderTd"  style="width: 80px"  class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >线路</td>
                    <td id="orderTd"  style="width: 80px"  class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >站点</td>
                    <td id="orderTd"  style="width: 70px"  class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备类型</td>
                    <td id="orderTd"  style="width: 66px"  class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备代码</td>
                    <td id="orderTd"   style="width: 70px"  class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >操作员代码</td>
                </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                <c:forEach items="${ResultSet}" var="rs">

                    <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                        onMouseOut="outResultRow('detailOp', this);"
                        onclick="clickResultRow('detailOp', this, '');
			setPageControl('detailOp');"
                         id="${rs.logiId}}#${rs.dealTime}}" >


                        <td  id="logiId" class="table_list_tr_col_data_block" style="width: 130px">
                            ${rs.logiId}
                        </td>
                        <td  id="dealTime" class="table_list_tr_col_data_block" style="width: 130px">
                            ${rs.dealTime}
                        </td>
                        <td  id="dealType" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.dealType}
                        </td>
                        <td  id="dealAmount" class="table_list_tr_col_data_block" style="width: 66px">
                            ${rs.dealAmount}
                        </td>
                        <td  id="balance" class="table_list_tr_col_data_block" style="width: 66px">
                            ${rs.balance}
                        </td>
                        <td  id="cardMainCode" class="table_list_tr_col_data_block" style="width: 90px">
                            ${rs.cardMainCode}
                        </td>
                        <td  id="cardSubCode" class="table_list_tr_col_data_block" style="width: 90px">
                            ${rs.cardSubCode}
                        </td>
                          <td  id="lineId" class="table_list_tr_col_data_block" style="width: 80px">
                            ${rs.lineId}
                        </td>
                        <td  id="stationId" class="table_list_tr_col_data_block" style="width: 80px">
                            ${rs.stationId}
                        </td>  <td  id="devTypeId" class="table_list_tr_col_data_block" style="width: 70px">
                            ${rs.devTypeId}
                        </td>
                        <td  id="deviceId" class="table_list_tr_col_data_block" style="width: 66px">
                            ${rs.deviceId}
                        </td>
                         <td  id="operatorId" class="table_list_tr_col_data_block" style="width: 70px" >
                            ${rs.operatorId}
                        </td>
                    </tr>

                </c:forEach>

            </table>
            </div>
        </div>

        <FORM method="post" name="detailOp" id="detailOp" action="TicketCardDealHis" >
            <!--导出全部参数 -->
<!--             <input type="hidden" name="expAllFields" id="d_expAllFields" value="LOGI_ID,DEAL_TIME,DEAL_TYPE,DEAL_AMOUNT,BALANCE,CARD_MAIN_CODE,CARD_SUB_CODE,LINE_ID,STATION_ID,DEV_TYPE_ID,DEVICE_ID,OPERATOR_ID" /> -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="getLogiId,getDealTime,getDealType,getDealAmount,getBalance,getCardMainCode,getCardSubCode,getLineId,getStationId,getDevTypeId,getDeviceId,getOperatorId" />
			<input type="hidden" name="expFileName" id="d_expFileName" value="票卡交易记录查询.xlsx" />
			<input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
			<input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketCardDealHisExportAll" />
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
