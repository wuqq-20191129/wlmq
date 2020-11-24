<%-- 
    Document   : inAdjustCheck
    Created on : 2017-9-9
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
        <title>盘点结果</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <!--<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>-->
        <!--<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>-->
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="
            initDocument('detailOp', 'detail');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPrimaryKeys('detailOp', '');
            setTableRowBackgroundBlock('DataTable')">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">盘点结果
                </td>
            </tr>
        </table>



        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <!--style="width :1800px"-->
            <div id="clearStartHead" class="divForTableBlockHead" style="width :1300px">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:80px" >盘点单号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >仓库</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false  index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票区</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >&nbsp柜&nbsp</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >&nbsp层&nbsp</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >&nbsp托&nbsp</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="9" sortedby="asc"  onclick="sortForTableBlock('clearStart');">面值(分/次)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:110px">盘点日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');" >盘点人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');" style="width:110px">审核时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');" >审核人</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="14" sortedby="asc"  onclick="sortForTableBlock('clearStart');">系统数量</td>
                        
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="15" sortedby="asc"  onclick="sortForTableBlock('clearStart');">盘点数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="16" sortedby="asc"  onclick="sortForTableBlock('clearStart');">偏差数量</td>


                    </tr>


                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData" style="width :1300px">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">

                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            id="${rs.checkBillNo}#${rs.waterNo}" >

                            <td id="rectNo1" class="table_list_tr_col_data_block" >
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');
                                       "
                                       value="${rs.checkBillNo}#${rs.waterNo}" >

                                </input>
                            </td>
                            <td  id="checkBillNo" class="table_list_tr_col_data_block" style="width:80px">
                                ${rs.checkBillNo}
                            </td>
                            <td  id="storageId" class="table_list_tr_col_data_block">
                                ${rs.storageName}
                            </td>
                            <td  id="areaId" class="table_list_tr_col_data_block">
                                ${rs.areaName}
                            </td>
                            <td  id="chestId" class="table_list_tr_col_data_block">
                                ${rs.chestId}
                            </td>
                            <td  id="storeyId" class="table_list_tr_col_data_block">
                                ${rs.storeyId}
                            </td>
                            <td  id="baseId" class="table_list_tr_col_data_block">
                                ${rs.baseId}
                            </td>
                            <td  id="icMainType" class="table_list_tr_col_data_block">
                                ${rs.icMainName}
                            </td>
                            <td  id="icSubType" class="table_list_tr_col_data_block">
                                ${rs.icSubName}
                            </td>
                            <td  id="cardMoney" class="table_list_tr_col_data_block">
                                ${rs.cardMoney}
                            </td>
                            <td  id="checkDate" class="table_list_tr_col_data_block" style="width:110px">
                                ${rs.checkDate}
                            </td>

                            <td  id="checkPerson" class="table_list_tr_col_data_block">
                                ${rs.checkPerson}
                            </td>
                            <td  id="verifyDate" class="table_list_tr_col_data_block" style="width:110px">
                                ${rs.verifyDate}
                            </td>
                            <td  id="verifyPerson" class="table_list_tr_col_data_block">
                                ${rs.verifyPerson}
                            </td>
                            <td  id="sysAmount" class="table_list_tr_col_data_block">
                                ${rs.sysAmount}
                            </td>
                            
                            
                            <td  id="realAmount" class="table_list_tr_col_data_block">
                                ${rs.realAmount}
                            </td>
                            <td  id="diffAmount" class="table_list_tr_col_data_block">
                                ${rs.diffAmount}
                            </td>
                            

                        </tr>

                    </c:forEach>

                </table>
            </div>
        </div>


        <form method="post" name="detailOp" id="detailOp" action="ticketStorageInAdjustCheck" >
            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import  url="/jsp/common/common_template_web_variable_ic.jsp?template_name=common_web_variable_ic" />
            
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr></tr>
                </table>
            </div>
            

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>

            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>


        <!-- 状态栏 通用模板 -->

        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
