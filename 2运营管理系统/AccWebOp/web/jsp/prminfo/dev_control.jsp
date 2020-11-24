<%-- 
    Document   : dev_control
    Created on : 2017-6-13
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>设备控制</title>

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_line_id#d_station_id');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'modify;submit1');
                    setPrimaryKeys('detailOp', 'd_line_id#d_station_id');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:otherwise>
        </c:choose>
        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">设备控制（
                    ${version.record_flag_name}：${version.version_no}
                    ）
                </td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="版本信息"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <table class="table_edit" >
            <tr class="table_edit_tr">
                <td class="table_edit_tr_td_label">生效起始时间:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="startDate" id="startDate" size="20" value="${version.begin_time}" readonly="true">
                    </input>
                </td>
                <td class="table_edit_tr_td_label">生效截至时间:</td>
                <td class="table_edit_tr_td_input">
                    <input type="text" name="endDate" id="endDate" size="20" value="${version.end_time}" readonly="true">
                    </input>
                </td>
            </tr>
        </table>
        <br/>
                    
        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
	<div id="clearStartBlock" class="divForTableBlock">
            <DIV id="clearStartHead"  class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td  align="center" class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
		
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">SC操作员无操作自动注销时间(秒)</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="2" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">SC重新连接LC的周期(秒)</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="3" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">BOM运营人员自动注销时间(秒)</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="4" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">进/出闸超时(秒)</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="5" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">降级模式的影响有效期(天)</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="6" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">储值卡钱包充值上限(分)</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="7" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">积分钱包充值上限(次)</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="8" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">单程票发售钱包充值上限(张)</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="9" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">储值卡钱包充值下限(分)</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="10" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">积分钱包充值下限(次)</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="11" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">单程票发售钱包充值下限(张)</td>
                </tr>
	       </table>
            </div>
            
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                <c:forEach items="${ResultSet}" var="rs">
                    <tr class="table_list_tr_data" 
                        onMouseOver="overResultRow('detailOp', this);" 
                        onMouseOut="outResultRow('detailOp', this);" 
                        onclick="clickResultRow('detailOp',this,'detail');setPageControl('detailOp');" 
                        id="${rs.version_no}">

                        <td id="rectNo1" class="table_list_tr_col_data_block">
                            <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                   value="${rs.version_no}">
                            </input>
                        </td>
                        <td id="logoff_idle_sc" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.logoff_idle_sc}
                        </td>
                        <td id="interval_connectsc" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.interval_connectsc}
                        </td>
                        <td id="logoff_idle_bom" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.logoff_idle_bom}
                        </td>
                        <td id="time_out_pass" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.time_out_pass}
                        </td>
                        <td id="degrade_time" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.degrade_time}
                        </td>
                        <td id="upper_amount" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.upper_amount}
                        </td>
                        <td id="upper_count" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.upper_count}
                        </td>
                        <td id="upper_sjt" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.upper_sjt}
                        </td>
                        <td id="under_amount" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.under_amount}
                        </td>
                        <td id="under_count" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.under_count}
                        </td>
                        <td id="under_sjt" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.under_sjt}
                        </td>
                    </tr>
                </c:forEach>
            </table>
	   </DIV>
        </DIV>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="DevControl" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getLogoff_idle_sc,getInterval_connectsc,getLogoff_idle_bom,getTime_out_pass,getDegrade_time,getUpper_amount,getUpper_count,getUpper_sjt,getUnder_amount,getUnder_count,getUnder_sjt" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="设备控制.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/DevControlExportAll"/>
            
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" style="width:20%;">SC操作员无操作自动注销时间(秒):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_logoff_idle_sc" name="d_logoff_idle_sc" size="10" maxlength="4"  Require="true" dataType="integer" msg="SC操作员无操作自动注销时间只能为正整数！"/>
                        </td>
                        <td class="table_edit_tr_td_label" style="width:20%;">SC重新连接LC的周期(秒):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_interval_connectsc" name="d_interval_connectsc" size="10" maxlength="4"  Require="true"  dataType="integer" msg="SC重新连接LC的周期只能为正整数！"/>
                        </td>
                        <td class="table_edit_tr_td_label" style="width:20%;">降级模式的影响有效期(天):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_degrade_time" name="d_degrade_time" size="10" maxlength="2"  Require="true" dataType="integer" msg="降级模式的影响有效期只能为正整数！"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" style="width:20%;">BOM运营人员自动注销时间(秒):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_logoff_idle_bom" name="d_logoff_idle_bom" size="10" maxlength="4"  Require="true" dataType="integer" msg="BOM运营人员自动注销时间只能为正整数！"/>
                        </td>
                        <td class="table_edit_tr_td_label" style="width:20%;">进/出闸超时(秒):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_time_out_pass" name="d_time_out_pass" size="10" maxlength="4"  Require="true" dataType="integer" msg="进/出闸超时只能为正整数！"/>
                        </td>
                        <td class="table_edit_tr_td_label" style="width:20%;">积分钱包充值下限(次):</td>
                        <td class="table_edit_tr_td_input">
                            <input name="d_under_count" type="text" id="d_under_count" size="10" maxlength="8"  Require="true" dataType="integer" msg="积分钱包充值下限只能为正整数！"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" style="width:20%;">单程票发售钱包充值下限(张):</td>
                        <td class="table_edit_tr_td_input">
                            <input name="d_under_sjt" type="text" id="d_under_sjt" size="10" maxlength="8"  Require="true" dataType="integer" msg="单程票发售钱包充值下限只能为正整数！"/>
                        </td>
                        <td class="table_edit_tr_td_label" style="width:20%;">储值卡钱包充值下限(分):</td>
                        <td class="table_edit_tr_td_input">
                            <input name="d_under_amount" type="text" id="d_under_amount" size="10" maxlength="8"  Require="true" dataType="integer" msg="储值卡钱包充值下限只能为正整数！"/>
                        </td>
                        <td class="table_edit_tr_td_label" style="width:20%;">积分钱包充值上限(次):</td>
                        <td class="table_edit_tr_td_input">
                            <input name="d_upper_count" type="text"  operator="GreaterThanEqual" to="d_under_count" id="d_upper_count" size="10" maxlength="8"  Require="true" dataType="integer|CompareNum" msg="积分钱包充值上限只能正整数并大于下限次数！"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" style="width:20%;">单程票发售钱包充值上限(张):</td>
                        <td class="table_edit_tr_td_input">
                            <input name="d_upper_sjt" type="text"  operator="GreaterThanEqual" to="d_under_sjt" id="d_upper_sjt" size="10" maxlength="8"  Require="true" dataType="integer|CompareNum" msg="单程票发售钱包充值上限只能正整数并大于下限次数！"/>
                        </td>
                        <td class="table_edit_tr_td_label" style="width:20%;">储值卡钱包充值上限(分):</td>
                        <td class="table_edit_tr_td_input">
                            <input name="d_upper_amount" type="text"  operator="GreaterThanEqual" to="d_under_amount" id="d_upper_amount" size="10" maxlength="8"  Require="true" dataType="integer|CompareNum" msg="储值卡钱包充值上限只能为正整数并大于下限次数！"/>
                        </td>
                        <td class="table_edit_tr_td_label" style="width:20%;"><!--操作员代码:--></td>
                        <td class="table_edit_tr_td_input">
                            <!--<input type="text" id="d_oper_id" name="d_oper_id" size="10" maxlength="6"  dataType="Require" msg="操作员代码不能为空！"/>-->
                        </td>
                    </tr>
                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="clone" scope="request" value="1"/>
            <c:set var="submit" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_line_id#d_station_id');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
    </body>
</html>
