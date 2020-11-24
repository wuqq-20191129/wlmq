<%-- 
    Document   : disk
    Created on : 2017-9-15
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>硬盘状态</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>  
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    
    <body onload="
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">硬盘状态
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <form method="post" name="queryOp" id="queryOp" action="disk">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
        </form>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead" >
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 230px" >服务器名称</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">设备型号</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">服务器IP</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">硬盘状态</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">状态时间</td>
                    </tr>
                </table>
            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" >
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, '');
                            setPageControl('detailOp');" 
                            id="${rs.ip}" >
                            <td  id="name" class="table_list_tr_col_data_block" style="width: 230px">
                                ${rs.name}
                            </td>
                            <td  id="type" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.type}                                
                            </td>
                            <td  id="ip" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.ip}                                
                            </td>
                            <td  id="status" class="table_list_tr_col_data_block" style="width: 150px">
                                <c:choose>
                                    <c:when test="${rs.status == '1'}">
                                        <font color="red">
                                            <b>
                                                ${rs.status_text}
                                            </b>
                                        </font>
                                    </c:when>
                                    <c:otherwise>
                                        ${rs.status_text}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td  id="status_date" class="table_list_tr_col_data_block" style="width: 130px">
                                    ${rs.statusDate}
                            </td>                           
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <!-- 表头 通用模板 -->
        <FORM method="post" name="detailOp" id="detailOp" action="disk" >
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getName,getType,getIp,getStatus_text,getStatus_date" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="硬盘状态.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/diskExportAll"/>
            
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
