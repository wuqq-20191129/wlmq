<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>日志查询</title>
         <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript">

           
            
           
            </script>
    </head>
   <body onload="preLoadVal('q_beginDatetime', 'q_beginDate');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    日志查询 报表生成日志查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="ReporBuildLog">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                     <td class="table_edit_tr_td_label">
                               报表模板:
                            </td>
             
              	<td class="table_edit_tr_td_input">   
                      <select id="q_rpModule" name="q_rpModule" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_reportModule" />
                        </select>
                     
                    </td>   
                        <td class="table_edit_tr_td_label">
                               报表代码:
                            </td>
             
              	<td class="table_edit_tr_td_input">   
                      <select id="q_rpCode" name="q_rpCode" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_reportCode" />
                        </select>
                      
                    </td>  
                     <td class="table_edit_tr_td_label" >
                                清算流水号:
                            </td>
                            <td class="table_edit_tr_td_input">
                                
                                    <input type="text" name="q_balanceWaterNo" id="q_balanceWaterNo" size="20"  maxlength="10"  require="false"  dataType="Require|integer|LimitB"  min="10" max="10"   msg="清算流水号为10位数字"   />
                               
                            </td>
                 <td class="table_edit_tr_td_label" >生成日期:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="hidden" name="q_beginDatetime" id="q_beginDatetime" value="" />
                        <input type="text" name="q_beginDate" id="q_beginDate" size="8" require="yes"  
                                dataType="ICCSDate|LimitB"  maxLength="10" msg="日期格式为[yyyy-mm-dd]"/>
                            <a href="javascript:openCalenderDialogByID('q_beginDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                            </a>
                    </td>
                    
                </tr>
                 <tr class="table_edit_tr">
                      <td class="table_edit_tr_td_label" >
                                报表大小(<=):
                            </td>
                            <td class="table_edit_tr_td_input">
                                
                                    <input type="text" name="q_rpSize" id="q_rpSize" size="10"  maxlength="10"  require="false"  dataType="Require|integer|LimitB"  min="1" max="10"   msg="报表大小为数字"   />
                               
                            </td>
                     <td class="table_edit_tr_td_label" style="text-algin:left">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_rpModule#q_rpCode#q_balanceWaterNo#q_beginDate#q_rpSize');
                               "/>
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
                    <td id="orderTd"  style="width: 100px"  class="table_list_tr_col_head_block"  isDigit=true index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >报表代码</td>
                    <td id="orderTd"  style="width: 200px"  class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >报表文件名</td>
                    <td id="orderTd"  style="width: 100px"  class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >报表模板</td>
                    <td id="orderTd"  style="width: 200px"  class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >报表名称</td>
                    <td id="orderTd"  style="width: 100px"  class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >报表大小(k)</td>
                    <td id="orderTd"  style="width: 120px"  class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >生成时间</td>
                    <td id="orderTd"  style="width: 90px"  class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >花费时间(秒)</td>
                    <td id="orderTd"  style="width: 90px"  class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >清算流水号</td>
                    <td id="orderTd"  style="width: 100px"  class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >报表流水号</td>
                    
                </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >               
                <c:forEach items="${ResultSet}" var="rs">

                    <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                        onMouseOut="outResultRow('detailOp', this);" 
                        onclick="clickResultRow('detailOp', this, '');
			setPageControl('detailOp');" 
                         id="${rs.waterNo}}#${rs.reportCode}}" >
                        
                           <td  id="reportCode" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.reportCode}
                        </td>
                        
                        <td  id="reportName" class="table_list_tr_col_data_block" style="width: 200px">
                            ${rs.reportName}
                        </td>
                        <td  id="reportModule" class="table_list_tr_col_data_block" style="width: 100px"> 
                            ${rs.reportModule}
                        </td>
                        <td  id="reportModuleName" class="table_list_tr_col_data_block" style="width: 200px"> 
                            ${rs.reportModuleName}
                        </td>
                        <td  id="reportSize" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.reportSize}
                        </td>
                        <td  id="genStartTime" class="table_list_tr_col_data_block" style="width: 120px">
                            ${rs.strGenStartTime}
                        </td>
                        <td  id="useTime" class="table_list_tr_col_data_block" style="width: 90px"> 
                            ${rs.useTime}
                        </td>
                        <td  id="balanceWaterNo" class="table_list_tr_col_data_block" style="width: 90px">
                            ${rs.balanceWaterNo}
                        </td>
                        <td  id="reportWaterNo" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.reportWaterNo}
                        </td>
                       
                       
                    </tr>

                </c:forEach>

            </table>
            </div>
        </div>

        <FORM method="post" name="detailOp" id="detailOp" action="ReporBuildLog" >
            <input type="hidden" name="expAllTableHeadWidth" id="expAllTableHeadWidth" value=""/>
           <input type="hidden" name="expAllTableHeadName" id="expAllTableHeadName" value=""/>
            
            <input type="hidden" name="expAllFields" id="expAllFields" value="REPORT_CODE,REPORT_NAME,REPORT_MODULE,REPORT_MODULE_NAME,REPORT_SIZE,GEN_START_TIME,USE_TIME,BALANCE_WATER_NO,REPORT_WATER_NO"/>            
            <input type="hidden" name="expFileName" id="expFileName" value="报表生成日志查询.xlsx"/>
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/ReporBuildLogExportAll" />
            
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
             
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

