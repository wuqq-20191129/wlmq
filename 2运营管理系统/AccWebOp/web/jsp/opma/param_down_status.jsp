<%-- 
    Document   : param_down_status
    Created on : 2017-6-22, 14:36:39
    Author     : mh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>参数下发状态</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="preLoadVal('q_beginDate','q_endDate');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')"> 
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    参数 参数下发状态
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ParamDownStatus">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">开始时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_beginDate" name="q_beginDate"   size="10"  dataType="ICCSDate"  msg="开始日期格式为[yyyy-mm-dd]"/>
                         <a href="javascript:openCalenderDialogByID('q_beginDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                    </td>    
                    <td class="table_edit_tr_td_label">结束时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_endDate" name="q_endDate"   size="10"  dataType="ICCSDate|ThanDate" to="q_beginDate" msg="结束日期格式为[yyyy-mm-dd]且大于等于开始日期"/>
                         <a href="javascript:openCalenderDialogByID('q_endDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                    </td>    
                    <td class="table_edit_tr_td_label">线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_lineId" name = "q_lineId">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">应用情况:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_downloadStatus" name = "q_downloadStatus">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_downloadStatus" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">参数类型：</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_paramTypeId" name = "q_paramTypeId">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_parm_type" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">操作员：</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_operatorId" name = "q_operatorId">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_operator" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_beginDate#q_endDate#q_lineId#q_downloadStatus#q_paramTypeId#q_operatorId');setLineCardNames('queryOp','q_lineId','q_stationId','commonVariable')"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>

        </form>
                    
        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:120px;">参数类型</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:150px;">参数版本</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:170px;">时间</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px;">线路</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');"style="width:110px;">文件情况</td>
			<td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px;">通知情况</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:180px;">应用情况</td>
			<td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px;">操作员</td>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, '');
                                    setPageControl('detailOp');" 
                            id="${rs.code}">
                            <td  id="parmTypeId" class="table_list_tr_col_data_block" style="width:120px;">
                                ${rs.parmTypeText}
                            </td>
                            <td  id="versionText" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.parm_type_id}-${rs.version_no}
                            </td>
                            <td  id="downDateTime" class="table_list_tr_col_data_block" style="width:170px;">
                                ${rs.distribute_datetime}
                            </td>
                            <td  id="lineId" class="table_list_tr_col_data_block" style="width:100px;">
                                ${rs.lineText}
                            </td>
                            <td  id="fileInfor" class="table_list_tr_col_data_block" style="width:110px;">
                                ${rs.fileInforText}
                            </td>
                            <td  id="lineInfor" class="table_list_tr_col_data_block" style="width:90px;">
                                ${rs.lineInforText}
                            </td>
                            <td  id="download" class="table_list_tr_col_data_block" style="width:180px;">
                                ${rs.downloadText}
                            </td>
                            <td  id="operatorId" class="table_list_tr_col_data_block" style="width:90px;">
                                ${rs.operatorText}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <FORM method="post" name="detailOp" id="detailOp" action="ParamDownStatus" >
        <!-- 表头 通用模板 -->
        
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="parmTypeText,versionText,distribute_datetime,lineText,fileInforText,lineInforText,downloadText,operatorText" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="参数下发状态.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ParamDownStatusExportAll" />
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        
    </body>
</html>
