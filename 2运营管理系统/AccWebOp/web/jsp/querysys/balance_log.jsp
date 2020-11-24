<%-- 
    Document   : balance_log
    Created on : 2017-6-16, 10:46:20
    Author     : zhouyang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>日志查询——清算日志</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <!--删除 hejj setInvisable('detailOp','clone');  -->
    <body onload="
            preLoadVal('q_beginDate', 'q_endDate');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setPrimaryKeys('detailOp', 'd_cardLogicalId');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')"> 
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    日志查询 清算日志
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="BalanceLog">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >开始时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginDate" id="q_beginDate" size="8" require="true"  
                                dataType="ICCSDate"  maxLength="10" msg="开始日期格式为[yyyy-mm-dd]"/>
                            <a href="javascript:openCalenderDialogByID('q_beginDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                            </a>
                    </td>
                    <td class="table_edit_tr_td_label" >结束时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_endDate" id="q_endDate" size="8" require="true"  
                                dataType="ICCSDate|ThanDate|LimitB"  maxLength="10" to="q_beginDate" msg="结束时间格式为[yyyy-mm-dd]且需大于或等于开始时间!"/>
                        <a href="javascript:openCalenderDialogByID('q_endDate','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label" width="10%">错误级别:</td>
                    <td class="table_edit_tr_td_input" width="20%">
                        <select id="q_errLevel" name="q_errLevel" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_errLevel" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_beginDate#q_endDate#q_errLevel');"/>
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px" >流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px" >清算流水号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px" >操作对象</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px" >错误级别</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 325px" >备注</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px" >操作员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 170px" >生成时间</td>
                    </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--删除 hejj setSelectValuesByRow('detailOp', 'd_cardLogicalId', 'commonVariable'); -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);"
                            onclick="clickResultRow('detailOp', this, '');
                                    setPageControl('detailOp');" 
                            id="${rs.water_no}">
                            <td  id="waterNo" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.water_no}
                            </td>
                            <td  id="balanceWaterNo" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.balance_water_no}
                            </td>
                            <td  id="operObject" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.oper_object}
                            </td>
                            <td  id="errLevel" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.err_level_name}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 325px">
                                ${rs.remark}
                            </td>
                            <td  id="operatorId" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.operator_id}
                            </td>
                            <td  id="genDatetime" class="table_list_tr_col_data_block" style="width: 170px">
                                ${rs.date}
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

        <FORM method="post" name="detailOp" id="detailOp" action="BalanceLog">
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="water_no,balance_water_no,oper_object,err_level_name,remark,operator_id,gen_datetime" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="清算日志.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/BalanceLogExportAll" />
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
