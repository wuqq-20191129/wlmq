<%--
    Document   : bom_sign_card_loss_apply_query
    Created on : 2017-6-19
    Author     : zhongziqi
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BOM记名卡挂失申请查询</title>
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
                    客服明细查询 BOM记名卡挂失申请查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="BomSignCardLossApplyQuery">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">业务凭证号：</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_businessReceiptId" id="q_businessReceiptId" size="30"  maxLength="25" require="false"   dataType="NumAndMinus"  max = "25" msg="业务凭证号最大25位,仅能输入数字和-(减号)"/>
                    </td>
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
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">证件类型：</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_identifyType" name = "q_identifyType">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_identifyType" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">证件号码：</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_identityId" id="q_identityId" size="25"  maxLength="18" require="false"   max = "18" dataType = "NumAndEng" msg="证件号最大18位，仅允许输入数字或字母"/>
                    </td>
                    <td class="table_edit_tr_td_label">业务类型</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_busnissType" name = "q_busnissType">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_busnissType" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_businessReceiptId#q_beginDatetime#q_endDatetime#q_identifyType#q_identityId#q_busnissType');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead" style="width: 1450px">
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=true index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                        <td id="orderTd"  style="width: 170px"  class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >业务凭证号</td>
                        <td id="orderTd"  style="width: 130px"  class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >逻辑卡号</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >创建时间</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >业务类型</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >证件类型</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >证件号码</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >申请线路</td>
                        <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >申请车站</td>
                    </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData" style="width: 1450px">
                <table class="table_list_block" id="DataTable"  >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="clickResultRow('detailOp', this, '');
                                    setPageControl('detailOp');"
                            id="${rs.waterNo}">
                            <td  id="waterNo" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.waterNo}
                            </td>
                            <td  id="applyBill" class="table_list_tr_col_data_block" style="width: 170px">
                                ${rs.applyBill}
                            </td>
                            <td  id="applyBill" class="table_list_tr_col_data_block" style="width: 130px">
                                    ${rs.cardLogicalId}
                            </td>
                            <td  id="createTime" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.createTime}
                            </td>
                            <td  id="busnissType" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.busnissTypeText}
                            </td>
                            <td  id="identifyType" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.identifyTypeText}
                            </td>
                            <td  id="identityId" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.identityId}
                            </td>
                            <td  id="identifyType" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.cardMainName}
                            </td>
                            <td  id="identityId" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.cardSubName}
                            </td>
                            <td  id="identifyType" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.lineName}
                            </td>
                            <td  id="identityId" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.stationName}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <FORM method="post" name="detailOp" id="detailOp" action="BomSignCardLossApplyQuery" >
            <!-- 表头 通用模板 -->
			<!--导出全部参数 -->
<!--             <input type="hidden" name="expAllFields" id="d_expAllFields" value="WATER_NO,APPLY_BILL,CREATE_TIME,BUSNISS_TYPE,IDENTIFY_TYPE,IDENTITY_ID,RETURN_LINE_ID,RETURN_STATION_ID" /> -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="getWaterNo,getApplyBill,getCreateTime,getBusnissTypeText,getIdentifyTypeText,getIdentityId,getReturnLineName,getReturnStationName" />
			<input type="hidden" name="expFileName" id="d_expFileName" value="BOM记名卡挂失申请.xlsx" />
			<input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
			<input type="hidden" name="methodUrl" id="d_methodUrl" value="/BomSignCardLossApplyQueryExportAll" />
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
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
