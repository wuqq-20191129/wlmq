<%--
    Document   : lcc
    Created on : 2017-9-18, 10:57:29
    Author     : mh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ACC与LC通信情况</title>


    <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
    <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
    <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
    <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
    <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
</head>
<!--
  setInvisable('detailOp', 'clone');
 setPrimaryKeys('detailOp', 'd_lineID#d_stationID#d_deviceID#d_deviceType');
-->
<!--
initDocument('formQuery','detail');
initDocument('formOp','detail');
setControlsDefaultValue('formQuery');
setListViewDefaultValue('formOp','clearStart');
setQueryControlsDefaultValue('formQuery','formOp');
setPrimaryKeys('formOp','d_billNo');
-->
<body onload="
            initDocument('detailOp', 'detail');
            setTableRowBackgroundBlock('DataTable')">
<table  class="table_title">
    <tr align="center" class="trTitle">
        <td colspan="4">ACC网络通信情况
        </td>
    </tr>
</table>

<!-- 表头 通用模板 -->
<c:set var="pTitleName" scope="request" value="列表"/>
<c:set var="pTitleWidth" scope="request" value="50"/>
<c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
<div id="clearStartBlock" class="divForTableBlock" >
    <div id="clearStartHead" class="divForTableBlockHead" >
        <table class="table_list_block" id="DataTableHead" >

            <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
            <tr  class="table_list_tr_head_block" id="ignore">

                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 250px" >线路计算机</td>
                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 250px">IP地址</td>
                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 250px">ACC网络通信状态</td>
                <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 250px">状态时间</td>
            </tr>


        </table>

    </div>
    <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
    <div id="clearStart"  class="divForTableBlockData" >
        <table class="table_list_block" id="DataTable" >

            <c:forEach items="${ResultSet}" var="rs">
                <!--class="listTableData" -->
                <!--
                setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                -->
                <tr class="table_list_tr_data"
                    onclick=" clickResultRow('detailOp', this, '');"
                    id="${rs.ip}" >


                    <td  id="name" class="table_list_tr_col_data_block" style="width: 250px">
                            ${rs.name}
                    </td>
                    <td  id="ip" class="table_list_tr_col_data_block" style="width: 250px">
                            ${rs.ip}
                    </td>
                    <c:choose>
                        <c:when test="${rs.status == '1' }">
                            <td  id="status" class="table_list_tr_col_data_block" style="width: 250px;">
                                <strong style="color:red"> ${rs.statusText} </strong>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td  id="status" class="table_list_tr_col_data_block" style="width: 250px">
                                    ${rs.statusText}
                            </td>
                        </c:otherwise>
                    </c:choose>
                    <td  id="status_date" class="table_list_tr_col_data_block" style="width: 250px">
                            ${rs.statusDate}
                    </td>
                </tr>

            </c:forEach>

        </table>
    </div>
</div>



<FORM method="post" name="detailOp" id="detailOp" action="lcc" >

    <c:set var="divideShow" scope="request" value="1"/>
    <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

    <input type="hidden" name="expAllFields" id="d_expAllFields"
           value="getName,getIp,getStatusText,getStatus_date" />
    <input type="hidden" name="expFileName" id="d_expFileName" value="ACC网络通信情况.xlsx" />
    <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
    <input type="hidden" name="methodUrl" id="d_methodUrl" value="/LccExportAll"/>

    <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
    <div id="detail"  class="divForTableDataDetail" >
        <table  class="table_edit_detail">
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

