<%--
    Document   : ticketStorageInBorrowInDetail
    Created on : 2017-9-12
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
        function setAudit(formName)
        {
            disableButton(formName, "btAudit", false);
            var flag = document.forms[formName].getElementsByTagName("input")["recordFlag"].value;
            var button = document.forms[formName].getElementsByTagName("input")["btAudit"];
//            alert(button.disabled);
//            button.disabled = "false";
//            button.setAttribute("disabled", false);

            if (flag === "0") {
                button.setAttribute("disabled", "true");
//                 alert(flag+"TRUE");
            }
        }
        function refreshParentWindowAfterAuditSuccess(formName) {
            var flag = document.forms['detailOp'].getElementsByTagName("input")["recordFlag"].value;
            //alert("flag:"+flag);
            if (flag === "0") {
                window.opener.queryByCondition();
            }
        }
    </SCRIPT>
</head>
<body onload="
        initDocument('detailOp', 'detail');
        setListViewDefaultValue('detailOp', 'clearStart');
        setAudit('detailOp');
        setTableRowBackgroundBlock('DataTable');
        refreshParentWindowAfterAuditSuccess();
        ">
    <!--<body >-->
    <table  class="table_title">
        <tr align="center" class="trTitle">
            <td colspan="4">借票归还入库明细
            </td>
        </tr>
    </table>

    <!-- 表头 通用模板 -->
    <c:set var="pTitleName" scope="request" value="本次归还列表"/>
    <c:set var="pTitleWidth" scope="request" value="50"/>
    <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
    <div id="clearStartBlock" class="divForTableBlock">
        <div id="clearStartHead" class="divForTableBlockHead" style="width :1900px">
            <table class="table_list_block" id="DataTableHead" >
                <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                <tr  class="table_list_tr_head_block" id="ignore">
                    <td   class="table_list_tr_col_head_block">
                        <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                setAudit('detailOp');
                               "/>
                    </td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >入库单号</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  >入库原因</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">仓库</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">票区</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">票卡主类型</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">票卡子类型</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >数量</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="8" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:140px">逻辑卡号起</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="9" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:140px">逻辑卡号止</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">面值(分/次)</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');">有效期</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:120px">盒号起</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:120px" >盒号止</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:90px">进出站限制模式</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width:90px" >进站线路</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="16" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:90px">进站车站</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="17" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:90px" >出站线路</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="18" sortedby="asc"  onclick="sortForTableBlock('clearStart');"style="width:90px" >出站车站</td>
                </tr>
            </table>
        </div>

        <div id="clearStart"  class="divForTableBlockData" style="width :1900px">
            <table class="table_list_block" id="DataTable" >
                <c:forEach items="${ResultSet}" var="rs">
                    <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);"
                        onMouseOut="outResultRow('detailOp', this);"
                        onclick="clickOneResultRow('detailOp', this, 'detail','rectNo');
                                setAudit('detailOp');"
                        id="${QueryCondition}#${rs.water_no}" flag="${rs.record_flag}" >

                        <td id="rectNo1" class="table_list_tr_col_data_block" >
                            <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                    setAudit('detailOp');"
                                   value="${QueryCondition}#${rs.water_no}"  flag="${rs.record_flag}" >
                            </input>
                        </td>
                        <td  id="waterNo" class="table_list_tr_col_data_block" >
                            ${rs.water_no}
                        </td>

                        <td  id="reasonId" class="table_list_tr_col_data_block" >
                            ${rs.reason_name}
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
                            ${rs.in_num}
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
                        <td  id="mode" class="table_list_tr_col_data_block" style="width:90px">
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

    <form method="post" name="detailOp" id="detailOp" action="ticketStorageInBorrowManage" >

        <c:set var="divideShow" scope="request" value="0"/>
        <c:set var="operTypeValue" scope="request" value="inBillDetail"/>
        <c:set var="queryConditionValue" scope="request" value="${QueryCondition}"/>
        <c:set var="billRecordFlag" scope="request" value="${BillRecordFlag}"/>
        <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
        <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />
        <div id="detail"  class="divForTableDataDetail" >
            <table  class="table_edit_detail">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">
                        <input id="recordFlag" type="hidden" name="recordFlag"  value="${recordFlag}" />
                    </td>
                </tr>
            </table>

        </div>

        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
        <c:set var="audit" scope="request" value="1"/>
        <c:set var="exportBill" scope="request" value="1"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','');"/>
        <c:set var="clickMethod" scope="request" value="btnClickForMulDataAudit('detailOp','clearStart','detail');refreshParentWindowAfterAuditSuccess();"/>
        <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_in_borrow',['p_bill_no'],['queryCondition'],800,500)"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
    </FORM>
    <!-- 状态栏 通用模板 -->
    <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
</body>
</html>
