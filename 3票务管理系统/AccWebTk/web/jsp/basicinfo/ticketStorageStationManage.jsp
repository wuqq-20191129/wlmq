<%-- 
    Document   : ticketStorgeStationManage
    Created on : 2017-7-29, 17:06:41
    Author     : tdb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>票务车站</title>
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
            setPrimaryKeys('detailOp', 'd_lineId#d_stationId');
            setListViewDefaultValue('detailOp', 'clearStart');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">票务车站</td>
            </tr>
        </table>
        
        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">

                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:170px;" >票务线路名称</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:170px;" >票务车站代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:170px;" >票务车站名称</td>
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
                            onclick="clickResultRow('detailOp', this, 'detail');"
                                   
                            id="${rs.line_id}#${rs.line_name}#${rs.station_id}#${rs.chinese_name}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.station_id}">
                                </input>
                            </td>
                            <td  id="lineId" class="table_list_tr_col_data_block" style="width:170px;">
                                ${rs.pub_lineText}
                            </td>
                            <td  id="stationId" class="table_list_tr_col_data_block" style="width:170px;">
                                ${rs.station_id}
                            </td>
                            <td  id="chineseName" class="table_list_tr_col_data_block" style="width:170px;">
                                ${rs.chinese_name}
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageStationManage">
            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getPub_lineText,getStation_id,getChinese_name" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票务车站.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageStationManageExportAll"/>
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label"  style="width:100px;">票务线路名称:</td>
                        <td class="table_edit_tr_td_input"  style="width:100px;">
                            <select id="d_lineId" name="d_lineId" require="true" dataType="NotEmpty"  msg="票务线路名称不能为空">				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_icline" />
                            </select>
                        </td>
                         <td class="table_edit_tr_td_label"  style="width:100px;">票务车站代码:</td>
                        <td class="table_edit_tr_td_input"  style="width:100px;">
                            <input type="text" name="d_stationId" id="d_stationId" size="10" maxLength="2" require="true" dataType="NotEmpty|Number" msg="票务车站代码不能为空且应为数字"/>
                        </td>
                        <td class="table_edit_tr_td_label"  style="width:100px;">票务车站名称：</td>
                        <td class="table_edit_tr_td_input"  style="width:310px;">
                            <input type="text" name="d_chineseName" id="d_chineseName" size="10" require="true" dataType="NotEmpty|LimitContainChinese"  maxLength="20" min="1" max="40" msg="票务车站名称不能为空且不能超过40个字节，一个中文字符占3个字节" style="width:150px;"/>
                        </td>
                    </tr>

                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineId#d_stationId');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM> 
        
    </body>
</html>
