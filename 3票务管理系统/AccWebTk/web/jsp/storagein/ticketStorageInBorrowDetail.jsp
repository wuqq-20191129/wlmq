<%--
    Document   : ticketStorageInBorrowDetail
    Created on : 2017-9-6
    Author     : zhongziqi
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
    <title>明细</title>
    <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
    <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
    <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
    <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
    <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
    <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageIn.js"></script>
    <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
    <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    <SCRIPT LANGUAGE="JavaScript">
        function setIdVisable(formName)
        {
            if (document.forms[formName].getElementsByTagName("input")["d_startLogicalId"].value == "") {
                alert('修改记录无车票ID,不需拆分车票ID!');
            } else
            {
                alert('请注意记录的车票ID拆分情况!');
                document.forms[formName].getElementsByTagName("input")["d_startLogicalId"].disabled = false;
                document.forms[formName].getElementsByTagName("input")["d_endLogicalId"].disabled = false;
            }
        }
        function trimLendWateno(formName) {
            document.forms[formName].getElementsByTagName("input")["d_lendWaterNo"].value = document.forms[formName].getElementsByTagName("input")["d_lendWaterNo"].value.trim();
//            alert("enter"+document.forms[formName].getElementsByTagName("input")["d_lendWaterNo"].value);
        }
    </SCRIPT>
