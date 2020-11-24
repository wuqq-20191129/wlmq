<%-- 
    Document   : station_device
    Created on : 2017-5-16, 20:48:42
    Author     : hejj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <!--add by zhongzq 20180609-->
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

        <!--<body >-->
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
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                       	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出库原因</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出库数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票区</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >详细位置</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">票盒起号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 90px">票盒止号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 110px">逻辑卡号起</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 110px">逻辑卡号止</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');">有效期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');">金额(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:100px">票卡有效天数</td>


                    </tr>


                </table>

            </div>
            
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        setPageControl('detailOp');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="controlsByFlag('detailOp', ['del', 'audit']);
                                    clickResultRow('detailOp', this, 'detail');
                                    " 
                            id="${rs.bill_no}" flag="${rs.record_flag}">


                            <td  id="billNo" class="table_list_tr_col_data_block" style="width: 90px">

                                 ${rs.bill_no}
                            </td>
                            <td  id="icMainType" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.ic_main_type_name}

                            </td>
                            <td  id="icSubType" class="table_list_tr_col_data_block">
                                ${rs.ic_sub_type_name}
                            </td>
                            <td  id="reasonId" class="table_list_tr_col_data_block">
                                ${rs.reason_id_name}
                            </td>
                            <td  id="outNum" class="table_list_tr_col_data_block">
                                <c:if test="${rs.section_num != null and rs.section_num !=0}">
                                    ${rs.section_num}
                                </c:if>
                                <c:if test="${rs.section_num == null or rs.section_num==0}">
                                    ${rs.out_num}
                                </c:if>
                            </td>
                            <td  id="storageId" class="table_list_tr_col_data_block">
                                ${rs.storage_id_name}
                            </td>
                            <td  id="areaId" class="table_list_tr_col_data_block">
                                ${rs.area_id_name}
                            </td>
                            <td  id="detailPlace" class="table_list_tr_col_data_block">
                                ${rs.detail_place}
                            </td>
                            <td  id="startBoxId" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.start_box_id}
                            </td>
                            <td  id="endBoxId" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.end_box_id}
                            </td>
                            <td  id="startLogicalId" class="table_list_tr_col_data_block" style="width: 110px">
                                ${rs.start_logical_id}
                            </td>
                            <td  id="endLogicalId" class="table_list_tr_col_data_block" style="width: 110px">
                                ${rs.end_logical_id}
                            </td>
                            <td  id="vaildDate" class="table_list_tr_col_data_block">
                                ${rs.valid_date}
                            </td>
                            <td  id="cardMoney" class="table_list_tr_col_data_block">
                                ${rs.card_money}
                            </td>
                            <td  id="cardAvaDays" class="table_list_tr_col_data_block" style="width:100px">
                                ${rs.card_ava_days}
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageOutProduceManage" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />

            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr></tr>


                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />


            <c:set var="exportBill" scope="request" value="1"/>

           


            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:set var="exportBillAfter" scope="request" value="exportBillDetail('detailOp','w_rpt_ic_rct_bl_out_produce_detail',['p_bill_no'],['queryCondition'],800,500)"/>
            
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <%--<c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable','','','');"/>--%>
        <%--<c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>--%>

        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
