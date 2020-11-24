<%-- 
    Document   : cmClearLog
    Created on : 2017-9-14, 10:54:50
    Author     : chenzx
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>数据交换系统</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>  
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    
    <body onload="
            initDate('q_begin_clear_datetime');
            initDate('q_end_clear_datetime');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable');
            ">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">数据交换系统 清理日志
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="cmClearLog">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">

                    <td class="table_edit_tr_td_label" style="width: 60px">源表表名:</td>
                    <td class="table_edit_tr_td_input" style="width: 180px">
                        <input type="text" name="q_origin_table_name" id="q_origin_table_name" size="35" require="false" maxLength="40" dataType="NotEmpty"  msg="源表名" />
                    </td>
                    <td class="table_edit_tr_td_label" style="width: 60px">开始时间:</td>
                    <td class="table_edit_tr_td_input" style="width: 120px">
                        <input type="text" dataType="ICCSDateTime" name="q_begin_clear_datetime"  id="q_begin_clear_datetime" size="17" require="true" 
                               msg="请选择开始时间，日期格式为(YYYY-MM-dd hh:mm:ss)" />
                        <a href="javascript:openDetailCalenderDialogByID('q_begin_clear_datetime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label" style="width: 60px">结束时间:</td>
                    <td class="table_edit_tr_td_input" style="width: 120px">
                        <input type="text" dataType="ICCSDateTime|ThanDate|dateDiff" name="q_end_clear_datetime"  id="q_end_clear_datetime" size="17" require="true" 
                               msg="请选择结束时间，格式为(YYYY-MM-dd hh:mm:ss)且大于等于开始时间，查询范围为90天!" to="q_begin_clear_datetime" />
                        <a href="javascript:openDetailCalenderDialogByID('q_end_clear_datetime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="1">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_origin_table_name#q_begin_clear_datetime#q_end_clear_datetime')"/>
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
        <div id="clearStartBlock" class="divForTableBlock" >
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">源表表名</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">开始时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 130px">结束时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 400px">信息</td>
                    </tr>

                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--
                        setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                        -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, '');" 
                            id="${rs.id}" >
                            
                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.origin_table_name}">
                                </input>
                            </td>

                            <td  id="originTableName" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.origin_table_name}
                            </td>
                            <td  id="beginClearDatetime" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.begin_clear_datetime}
                            </td>
                            <td  id="endClearDatetime" class="table_list_tr_col_data_block" style="width: 130px">
                                ${rs.end_clear_datetime}
                            </td>
                            <td  id="clearRecdCount" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.clear_recd_count}
                            </td>
                            <td  id="errDiscribe" class="table_list_tr_col_data_block" style="width: 400px">
                                ${rs.err_discribe}
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

        <FORM method="post" name="detailOp" id="detailOp" action="cmClearLog" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"  value="getOrigin_table_name,getBegin_clear_datetime,getEnd_clear_datetime,getClear_recd_count,getErr_discribe" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="数据交换系统 清理日志.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl"  value="/CmClearLogExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">                  
                </table>
            </div>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="expAll" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
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