</head>
<body onload="
        initDocument('detailOp', 'detail');
        setListViewDefaultValue('detailOp', 'clearStart');
        setTableRowBackgroundBlock('DataTable')">
    <!--<body >-->
    <table  class="table_title">
        <tr align="center" class="trTitle">
            <td colspan="4">明细
            </td>
        </tr>
    </table>

    <!-- 表头 通用模板 -->
    <c:set var="pTitleName" scope="request" value="本次归还列表"/>
    <c:set var="pTitleWidth" scope="request" value="50"/>
    <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
    <div id="clearStartBlock" class="divForTableBlock">
        <div id="clearStartHead" class="divForTableBlockHead" style="width :2100px">
            <table class="table_list_block" id="DataTableHead" >
                <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                <tr  class="table_list_tr_head_block" id="ignore">
                    <td   class="table_list_tr_col_head_block">
                        <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                controlsByFlag('detailOp', ['del']);
                                controlsByFlagWithoutCk('detailOp', ['modify']);
                               "/>
                    </td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  >借票流水</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">单据状态</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">仓库</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">票区</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">票卡主类型</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">票卡子类型</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >归还数量</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >不归还数量</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" >遗失数量</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:140px">逻辑卡号起</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:140px">逻辑卡号止</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');">面值(分/次)</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');">有效期</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:120px">盒号起</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="16" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:120px" >盒号止</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">进出站限制模式</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="18" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width:90px" >进站线路</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="19" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:90px">进站车站</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="20" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:90px" >出站线路</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="21" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width:90px" >出站车站</td>

                </tr>
            </table>
        </div>

        <div id="clearStart"  class="divForTableBlockData" style="width :2100px">
            <table class="table_list_block" id="DataTable" >
                <c:forEach items="${ResultSet}" var="rs">
                    <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                        onMouseOut="outResultRow('detailOp', this);"
                        onclick="setSelectValuesByRowPropertyName('detailOp', 'd_icSubType', 'commonVariable', 'icMainType');
                                setSelectValuesByRowPropertyName('detailOp', 'd_areaId', 'commonVariable1', 'storageId');
                                clickOneResultRow('detailOp', this, 'detail','rectNo');
                                controlsByFlag('detailOp', ['del']);
                                controlsByFlagWithoutCk('detailOp', ['modify']);
                                trimLendWateno('detailOp');
                        "
                        id="${QueryCondition}#${rs.water_no}" flag="${rs.record_flag}" icMainType="${rs.ic_main_type}" storageId="${rs.storage_id}">

                        <td id="rectNo1" class="table_list_tr_col_data_block" >
                            <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                    controlsByFlag('detailOp', ['del']);
                                    controlsByFlagWithoutCk('detailOp', ['modify']);
                                   "
                                   value="${QueryCondition}#${rs.water_no}"  flag="${rs.record_flag}" >
                            </input>
                        </td>
                        <td  id="waterNo" class="table_list_tr_col_data_block" >
                            ${rs.water_no}
                        </td>

                        <td  id="lendWaterNo" class="table_list_tr_col_data_block" >
                            <a href='#'
                               onClick="openwindow('ticketStorageInBorrowManage?queryConditionLendWaterNo=${rs.lend_water_no}&queryConditionWaterNo=${rs.water_no}&command=queryReturnDetail&operType=returnDetail&ModuleID=${ModuleID}&_divideShow=0', '', 950, 500)">
                                ${rs.lend_water_no}
                            </a>
                        </td>
                        <td  id="recordFlag" class="table_list_tr_col_data_block" style="width:90px">
                            ${rs.record_flag_name}
                        </td>
                        <td  id="storageId" class="table_list_tr_col_data_block" style="width:90px">
                            ${rs.storage_name}
                        </td>
                        <td  id="areaId" class="table_list_tr_col_data_block" style="width:90px">
                            ${rs.area_name}
                        </td>
                        <td  id="icMainType" class="table_list_tr_col_data_block" style="width:90px">
                            ${rs.ic_main_type_name}
                        </td>
                        <td  id="icSubType" class="table_list_tr_col_data_block" style="width:90px">
                            ${rs.ic_sub_type_name}
                        </td>
                        <td  id="returnQuantity" class="table_list_tr_col_data_block">
                            ${rs.return_quantity}
                        </td>
                        <td  id="notQuantity" class="table_list_tr_col_data_block">
                            ${rs.not_quantity}
                        </td>
                        <td  id="lostQuantity" class="table_list_tr_col_data_block">
                            ${rs.lost_quantity}
                        </td>
                        <td  id="startLogicalId" class="table_list_tr_col_data_block" style="width:140px">
                            ${rs.start_logical_id}
                        </td>
                        <td  id="endLogicalId" class="table_list_tr_col_data_block" style="width:140px">
                            ${rs.end_logical_id}
                        </td>
                        <td  id="cardMoney" class="table_list_tr_col_data_block">
                            ${rs.card_money}
                        </td>
                        <td  id="validDate" class="table_list_tr_col_data_block">
                            ${rs.valid_date}
                        </td>
                        <td  id="startBoxId" class="table_list_tr_col_data_block" style="width:120px">
                            ${rs.start_box_id}
                        </td>
                        <td  id="endBoxId" class="table_list_tr_col_data_block" style="width:120px">
                            ${rs.end_box_id}
                        </td>
                        <td  id="mode" class="table_list_tr_col_data_block" style="width:100px">
                            ${rs.model_name}
                        </td>
                        <td  id="lineId" class="table_list_tr_col_data_block" style="width:90px">
                            ${rs.line_name}
                        </td>
                        <td  id="stationId" class="table_list_tr_col_data_block" style="width:90px">
                            ${rs.station_name}
                        </td>
                        <td  id="exitLineId" class="table_list_tr_col_data_block" style="width:90px">
                            ${rs.exit_line_name}
                        </td>
                        <td  id="exitStationId" class="table_list_tr_col_data_block" style="width:90px">
                            ${rs.exit_station_name}
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
    <form method="post" name="detailOp" id="detailOp" action="ticketStorageInBorrowManage" >
        <c:set var="divideShow" scope="request" value="0"/>
        <c:set var="operTypeValue" scope="request" value="planDetail"/>
        <c:set var="queryConditionValue" scope="request" value="${QueryCondition}"/>
        <c:set var="billRecordFlag" scope="request" value="${BillRecordFlag}"/>
        <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
        <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />
        <div id="detail"  class="divForTableDataDetail" >
            <table  class="table_edit_detail">
                <tr class="table_edit_tr">

                    <td class="table_edit_tr_td_label">流水号: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="d_waterNo" id="d_waterNo" size="10" maxlength="18" />
                    </td>
                    <td class="table_edit_tr_td_label">借票流水: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="d_lendWaterNo" id="d_lendWaterNo" size="12" maxlength="12" />
                    </td>

                    <td class="table_edit_tr_td_label">票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_icMainType" name="d_icMainType" dataType="Require" msg="票卡主类型不能为空!"
                                onChange="setSelectValues('detailOp', 'd_icMainType', 'd_icSubType', 'commonVariable');">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_maincard" />
                        </select>

                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">票卡子类型:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_icSubType"  name="d_icSubType" dataType="Require" msg="票卡子类型不能为空!" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_mainsubcard" />
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">仓库:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_storageId" name="d_storageId" dataType="Require" msg="仓库不能为空!"
                                onChange="
                                ">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票区:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="d_areaId" name="d_areaId" dataType="Require" msg="库存票区不能为空!"
                                onChange="
                                ">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storagezone_borrow_in" />
                    </td>
                    <td class="table_edit_tr_td_label">归还数量:</td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text" name="d_returnQuantity" id="d_returnQuantity" size="8" maxlength="9"
                               dataType="integer" required="true" msg="归还数量为整数"/>
                    </td>
                    <td class="table_edit_tr_td_label">不归还数量:</td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text"
                               name="d_notQuantity" id="d_notQuantity" size="8" maxlength="9" value = "0"
                               dataType="Integer|Limit" required="true" msg="不归还数量为整数"/>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">遗失数量:</td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text"
                               name="d_lostQuantity" id="d_lostQuantity" size="8" maxlength="9" value = "0"
                               dataType="Integer|Limit" required="true" msg="遗失数量为整数"/>
                    </td>
                    <td class="table_edit_tr_td_label">逻辑卡号起:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text"
                               name="d_startLogicalId" id="d_startLogicalId" size="25"
                               require="false" dataType="Number|Limit" min="16" max="20"
                               maxlength="20" msg="起始逻辑卡号应为16位-20位数字" /></td>
                    <td class="table_edit_tr_td_label">逻辑卡号止:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text"
                               name="d_endLogicalId" id="d_endLogicalId" size="25"
                               require="false" min="16" max="20"
                               dataType="SameLen|Number|CompareBigNum|Limit"
                               operator="GreaterThanEqual" to="d_startLogicalId" str1 ="d_startLogicalId" str2 ="d_endLogicalId"
                               maxlength="20" msg="终止逻辑卡号应为16位-20位数字，输入位数与起始逻辑卡号相同,有效值大于或等于起始逻辑卡号" />
                    </td>
                </tr>
            </table>
        </div>

        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
        <c:set var="updateId" scope="request" value="1"/>
        <c:set var="modify" scope="request" value="1"/>
        <c:set var="del" scope="request" value="1"/>
        <c:set var="save1" scope="request" value="1"/>
        <c:set var="updateId" scope="request" value="1"/>
        <c:set var="cancle" scope="request" value="1"/>
        <c:set var="exportBill" scope="request" value="1"/>
        <c:set var="clickMethodVisable" scope="request" value="setIdVisable('detailOp');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','');"/>
        <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail');"/>
        <c:set var="addAfterClickModifyMethod" scope="request" value="disableFormControlsById(['d_waterNo','d_lendWaterNo','d_icMainType','d_icSubType','d_startLogicalId','d_endLogicalId','d_storageId','d_areaId'],true);"/>
        <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_in_return',['p_bill_no'],['queryCondition'],800,500)"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
    </FORM>
    <!-- 状态栏 通用模板 -->
    <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
</body>
</html>
