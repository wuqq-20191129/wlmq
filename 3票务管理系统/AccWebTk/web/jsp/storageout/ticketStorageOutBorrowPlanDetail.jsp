<%--
    Document   : ticketStorageOutBorrowPlanDetail
    Created on : 2017-8-16
    Author     : zhongziqi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
    <title>借票单明细</title>
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
        setPrimaryKeys('detailOp', 'd_waterNo#d_billNo#d_lineId#d_stationId#d_exitLineId#d_exitStationId#d_lendQuantity#d_startLogicalId#d_endLogicalId');
        setDetailBillNo('detailOp', 'd_billNo', 'queryCondition');
        controlByRecordFlagForInit('detailOp', ['add'], true);
        getCurrentDate('h_curDate');
        setTableRowBackgroundBlock('DataTable')">
    <!--<body >-->
    <table  class="table_title">
        <tr align="center" class="trTitle">
            <td colspan="4">借票单明细
            </td>
        </tr>
    </table>

    <!-- 表头 通用模板 -->
    <c:set var="pTitleName" scope="request" value="列表"/>
    <c:set var="pTitleWidth" scope="request" value="50"/>
    <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
    <div id="clearStartBlock" class="divForTableBlock">
        <div id="clearStartHead" class="divForTableBlockHead" style="width :1700px">
            <table class="table_list_block" id="DataTableHead" >

                <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                <tr  class="table_list_tr_head_block" id="ignore">
                    <td   class="table_list_tr_col_head_block">
                        <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                controlsByFlag('detailOp', ['del']);
                                controlsByFlagWithoutCk('detailOp', ['modify']);
                                controlByRecordFlagForInit('detailOp', ['add'], true);"/>
                    </td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px" >借票单号</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >单据状态</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >仓库</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票区</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >面值(分/次)</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >有效期</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:110px">进出站限制模式</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');">进站线路</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');">进站车站</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站线路</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站车站</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');">借票数量</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="16" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:120px" >逻辑卡号起</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="17" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width:120px" >逻辑卡号止</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="18" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:250px">备注</td>
                </tr>
            </table>
        </div>

        <div id="clearStart"  class="divForTableBlockData" style="width :1700px">
            <table class="table_list_block" id="DataTable" >
                <c:forEach items="${ResultSet}" var="rs">
                    <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                        onMouseOut="outResultRow('detailOp', this);"
                        onclick="setSelectValuesByRowPropertyName('detailOp', 'd_icSubType', 'commonVariable', 'icMainType');
                                setSelectValuesByRowPropertyName('detailOp', 'd_areaId', 'commonVariable1', 'storageId');
                                setSelectValuesByRowPropertyName('detailOp', 'd_lineId', 'commonVariable2', 'storageId');
                                setSelectValuesByRowPropertyName('detailOp', 'd_stationId', 'commonVariable3', 'lineId');
                                setSelectValuesByRowPropertyName('detailOp', 'd_exitLineId', 'commonVariable4', 'storageId');
                                setSelectValuesByRowPropertyName('detailOp', 'd_exitStationId', 'commonVariable5', 'exitLineId');
                                setSelectValuesByMapping('detailOp', 'd_icMainType', 'd_areaId', 'commonVariable1', ['12', '1', '2', '40', '7', '8', '10'],
                                        [
                                            ['01', '02', '03', '04', '05', '06', '07'],
                                            ['01', '02', '03', '04', '05', '06'],
                                            ['01', '02', '03', '04', '05', '06'],
                                            ['01', '02', '03', '04', '05', '06'],
                                            ['01', '02', '03', '04', '05', '06'],
                                            ['01', '02', '03', '04', '05', '06'],
                                            ['01', '02', '03', '04', '05', '06']
                                        ]
                                        );
                                clickResultRow('detailOp', this, 'detail');
                                controlRadios('detailOp', '${rs.lendQuantity}', '${rs.startLogicalId}', '${rs.endLogicalId}',
                                        ['d_select', 'd_lendQuantity', 'd_startLogicalId', 'd_endLogicalId']);
                                controlsByFlag('detailOp', ['del']);
                                controlsByFlagWithoutCk('detailOp', ['modify']);
                                controlByRecordFlagForInit('detailOp', ['add'], true);
                        "
                        id="${rs.billNo}#${rs.waterNo}" flag="${rs.recordFlag}" icMainType="${rs.icMainType}" storageId="${rs.storageId}"
                        lineId="${rs.lineId}" exitLineId="${rs.exitLineId}" >

                        <td id="rectNo1" class="table_list_tr_col_data_block" >
                            <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                    controlsByFlag('detailOp', ['del']);
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                    controlByRecordFlagForInit('detailOp', ['add'], true);"
                                   value="${rs.billNo}"  flag="${rs.recordFlag}" >
                            </input>
                        </td>
                        <td  id="waterNo" class="table_list_tr_col_data_block" >
                            ${rs.waterNo}
                        </td>
                        <td  id="billNo" class="table_list_tr_col_data_block" style="width:80px">
                            ${rs.billNo}
                        </td>
                        <td  id="recordFlag" class="table_list_tr_col_data_block">
                            ${rs.recordFlagName}
                        </td>
                        <td  id="storageId" class="table_list_tr_col_data_block">
                            ${rs.storageName}
                        </td>
                        <td  id="areaId" class="table_list_tr_col_data_block">
                            ${rs.areaName}
                        </td>
                        <td  id="icMainType" class="table_list_tr_col_data_block">
                            ${rs.icMainTypeName}
                        </td>
                        <td  id="icSubType" class="table_list_tr_col_data_block">
                            ${rs.icSubTypeName}
                        </td>
                        <td  id="cardMoney" class="table_list_tr_col_data_block">
                            ${rs.cardMoney}
                        </td>
                        <td  id="validDate" class="table_list_tr_col_data_block">
                            ${rs.validDate}
                        </td>
                        <td  id="mode" class="table_list_tr_col_data_block" style="width:110px">
                            ${rs.modelName}
                        </td>
                        <td  id="lineId" class="table_list_tr_col_data_block">
                            ${rs.lineName}
                        </td>
                        <td  id="stationId" class="table_list_tr_col_data_block">
                            ${rs.stationName}
                        </td>
                        <td  id="exitLineId" class="table_list_tr_col_data_block">
                            ${rs.exitLineName}
                        </td>
                        <td  id="exitStationId" class="table_list_tr_col_data_block">
                            ${rs.exitStationName}
                        </td>

                        <td  id="lendQuantity" class="table_list_tr_col_data_block">
                            ${rs.lendQuantity}
                        </td>
                        <td  id="startLogicalId" class="table_list_tr_col_data_block" style="width:120px">
                            ${rs.startLogicalId}
                        </td>
                        <td  id="endLogicalId" class="table_list_tr_col_data_block" style="width:120px">
                            ${rs.endLogicalId}
                        </td>
                        <td  id="remark" class="table_list_tr_col_data_block" style="width:250px">
                            ${rs.remark}
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
    <form method="post" name="detailOp" id="detailOp" action="ticketStorageOutBorrowManage" >
        <c:set var="divideShow" scope="request" value="0"/>
        <c:set var="operTypeValue" scope="request" value="planDetail"/>
        <c:set var="queryConditionValue" scope="request" value="${QueryCondition}"/>
        <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
        <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />
        <div id="detail"  class="divForTableDataDetail" >
            <table  class="table_edit_detail">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">流水号: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="d_waterNo" id="d_waterNo" size="10" maxlength="18" />
                    </td>
                    <td class="table_edit_tr_td_label">借票单: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="d_billNo" id="d_billNo" size="10" maxlength="12" />
                    </td>
                    <td class="table_edit_tr_td_label">仓库:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_storageId" name="d_storageId" dataType="Require" msg="仓库不能为空!"
                                onChange="
                                        setSelectValues('detailOp', 'd_storageId', 'd_areaId', 'commonVariable1');
                                        setSelectValues('detailOp', 'd_storageId', 'd_lineId', 'commonVariable2');
                                        setSelectValues('detailOp', 'd_lineId', 'd_stationId', 'commonVariable3');
                                        setSelectValues('detailOp', 'd_storageId', 'd_exitLineId', 'commonVariable4');
                                        setSelectValues('detailOp', 'd_exitLineId', 'd_exitStationId', 'commonVariable5');

                                        setSelectValuesByMapping('detailOp', 'd_icMainType', 'd_areaId', 'commonVariable1', ['12', '1', '2', '40', '7', '8', '10'],
                                                [
                                                    ['01', '02', '03', '04', '05', '06', '07'],
                                                    ['01', '02', '03', '04', '05', '06'],
                                                    ['01', '02', '03', '04', '05', '06'],
                                                    ['01', '02', '03', '04', '05', '06'],
                                                    ['01', '02', '03', '04', '05', '06'],
                                                    ['01', '02', '03', '04', '05', '06'],
                                                    ['01', '02', '03', '04', '05', '06']
                                                ]
                                                );
                                        controlByCardTypeAndAreaForBorrow();
                                        controlByCardTypeAndAreaForModeForBorrow();
                                        controlByAreaIdForBorrow();
                                        controlLineStationByMode('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');
                                        controlByRadioFoaBorrow();

                                ">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_icMainType"
                                name="d_icMainType" dataType="Require" msg="票卡主类型不能为空!"
                                onChange="setSelectValues('detailOp', 'd_icMainType', 'd_icSubType', 'commonVariable');
                                        setSelectValuesByMapping('detailOp', 'd_icMainType', 'd_areaId', 'commonVariable1', ['12', '1', '2', '40', '7', '8', '10'],
                                                [
                                                    ['01', '02', '03', '04', '05', '06', '07'],
                                                    ['01', '02', '03', '04', '05', '06'],
                                                    ['01', '02', '03', '04', '05', '06'],
                                                    ['01', '02', '03', '04', '05', '06'],
                                                    ['01', '02', '03', '04', '05', '06'],
                                                    ['01', '02', '03', '04', '05', '06'],
                                                    ['01', '02', '03', '04', '05', '06']
                                                ]
                                                );
                                        controlByCardTypeAndAreaForBorrow();
                                        controlByCardTypeAndAreaForModeForBorrow();

                                        controlByAreaIdForBorrow();
                                        controlLineStationByModeById('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');
                                        controlByRadioFoaBorrow();

                                ">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard_limit" />
                        </select>

                    </td>

                </tr>
                <tr class="table_edit_tr">

                    <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_icSubType"
                                name="d_icSubType" dataType="Require" msg="票卡子类型不能为空!"
                                >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                    </td>
                    <td class="table_edit_tr_td_label">票区:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_areaId" name="d_areaId" dataType="Require" msg="库存票区不能为空!"
                                onChange="
                                        controlByCardTypeAndAreaForBorrow();
                                        controlByCardTypeAndAreaForModeForBorrow();
                                        controlByAreaIdForBorrow();
                                        controlLineStationByModeById('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');
                                        controlByRadioFoaBorrow();
                                ">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone" />
                    </td>
                    <td class="table_edit_tr_td_label">面值(分/次):</td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text"name="d_cardMoney" id="d_cardMoney" size="3" value="0"
                               require="true" dataType="Integer" maxlength="5"
                               msg="面值应是数字" />
                        <select id="d_cardMoney1"
                                name="d_cardMoney1" dataType="false"
                                onChange="setCardMoneyById('d_cardMoney', 'd_cardMoney1')">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardmoney" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">有效期:</div></td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="d_validDate" id="d_validDate" value="" size="12" require="false"  dataType="Date|ThanDate" format="ymd"  to="h_curDate"
                               msg="有效期格式为(yyyy-mm-dd),且不得日期不得小于当天!" />
                        <a href="javascript:openCalenderDialogByID('d_validDate','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">进出站限制模式:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_mode" name="d_mode" require="false" msg="进出站限制模式不能为空"
                                onChange="controlLineStationByModeById('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mode" />
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label">进站线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_lineId" name="d_lineId" require="false" dataType="NotEmpty" msg="进站线路不能为空"
                                onChange="setSelectValues('detailOp', 'd_lineId', 'd_stationId', 'commonVariable3');">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable2"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage_line_serial" />
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">进站车站:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_stationId" name="d_stationId" require="false"  dataType="NotEmpty" msg="进站车站不能为空" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable3"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_iclinestation" />
                    </td>
                    <td class="table_edit_tr_td_label">出站线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_exitLineId"
                                name="d_exitLineId" require="false" dataType="NotEmpty" msg="出站线路不能为空"
                                onChange="setSelectValues('detailOp', 'd_exitLineId', 'd_exitStationId', 'commonVariable5');">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable4"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage_line_serial" />
                    </td>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label"><div align="right">出站车站:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_exitStationId" name="d_exitStationId" require="false" dataType="NotEmpty" msg="出站车站不能为空">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable5"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_iclinestation" />
                    </td>
                    <td class="table_edit_tr_td_label">
                        <input type="radio"
                               name="d_select" id="d_select" size="1"
                               onClick="controlByRadioFoaBorrow();"
                               value="0" />
                        借票数量:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text"
                               name="d_lendQuantity" id="d_lendQuantity" size="10"
                               require="false" maxlength="9" dataType="integer|Positive"
                               msg="借票数量为大于零的整数!" />
                    </td>
                    <td class="table_edit_tr_td_label">
                        <input type="radio"
                               name="d_select" id="d_select" size="1"
                               onClick="controlByRadioFoaBorrow();"
                               value="0" />
                        逻辑卡号起:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text"
                               name="d_startLogicalId" id="d_startLogicalId" size="20"
                               require="false" dataType="Number|Limit" min="16" max="20"
                               maxlength="20" msg="起始逻辑卡号应为16位-20位数字" /></td>
                    <td class="table_edit_tr_td_label">逻辑卡号止:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text"
                               name="d_endLogicalId" id="d_endLogicalId" size="20"
                               require="false" min="16" max="20"
                               dataType="SameLen|Number|CompareBigNum|Limit"
                               operator="GreaterThanEqual" to="d_startLogicalId" str1 ="d_startLogicalId" str2 ="d_endLogicalId"
                               maxlength="20" msg="终止逻辑卡号应为16位-20位数字，输入位数与起始逻辑卡号相同,有效值大于或等于起始逻辑卡号" />
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">备注:</td>
                    <td class="table_edit_tr_td_input" colspan="5">
                        <input type="text"
                               name="d_remark" id="d_remark" size="85" max ="256" min ="1"
                               require="false"  maxLength="256" dataType="LimitContainChinese" msg="备注最大长度为256个字节，一个中文是3个字节（最大85个中文）"
                               />
                    </td>
                    <td class="table_edit_tr_td_input">
                        <input type="hidden" name="h_curDate" id="h_curDate" value="" size="12"
                               msg="有效期格式为(yyyy-mm-dd)!" />

                    </td>
                </tr>

            </table>
        </div>

        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
        <c:set var="add" scope="request" value="1"/>
        <c:set var="modify" scope="request" value="1"/>
        <c:set var="del" scope="request" value="1"/>
        <c:set var="save1" scope="request" value="1"/>
        <c:set var="cancle" scope="request" value="1"/>
        <c:set var="exportBill" scope="request" value="1"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_billNo#d_waterNo');enablePrimaryKeys('detailOp');"/>
        <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail');disableFormControls('detailOp',['d_waterNo','d_billNo'],true);"/>
        <%--<c:set var="addAfterMethod" scope="request" value="disablePrimaryKeys('detailOp');disableFormControlsById(['d_mode','d_lineId','d_stationId','d_exitLineId','d_exitStationId'],'true');controlByCardTypeAndAreaForModeForBorrow();controlLineStationByModeById('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');controlByRadioFoaBorrow();"/>--%>
        <c:set var="addAfterMethod" scope="request" value="disableFormControls('detailOp',['d_mode','d_lineId','d_stationId','d_exitLineId','d_exitStationId','d_lendQuantity','d_startLogicalId','d_endLogicalId'],true);controlByCardTypeAndAreaForModeForBorrow();controlLineStationByModeById('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');controlByRadioFoaBorrow();"/>
        <%--<c:set var="addAfterClickModifyMethod" scope="request" value="controlLineStationByModeById('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');controlByCardTypeAndAreaForModeForBorrow();controlByRadioFoaBorrow();"/>--%>
        <c:set var="addAfterClickModifyMethod" scope="request" value="disableFormControls('detailOp',['d_mode','d_lineId','d_stationId','d_exitLineId','d_exitStationId','d_lendQuantity','d_startLogicalId','d_endLogicalId'],true);controlByCardTypeAndAreaForModeForBorrow();controlLineStationByModeById('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');controlByRadioFoaBorrow();"/>
        <c:set var="addAfterClickSaveMethod" scope="request" value="disableFormControls('detailOp',['d_mode','d_lineId','d_stationId','d_exitLineId','d_exitStationId','d_lendQuantity','d_startLogicalId','d_endLogicalId'],true);controlByCardTypeAndAreaForModeForBorrow();controlLineStationByModeById('detailOp', 'd_mode', 'd_lineId', 'd_stationId', 'd_exitLineId', 'd_exitStationId');controlByRadioFoaBorrow();"/>
        <!--exportBillDetail('detailOp','rpt_ic_rct_bl_out_distribute_plan',['p_bill_no'],['queryCondition'],800,500);-->
        <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_out_lend',['p_bill_no'],['queryCondition'],800,500)"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
    </FORM>


    <!-- 状态栏 通用模板 -->

    <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
</body>
</html>
