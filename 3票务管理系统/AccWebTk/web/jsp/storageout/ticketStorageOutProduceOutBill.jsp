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
        <title>出库单</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <!-- 
    initDocument('queryOp', 'detail');
    setControlsDefaultValue('queryOp');
    setQueryControlsDefaultValue('queryOp', 'detailOp');
                setPageControl('detailOp');
    -->
    <body onload="
            initDocument('detailOp', 'detail');

            setListViewDefaultValue('detailOp', 'clearStart');


            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">出库单
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
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);
                                   controlsByFlag('detailOp', [ 'audit']);
                                      "/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">出库单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">订单单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">计划单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >单据状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >制单人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px" >制单日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >审核人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">审核日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >领票人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">记帐员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');">车票管理员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width: 180px">备注</td>


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
                        controlsByFlag('detailOp', ['del', 'audit']);
                         setPageControl('detailOp');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="controlsByFlag('detailOp', [ 'audit']);
                                    clickResultRow('detailOp', this, 'detail');
                                   " 
                            id="${rs.bill_no}" flag="${rs.record_flag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');controlsByFlag('detailOp', ['audit'])"
                                       value="${rs.bill_no}"  flag="${rs.record_flag}">

                                </input>
                            </td>
                            <td  id="billNo" class="table_list_tr_col_data_block" style="width: 90px">
                                <a href='#'
                                   onClick="openwindow('ticketStorageOutProduceManage?queryCondition=${rs.bill_no}&command=query&operType=outBillDetail&billRecordFlag=${rs.record_flag}&ModuleID=${ModuleID}', '', 1200, 500)">
                                    <!--  <a href='#' onClick="window.showModelessDialog('ticketStorageOutProduceManage.do?queryCondition={billNo}&amp;command=queryDetail&amp;operType=planDetail&amp;ModuleID={/Service/Result/ModuleID}','0','dialogWidth:700px;dialogHeight:500px;center:yes;resizable:no;status:no;scroll:no')"> --> 
                                    ${rs.bill_no}
                                </a>

                            </td>
                            <td  id="orderNo" class="table_list_tr_col_data_block" style="width: 90px">
                                <c:choose>
                                    <c:when test="${rs.record_flag == '3'}">
                                        订单明细
                                    </c:when>
                                    <c:otherwise>
                                        <a href='#'
                                           onClick="openwindow('ticketStorageOutProduceManage?queryCondition=${rs.distribute_bill_no}&command=query&operType=order&billRecordFlag=${rs.record_flag}&ModuleID=${ModuleID}', '', 1000, 600)">                                
                                            订单明细
                                        </a>
                                    </c:otherwise>


                                </c:choose>


                            </td>
                            <td  id="distributeBillNo" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.distribute_bill_no}
                            </td>
                            <td  id="recordFlag" class="table_list_tr_col_data_block">
                                ${rs.record_flag_name}
                            </td>
                            <td  id="formMaker" class="table_list_tr_col_data_block">
                                ${rs.form_maker}
                            </td>
                            <td  id="billDate" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.bill_date}
                            </td>
                            <td  id="verifyPerson" class="table_list_tr_col_data_block">
                                ${rs.verify_person}
                            </td>
                            <td  id="verifyDate" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.verify_date}
                            </td>
                            <td  id="drawer" class="table_list_tr_col_data_block">
                                ${rs.drawer}
                            </td>
                            <td  id="accounter" class="table_list_tr_col_data_block">
                                ${rs.accounter}
                            </td>
                            <td  id="administer" class="table_list_tr_col_data_block">
                                ${rs.administer}
                            </td>
                            <td  id="remarks" class="table_list_tr_col_data_block" style="width: 180px">
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

        <form method="post" name="detailOp" id="detailOp" action="ticketStorageOutProduceManage" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />

            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr></tr>
                </table>
            </div>






            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />

            <c:set var="audit" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
           


            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </form>
        <!--
        <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_storage','d_zone','commonVariable','','','');"/>
        <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');"/>
        -->

        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
