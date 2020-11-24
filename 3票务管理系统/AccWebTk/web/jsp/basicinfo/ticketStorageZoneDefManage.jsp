<%-- 
    Document   : ticketStorageZoneDefManage
    Created on : 2017-8-2
    Author     : xiaowu
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>票区定义</title>

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>

    <body onload="
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setPrimaryKeys('detailOp', 'd_area_id#d_storage_id#d_real_num#d_area_name');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">票区定义
                </td>
            </tr>
        </table>

        
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ticketStorageZoneDefManage" style="margin-bottom: 3px;">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >票区代码:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_area_id" id="q_area_id" size="3" maxlength="3" require="false" dataType="Integer" style="width: 100px;"/>
                    </td>
                    <td class="table_edit_tr_td_label">仓库名称:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_storage_id" name="q_storage_id">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                        </select>	
                    </td>
                    
                    <td class="table_edit_tr_td_label">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_area_id#q_storage_id');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                    <td class="table_edit_tr_td_label" ></td>
                    <td class="table_edit_tr_td_input"></td>
                </tr>
            </table>
        </form>
        
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
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');">票区代码</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px;">票区名称</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px;">仓库名称</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');">实际数量</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px;">上限数量</td>
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
                            id="${rs.area_id}#${rs.storage_id}" >
                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.area_id}#${rs.storage_id}">
                                </input>
                            </td>
                            <td  id="area_id" class="table_list_tr_col_data_block">
                                ${rs.area_id}
                            </td>
                            <td  id="area_name" class="table_list_tr_col_data_block" style="width: 150px;">
                                ${rs.area_name}
                            </td>
                            <td  id="storage_id" class="table_list_tr_col_data_block" style="width: 150px;">
                                ${rs.storage_name}
                            </td>
                            <td  id="real_num" class="table_list_tr_col_data_block">
                                ${rs.real_num}
                            </td>
                            <td  id="upper_num" class="table_list_tr_col_data_block" style="width: 100px;">
                                ${rs.upper_num}
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

        <FORM method="post" name="detailOp" id="detailOp" action="ticketStorageZoneDefManage" >

            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getArea_id,getArea_name,getStorage_name,getReal_num,getUpper_num" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票区定义.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TicketStorageZoneDefExportAll"/>

            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">票区ID:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_area_id" id="d_area_id" size="10" readonly="true"  require="true" dataType="String"/>
                        </td>
                        <td class="table_edit_tr_td_label">票区名称:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_area_name" name="d_area_name" size="10" readonly="true" maxlength="10" dataType="Require" msg="票区不能为空" />
                        </td>
                        <td class="table_edit_tr_td_label">仓库名称:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_storage_id" name="d_storage_id" dataType="Require" msg="仓库不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_storage" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">实际数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_real_num" readonly="true" id="d_real_num" size="9" maxlength="9" />
                        </td>
                        <td class="table_edit_tr_td_label">上限数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_upper_num" id="d_upper_num" size="9"  maxlength="9" require="true" dataType="integer|Positive" msg="上限数量输入大于零的整数"/>
                        </td>
                    </tr>

                </table>
            </div>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
    </body>
</html>
