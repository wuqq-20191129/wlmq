<%-- 
    Document   : tvm_stop_sell_config
    Created on : 2017-6-11, 14:24:29
    Author     : zhouyang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TVM停售时间配置参数</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript">
				
                    function selectedRow(thisObject) { 
                    document.getElementById('flow_id').value=thisObject;
                    }
                </script>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="
                    initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_timeTableID');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="
                    initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del;submit1');
                    setPrimaryKeys('detailOp', 'd_timeTableID');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:otherwise>
        </c:choose>
        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">TVM停售时间配置参数（
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


        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="TvmStopSellConfig">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" style="width: 80px">开始时间:</td>
                    <td class="table_edit_tr_td_input" style="width: 120px">
                        <input type="text" name="q_beginDate" id="q_beginDate" size="8" require="false"  
                                dataType="ICCSDate|LimitB"  maxLength="10" msg="开始时间格式为[yyyy-mm-dd]"/>
                            <a href="javascript:openCalenderDialogByID('q_beginDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                            </a>
                    </td>
                    <td class="table_edit_tr_td_label" style="width: 80px"><div align="right">结束时间:</div></td>
                    <td class="table_edit_tr_td_input" style="width: 120px">
                        <input type="text" name="q_endDate" id="q_endDate" size="8" require="false"  
                                dataType="ICCSDate|ThanDate|LimitB"  maxLength="10" to="q_beginDate" msg="结束时间格式为[yyyy-mm-dd]且要大于或等于开始时间!"/>
                        <a href="javascript:openCalenderDialogByID('q_endDate','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label" style="width: 80px">时间表类型:</td>
                    <td class="table_edit_tr_td_input" style="width: 120px">
                        <select id="q_timeTableID" name="q_timeTableID" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_timeTableId" />
                        </select>
                    </td>
                    
                    <td class="table_edit_tr_td_label" rowspan="2" style="width: 60px">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_beginDate#q_endDate#q_timeTableID');"/>
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
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead">

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td align="center" class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td align="center" id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">时间表类型</td>
                        <td align="center" id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">开始时间</td>
                        <td align="center" id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">结束时间</td>
                        <td align="center" id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true  index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">版本号</td>
                        <td align="center" id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">参数类型</td>
                    </tr>
                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');selectedRow(${rs.flow_id});setPageControl('detailOp');" 
                            id="${rs.begin_date}#${rs.end_date}#${rs.flow_id}#${rs.timeTable_id}#${rs.version_no}#${rs.record_flag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.flow_id}">
                                </input>
                            </td>
                            <td  id="timeTableID" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.timeTable_id_name}
                            </td>
                            <td  id="beginDate" class="table_list_tr_col_data_block"style="width: 120px">
                                ${rs.begin_date}
                            </td>
                            <td  id="endDate" class="table_list_tr_col_data_block"style="width: 120px">
                                ${rs.end_date}
                            </td>
                            <td  id="versionNo" class="table_list_tr_col_data_block"style="width: 100px">
                                ${rs.version_no}
                            </td>
                            <td  id="recordFlag" class="table_list_tr_col_data_block"style="width: 100px">
                                ${rs.record_flag_name}
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

        <FORM method="post" name="detailOp" id="detailOp" action="TvmStopSellConfig" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <input type="hidden" name="flow_id" id="flow_id" />
                        <td class="table_edit_tr_td_label" style="width:70px">时间表类型:</td>
                        <td class="table_edit_tr_td_input" style="width:100px">
                            <select id="d_timeTableID" name="d_timeTableID" require="true" dataType="LimitB" min="1" msg="没有选择时间表类型" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_timeTableId" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label" style="width:70px">开始时间:</td>
                        <td class="table_edit_tr_td_input" style="width:150px">
                            <input type="text" name="d_beginDate" id="d_beginDate" size="20" require="true"  
                                    dataType="ICCSDateTime|LimitB" min="1" msg="开始时间格式为[YYYY-MM-dd hh:mm:ss],且不能为空"/>
                                <a href="javascript:openDetailCalenderDialogByID('d_beginDate','false');">
                                    <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                                </a>
                        </td>
                        <td class="table_edit_tr_td_label" style="width:70px"><div align="right">结束时间:</div></td>
                        <td class="table_edit_tr_td_input" style="width:150px">
                            <input type="text" name="d_endDate" id="d_endDate" size="20" require="true"  
                                    dataType="ICCSDateTime|thanDate|LimitB" to="d_beginDate" min="1" msg="结束时间格式为[YYYY-MM-dd hh:mm:ss]，不能为空且要大于开始时间"/>
                            <a href="javascript:openDetailCalenderDialogByID('d_endDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                            </a>
                        </td>
                        
                    </tr>
                </table>
            </DIV>

            <input type="hidden" name="expAllFields" id="d_expAllFields" value="timeTable_id_name,begin_date,end_date,version_no,record_flag_name" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="TVM停售时间配置参数.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TvmStopSellConfigExportAll" />
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
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

            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','timeTable_id');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        
    </body>
</html>
