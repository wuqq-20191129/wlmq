<%-- 
    Document   : taskConfig
    Created on : 2017-9-14, 15:34:06
    Author     : ysw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>系统管理 任务日志</title>

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>  
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icProduceOut.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/icStorageProductBill.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>

    </head>

    <body onload="
            setDiffTimeToCurTime('q_startTime', 15);
            initDate('q_endTime');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable');">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">系统管理 任务日志</td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="taskLog">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">                                      
                    <td class="table_edit_tr_td_label">任务名:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_taskName" id="q_taskName" size="25" maxlength="50" require="false" dataType="string" msg="任务名应为字母或数字"/>                                                          
                    </td>

                    <td class="table_edit_tr_td_label">开始时间:</td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text" name="q_startTime" id="q_startTime" require="false" dataType="ICCSDateTime" value="" size="19"   format="ymd"  maxlength="19"
                               msg="请选择开始时间，日期格式为(YYYY-MM-dd hh:mm:ss)"/>
                        <a href="javascript:openDetailCalenderDialogByID('q_startTime','false');">
                            <img src="./images/calendar.gif"  border="0" style="block"/>
                        </a>
                    </td>

                </tr>
                <tr class="table_edit_tr">  
                    <td class="table_edit_tr_td_label">运行结果:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_taskResult" name="q_taskResult" >
                            <option value="">=请选择=</option>
                            <option value ="0">正常结束</option>
                            <option value ="1">异常结束</option>
                            <option value ="2">线程挂起</option>                                           
                        </select>                        
                    </td>
                    <td class="table_edit_tr_td_label">结束时间:</td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text" name="q_endTime" id="q_endTime" require="false" dataType="ICCSDateTime|ThanDate|dateDiff"  size="19" to="q_startTime" maxlength="19"
                               msg="请选择结束时间，格式为(YYYY-MM-dd hh:mm:ss)且大于等于开始时间，查询范围为90天!" />
                        <a href="javascript:openDetailCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif"   border="0" style="block"/>
                        </a>
                    </td> 
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="removeBlankSpaceBeforeQuery('queryOp',['q_taskName']);setControlNames('queryOp','q_taskName#q_taskResult#q_startTime#q_endTime');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>
        </form>

        <!-- 表头 通用模板 -->
        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead" style="width:1600px">
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAll('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">任务名</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 400px">任务名描述</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">开始时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">结束时间</td>                         
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">运行结果</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 400px">结果描述</td>                         
                    </tr>
                </table>
            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData" style="width:1600px">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">                 
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickOneResultRow('detailOp', this, '','rectNo');setPageControl('detailOp');"
                            id="${rs.taskName}#${rs.endTime}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" 
                                       value="${rs.taskName}" >
                                </input>
                            </td>                       
                            <td  id="taskName" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.taskName}
                            </td>
                            <td  id="taskDesc" class="table_list_tr_col_data_block" style="width: 400px">
                                ${rs.taskDesc}
                            </td>
                            <td  id="startTime" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.startTime}
                            </td> 
                            <td  id="endTime" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.endTime}
                            </td>
                            <td  id="taskResult" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.resultName}
                            </td> 
                            <td  id="resultDesc" class="table_list_tr_col_data_block" style="width: 400px">
                                ${rs.resultDesc}
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

        <FORM method="post" name="detailOp" id="detailOp" action="taskLog" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getTaskName,getTaskDesc,getStartTime,getEndTime,getResultName,getResultDesc" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="任务日志.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TaskLogExportAll"/>
            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">                    
                </table>
            </div>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" /> 
            <c:set var="expAll" scope="request" value="1" />
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

