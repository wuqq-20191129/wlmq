<%-- 
    Document   : inOutProduceDiff
    Created on : 2017-8-10
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <!--add by zhongzq 20180609-->
        <meta http-equiv="X-UA-Compatible" content="IE=9,Chrome=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>生产出入库差额明细</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <!--<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>-->
        <!--<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>-->
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageIn.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <!--
    <body onload="setListViewDefaultValue('query1','clearStart');setBillNo('{/Service/Result/bill_no}')">
    -->
    <body onload="setListViewDefaultValue('detailOp', 'clearStart');
            setTableRowBackgroundBlock('DataTable')
                    ">
            <!--setBillNo('${bill_no}');-->

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">生产出入库差额明细
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">入库日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">入库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出库数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >有效票数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >ES废票</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" > 人工废票</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >遗失数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >实际结余</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">出入库差额</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 130px">差额原因</td>

                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" >
                           

                            <td  id="in_bill_date" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.in_bill_date}

                            </td>
                            <td  id="out_bill_no" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.in_bill_no}

                            </td>
                            
                            <td  id="out_bill_no" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.out_bill_no}

                            </td>
                            <td  id="out_num" class="table_list_tr_col_data_block">
                                ${rs.out_num}
                            </td>
                            <td  id="valid_num" class="table_list_tr_col_data_block">
                                ${rs.valid_num}
                            </td>
                            <td  id="es_useless_num" class="table_list_tr_col_data_block">
                                ${rs.es_useless_num}
                            </td>
                            <td  id="man_useless_num" class="table_list_tr_col_data_block">
                                ${rs.man_useless_num}
                            </td>
                            <td  id="lost_num" class="table_list_tr_col_data_block"  style="color: red">
                                ${rs.lost_num}
                            </td>
                            <td  id="real_balance" class="table_list_tr_col_data_block">
                                ${rs.real_balance}
                            </td>
                            <td  id="real_balance" class="table_list_tr_col_data_block">
                                ${rs.out_in_diff}
                            </td>
                            <td  id="diff_id" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.diff_id_name}
                            </td>

                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
