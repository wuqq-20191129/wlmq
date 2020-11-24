<%--
    Document   : ticketStorageOutDistributeOutDetail
    Created on : 2017-10-13
    Author     : zhongziqi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>出库单明细</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="
            initDocument('detailOp', 'detail');
            setListViewDefaultValue('detailOp', 'clearStart');
            setTableRowBackgroundBlock('DataTable')">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">出库单明细
                </td>
            </tr>
        </table>
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" style="width :2200px">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                       	<td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">票区</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">面值(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">有效期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 110px">进出站限制模式</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">进站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">进站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">出站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">出站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="13"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">出库数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">详细位置</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="15"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">盒号起</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="16"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">盒号止</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="17" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width: 130px">逻辑卡号起</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="18" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 130px">逻辑卡号止</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="19" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 130px">配票原因</td>
                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData" style="width :2200px">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="clickResultRow('detailOp', this, 'detail');"
                            id="${rs.startLogicalId}" >
                            <td  id="waterNo" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.waterNo}
                            </td>
                            <td  id="billNo" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.billNo}
                            </td>
                            <td  id="storageId" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.storageName}
                            </td>
                            <td  id="areaId" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.areaName}
                            </td>
                            <td  id="icMainType" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.icMainTypeName}
                            </td>
                            <td  id="icSubType" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.icSubTypeName}
                            </td>
                            <td  id="cardMoney" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.cardMoney}
                            </td>
                            <td  id="validDate" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.validDate}
                            </td>
                            <td  id="mode" class="table_list_tr_col_data_block" style="width:110px">
                                ${rs.modelName}
                            </td>
                            <td  id="lineId" class="table_list_tr_col_data_block"style="width: 90px">
                                ${rs.lineName}
                            </td>
                            <td  id="stationId" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.stationName}
                            </td>
                            <td  id="exitLineId" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.exitLineName}
                            </td>
                            <td  id="exitStationId" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.exitStationName}
                            </td>
                            <td  id="outNum" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.outNum}
                            </td>
                            <td  id="detailPlace" class="table_list_tr_col_data_block" style="width:120px">
                                ${rs.detailPlace}
                            </td>
                            <td  id="startBoxId" class="table_list_tr_col_data_block" style="width:120px">
                                ${rs.startBoxId}
                            </td>
                            <td  id="endBoxId" class="table_list_tr_col_data_block" style="width:120px">
                                ${rs.endBoxId}
                            </td>
                            <td  id="startLogicalId" class="table_list_tr_col_data_block" style="width:130px">
                                ${rs.startLogicalId}
                            </td>
                            <td  id="endLogicalId" class="table_list_tr_col_data_block" style="width:130px">
                                ${rs.endLogicalId}
                            </td>
                            <td  id="reasonId" class="table_list_tr_col_data_block" style="width:130px">
                                ${rs.reasonName}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageOutBorrowManage" >
            <c:set var="divideShow" scope="request" value="0"/>
            <c:set var="operTypeValue" scope="request" value="outBillDetail"/>
            <c:set var="queryConditionValue" scope="request" value="${QueryCondition}"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr>

                    </tr>
                </table>
            </div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="exportBill" scope="request" value="1"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_out_commen',['p_bill_no'],['queryCondition'],800,500)"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>