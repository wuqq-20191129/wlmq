<%-- 
    Document   : ntpAccSyn
    Created on : 2017-9-19
    Author     : zhongziqi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> ACC时钟同步状态</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="
            initDocument('detailOp', 'detail');
            setTableRowBackgroundBlock('DataTable')"> 
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                     ACC时钟同步状态
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead" style=" width: 1000px " >
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style =" width: 200px">服务器名称</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style =" width: 90px">服务器IP</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style =" width: 90px">时钟源IP</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style =" width: 150px">同步时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style =" width: 120px">差异时间(秒)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style =" width: 90px">状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style =" width: 150px">状态时间</td>
                    </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData" style=" width: 1000px ">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, '');
                            " 
                            id="${rs.ip}">
                            <td  id="name" class="table_list_tr_col_data_block" style =" width: 200px">
                                ${rs.name}
                            </td>
                            <td  id="type" class="table_list_tr_col_data_block" style =" width: 90px">
                                ${rs.ip}
                            </td>
                            <td  id="ipSource" class="table_list_tr_col_data_block" style =" width: 90px">
                                ${rs.ipSource}
                            </td>
                            <td  id="statusDateSyn" class="table_list_tr_col_data_block" style =" width: 150px">
                                ${rs.statusDateSyn}
                            </td>
                            <td  id="diff" class="table_list_tr_col_data_block" style =" width: 120px">
                                <c:choose> 
                                    <c:when test="${rs.diffFlag=='0'}" >
                                        ${rs.diff}
                                    </c:when>
                                    <c:otherwise>
                                        <font color="red">
                                        <b>
                                            ${rs.diff}
                                        </b>
                                        </font>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td  id="status" class="table_list_tr_col_data_block" style =" width: 90px">
                                <c:choose> 
                                    <c:when test="${rs.status=='0'}" >

                                        ${rs.statusName}
                                    </c:when>
                                    <c:otherwise>
                                        <font color="red">
                                        <b>
                                            ${rs.statusName}
                                        </b>
                                        </font>

                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td  id="statusDate" class="table_list_tr_col_data_block" style =" width: 150px">
                                ${rs.statusDate}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <FORM method="post" name="detailOp" id="detailOp" action="NtpIccsSyn" >
            <!-- 表头 通用模板 -->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">                  
                </table>
            </div>
            <c:set var="divideShow" scope="request" value="0"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
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
