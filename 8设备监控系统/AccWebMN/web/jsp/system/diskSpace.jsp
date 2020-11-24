<%-- 
    Document   : diskSpace
    Created on : 2017-9-15
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>磁盘空间</title>
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
                <td colspan="4">磁盘空间
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <form method="post" name="queryOp" id="queryOp" action="diskSpace">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="0"/>
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
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px" >服务器名称</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">服务器IP</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">文件系统</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=true index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">可用空间(G)</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 110px">使用率</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">挂载点</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">状态时间</td>
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
                            id="${rs.ip}#${rs.file_system}" >
                            <td  id="name" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.name}
                            </td>
                            <td  id="ip" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.ip}                                
                            </td>
                            <td  id="file_system" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.file_system}                                
                            </td>
                            <td  id="avail" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.avail}                                
                            </td>
                            <td  id="status" class="table_list_tr_col_data_block" style="width: 110px">
                                <c:choose>
                                    <c:when test="${rs.redOr == '1'}">
                                        <font color="red">
                                            <b>
                                                ${rs.capacity}
                                            </b>
                                        </font>
                                    </c:when>
                                    <c:otherwise>
                                        ${rs.capacity}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td  id="mounted_on" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.mounted_on}
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
        <FORM method="post" name="detailOp" id="detailOp" action="diskSpace" >
            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getName,getIp,getFile_system,getAvail,getCapacity,getMounted_on,getStatus_date" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="磁盘空间.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/diskSpaceExportAll"/>
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">                  
                </table>
            </div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <%--<c:set var="btNext" scope="request" value="1"/>--%>
            <%--<c:set var="btNextEnd" scope="request" value="1"/>--%>
            <%--<c:set var="btBack" scope="request" value="1"/>--%>
            <%--<c:set var="btBackEnd" scope="request" value="1"/>--%>
            <%--<c:set var="btGoPage" scope="request" value="1"/>--%>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
    </body>
</html>
