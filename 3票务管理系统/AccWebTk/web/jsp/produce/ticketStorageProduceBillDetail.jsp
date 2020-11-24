<%-- 
    Document   : produce_bill_detail
    Created on : 2017-8-21
    Author     : moqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <!--add by zhongzq 20180609-->
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>工作单明细</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <!--<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>-->
        <!--<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>-->
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
<!--    initDocument('formOp','detail');
    setPrimaryKeys('formOp','');
    setControlsDefaultValue('formOp');
    setListViewDefaultValue('formOp','clearStart');-->
    <body onload="
            initDocument('detailOp', 'detail');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPrimaryKeys('detailOp', '');
//            setDetailBillNo('detailOp', 'd_billNo', 'queryCondition');
//            controlByRecordFlagForInit('detailOp', ['add'], true);
            setTableRowBackgroundBlock('DataTable')">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">工作单明细
                </td>
            </tr>
        </table>



        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <!--style="width :1800px"-->
            <div id="clearStartHead" class="divForTableBlockHead" >
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                       "/>
                        </td>	
<!--                                    controlsByFlag('detailOp', ['modify', 'del']);
                                    controlByRecordFlagForInit('detailOp', ['add'], true);-->
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px" >工作单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >有效票数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >面值(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >有效日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >进站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >进站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="9" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出站车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">乘次票限制模式</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">起始盒号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">结束盒号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');">机器号</td>


                    </tr>


                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData" >
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">

                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            id="${rs.bill_no}#${rs.water_no}" >

                            <td id="rectNo1" class="table_list_tr_col_data_block" >
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                       "
                                       value="${rs.bill_no}#${rs.water_no}" >

                                </input>
                            </td>
                            <td  id="billNo" class="table_list_tr_col_data_block" style="width:80px">
                                ${rs.bill_no}
                            </td>
                            <td  id="ic_main_type" class="table_list_tr_col_data_block">
                                ${rs.ic_main_type_name}
                            </td>
                            <td  id="ic_sub_type" class="table_list_tr_col_data_block">
                                ${rs.ic_sub_type_name}
                            </td>
                            <td  id="draw_quantity" class="table_list_tr_col_data_block">
                                ${rs.draw_quantity}
                            </td>
                            <td  id="card_money" class="table_list_tr_col_data_block">
                                ${rs.card_money}
                            </td>
                            <td  id="valid_date" class="table_list_tr_col_data_block">
                                ${rs.valid_date}
                            </td>
                            <td  id="line_id" class="table_list_tr_col_data_block">
                                ${rs.line_id_name}
                            </td>
                            <td  id="station_id" class="table_list_tr_col_data_block">
                                ${rs.station_id_name}
                            </td>
                            <td  id="exit_line_id" class="table_list_tr_col_data_block">
                                ${rs.exit_line_id_name}
                            </td>
                            <td  id="exit_station_id" class="table_list_tr_col_data_block">
                                ${rs.exit_station_id_name}
                            </td>

                            <td  id="model" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.model_name}
                            </td>
                            <td  id="start_box_id" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.start_box_id}
                            </td>
                            <td  id="end_box_id" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.end_box_id}
                            </td>
                            <td  id="machine_no" class="table_list_tr_col_data_block">
                                ${rs.machine_no}
                            </td>

                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>


        <form method="post" name="detailOp" id="detailOp" action="ticketStorageProduceBillDetail" >
            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <%--<c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />--%>
            
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr></tr>
                </table>
            </div>
            
            <input type="hidden"  name="d_bill_no" id="d_bill_no" value="${d_bill_no}"/>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="exportBill" scope="request" value="1"/>

            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_billNo;"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail');"/>
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','W_RPT_IC_RCT_BL_PRODUCE',['p_bill_no'],['d_bill_no'],800,500)"/>


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
