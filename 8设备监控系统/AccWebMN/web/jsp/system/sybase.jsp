<%-- 
    Document   : sybase
    Created on : 2017-9-19
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ORACLE监视</title>
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
                <td colspan="4">ORACLE监视
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
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px" rowspan="3">服务器名称</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px" rowspan="3">服务器IP</td>
                        <td width="65"></td>
                        <td width="65"></td>
                        <td width="65"></td>
                        <td width="65"></td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="6" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 130px" rowspan="3">备份文件大小(G)
                        </td>
                        <td width="130"></td>
                        <td width="130"></td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="9" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 60px" rowspan="3">备份时长(min)
                        </td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="10" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 60px" rowspan="3">状态
                        </td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="11" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 160px" rowspan="3">状态时间
                        </td>
                    </tr>
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="12" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 130px" colspan="2">表空间
                        </td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="13" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 130px" colspan="2">归档空间
                        </td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="14" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 260px" colspan="2">备份起止时间
                        </td>
                    </tr>
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 65px">可用(M)</td>
                        <td id="orderTd" class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 65px">使用率(%)</td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="4" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 65px">归档可用(M)
                        </td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="5" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 65px">归档使用率(%)
                        </td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="7" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 130px">开始
                        </td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="8" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 130px">完成
                        </td>
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
                            id="${rs.name}" >
                            <td  id="name" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.name}
                            </td>
                            <td  id="ip" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.ip}                                
                            </td>
                            <td  id="free_index" class="table_list_tr_col_data_block" style="width: 65px">
                                ${rs.free_index}                                
                            </td>
                            <td  id="used_index_rate" class="table_list_tr_col_data_block" style="width: 65px">
                                <c:choose>
                                    <c:when test="${rs.redOr == '1'}">
                                        <font color="red">
                                            <b>
                                                ${rs.used_index_rate}
                                            </b>
                                        </font>
                                    </c:when>
                                    <c:otherwise>
                                        ${rs.used_index_rate}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td id="recovery_avail_mb" class="table_list_tr_col_data_block" style="width: 65px">
                                    ${rs.recovery_avail_mb}
                            </td>
                            <td id="recovery_used_pct" class="table_list_tr_col_data_block" style="width: 65px">
                                <c:choose>
                                    <c:when test="${rs.redOrForRecovery == '1'}">
                                        <font color="red">
                                            <b>
                                                    ${rs.recovery_used_pct}
                                            </b>
                                        </font>
                                    </c:when>
                                    <c:otherwise>
                                        ${rs.recovery_used_pct}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td  id="backup_size" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.backup_size}
                            </td>                           
                            <td  id="backup_start_time" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.backup_start_time}
                            </td>                           
                            <td  id="backup_end_time" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.backup_end_time}
                            </td>                           
                            <td  id="backup_interval" class="table_list_tr_col_data_block" style="width: 60px">
                                ${rs.backup_interval}
                            </td>                           
                            <td  id="status" class="table_list_tr_col_data_block" style="width: 60px">
                                <c:choose>
                                    <c:when test="${rs.status != '0'}">
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
                            <td  id="status_date" class="table_list_tr_col_data_block" style="width: 160px">
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
                   value="getName,getIp,getFree_index,getUsed_index_rate,getBackup_size,getBackup_start_time,getBackup_end_time,getBackup_interval,getStatus_text,getStatus_date" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="ORACLE监视.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/sybaseExportAll"/>
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
