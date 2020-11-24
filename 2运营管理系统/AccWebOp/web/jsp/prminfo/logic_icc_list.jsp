<%-- 
    Document   : logic_icc_list
    Created on : 2017-6-22
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>逻辑卡号刻印号对照表</title>

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript" >
            function MM_openBrWindow(theURL,winName,features) {
            	window.open(theURL,winName,features);
            }
            
            function OpenDialog() {
                alert(123456);
                var openUrl ='phyLogicList?command=import';
                openwindow(openUrl,'',600,250);
               // window.open(openUrl,'','width=756,height=430,scrollbars=yes,resizable=yes');
            }
        </script>
    </head>
    <body onload="preLoadVal('q_begin_time' , 'q_end_time');
                initDocument('detailOp','detail');
                setControlsDefaultValue('queryOp');
                setQueryControlsDefaultValue('queryOp', 'detailOp');
                setListViewDefaultValue('detailOp', 'clearStart');
                setPageControl('detailOp');
                setTableRowBackgroundBlock('DataTable');">
                        
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">逻辑卡号刻印号对照表</td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="logicIccList">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">开始时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_begin_time" name="q_begin_time" size="10"  require="false" dataType="Date" format="ymd" msg="开始时间格式为(yyyy-mm-dd)!"/>
                        <a href="javascript:openCalenderDialog(document.all.q_begin_time);">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>	
                    </td>
                    <td class="table_edit_tr_td_label">结束时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_end_time" name="q_end_time" size="10"  require="false" dataType="Date|ThanDate" format="ymd" to="q_begin_time"  msg="结束时间格式为(yyyy-mm-dd)且大于登录时间!"/>
                        <a href="javascript:openCalenderDialog(document.all.q_end_time);">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    
                    <td class="table_edit_tr_td_label">文件名:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_file_name" id="q_file_name" size="10" require="false" dataType="NotEmpty"   msg="文件名不能为空" />
                    </td>
                    <td class="table_edit_tr_td_label">操作员:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_operator_id" name="q_operator_id">				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_operator" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">开始逻辑卡号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_begin_logical" id="q_begin_logical" maxlength="20" size="20"  require="false"  dataType="integer"  msg="逻辑卡号应为数字字符。"/>
                    </td>
                    <td class="table_edit_tr_td_label">结束逻辑卡号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_end_logical" id="q_end_logical" maxlength="20"  size="20"  require="false"  dataType="integer"  msg="逻辑卡号应为数字字符。"/>
                    </td>
                  
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_begin_time#q_end_time#q_file_name#q_operator_id#q_begin_logical#q_end_logical');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />
        </form>
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
                    	
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="0" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">流水号</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">操作员</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="2" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">操作时间</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="3" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">文件名</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="4" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">开始逻辑卡号</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="5" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">结束逻辑卡号</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="6" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">记录数</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="7" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">备注</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="8" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">明细</td>
                </tr>
	    </table>
        </div>
	<div id="clearStart"  class="divForTableBlockData">
	     <table class="table_list_block" id="DataTable" >
                <c:forEach items="${ResultSet}" var="rs">
                    <tr class="table_list_tr_data" 
                        onMouseOver="overResultRow('detailOp', this);" 
                        onMouseOut="outResultRow('detailOp', this);" >
                        
			
                        <td id="physic_no" class="table_list_tr_col_data_block" style="width: 80px">
                            ${rs.water_no}
                        </td>
                        <td id="operator_id" class="table_list_tr_col_data_block" style="width: 80px">
                            ${rs.operator_name}
                        </td>
                        <td id="op_time" class="table_list_tr_col_data_block" style="width: 120px">
                            ${rs.op_time}
                        </td>
                        <td id="file_name" class="table_list_tr_col_data_block" style="width: 120px">
                            ${rs.file_name}
                        </td>
                        <td id="begin_logical_id" class="table_list_tr_col_data_block" style="width: 120px">
                            ${rs.begin_logical_id}
                        </td>
                        <td id="end_logical_id" class="table_list_tr_col_data_block" style="width: 120px">
                            ${rs.end_logical_id}
                        </td>
                        <td id="record_count" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.record_count}
                        </td>
                        <td id="remark" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.remark}
                        </td>
                        <td  id="openBrWindow" class="table_list_tr_col_data_block" style="width: 80px">
                            <a href="#" onClick="MM_openBrWindow('logicIccList?type=null&ModuleID=${ModuleID}&command=detail&waterNo=${rs.water_no}','物理逻辑卡号查看操作','scrollbars=yes,resizable=yes,width=800,height=500')">查看</a>
                            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
                        </td>	
                    </tr>
                </c:forEach>
            </table>
	</DIV>
    </div>	
    
    <!-- 表头 通用模板 -->
        <FORM method="post" name="detailOp" id="detailOp" action="logicIccList">
            <input type="hidden" name="importUrl" id="importUrl" value="logicIccList?command=import" />
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getWater_no,getOperator_id,getOp_time,file_name,getBegin_logical_id,getEnd_logical_id,getRecord_count,getRemark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="逻辑卡号刻印号对照表.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/PhyLogicListExportAll"/>
            
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>
            <c:set var="import_1" scope="request" value="1"/>	
            
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
    </body>
</html>
       
