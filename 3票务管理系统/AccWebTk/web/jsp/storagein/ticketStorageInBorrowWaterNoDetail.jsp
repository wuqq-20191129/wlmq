<%--
    Document   : ticketStorageInBorrowWaterNoDetail
    Created on : 2017-9-9
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

</head>
<body onload="
        initDocument('detailOp', 'detail');
        setListViewDefaultValue('detailOp', 'clearStart');
        setTableRowBackgroundBlock('DataTable')">
    <!--<body >-->
    <table  class="table_title">
        <tr align="center" class="trTitle">
            <td colspan="4">借出流水号 归还明细
            </td>
        </tr>
    </table>

    <!-- 表头 通用模板 -->
    <c:set var="pTitleName" scope="request" value="列表"/>
    <c:set var="pTitleWidth" scope="request" value="50"/>
    <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
    <div id="clearStartBlock" class="divForTableBlock">
        <div id="clearStartHead" class="divForTableBlockHead" style="width :2100px">
            <table class="table_list_block" id="DataTableHead" >
                <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                <tr  class="table_list_tr_head_block" id="ignore">
                    <td   class="table_list_tr_col_head_block">
                        <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                               "/>
                    </td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >流水号</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  >借票流水</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">归还单号</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">仓库</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">票区</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">票卡主类型</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px">票卡子类型</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >归还数量</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >不归还数量</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" >遗失数量</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:130px">逻辑卡号起</td>
                    <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:130px">逻辑卡号止</td>
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
                        onclick="clickOneResultRow('detailOp', this, '','rectNo');"
                        id="${rs.water_no}"  >
                        <td id="rectNo1" class="table_list_tr_col_data_block" >
                            <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                   value ="${rs.water_no}"  >
                            </input>
                        </td>
                        <td  id="waterNo" class="table_list_tr_col_data_block" >
                            ${rs.water_no}
                        </td>

                        <td  id="lendWaterNo" class="table_list_tr_col_data_block" >
                            ${rs.lend_water_no}
                        </td>
                        <td  id="billNo" class="table_list_tr_col_data_block" style="width:90px">
                            ${rs.bill_no}
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
                        <td  id="startLogicalId" class="table_list_tr_col_data_block" style="width:130px">
                            ${rs.start_logical_id}
                        </td>
                        <td  id="endLogicalId" class="table_list_tr_col_data_block" style="width:130px">
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
        <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
        <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />
        <div id="detail"  class="divForTableDataDetail" >
            <table  class="table_edit_detail">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">借出总数量：</td>
                    <td class="table_edit_tr_td_input" >
                        <strong>${lendNum}</strong>
                    </td>
                </tr>
                <tr class="table_edit_tr">

                    <td class="table_edit_tr_td_label">归还总数量(归还+不归还+遗失)：</td>
                    <td class="table_edit_tr_td_input" >
                        <strong>${returnTotal}</strong>
                    </td>
                </tr>

            </table>
        </div>


    </FORM>
</body>
</html>