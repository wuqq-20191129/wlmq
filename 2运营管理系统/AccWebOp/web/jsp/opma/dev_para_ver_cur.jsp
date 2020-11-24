<%-- 
    Document   : DevParaVerCur
    Created on : 2017-6-22, 19:01:09
    Author     : zhouyang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>参数 设备参数版本查询结果</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="JavaScript">
            function setDeviceId() {
                document.getElementById("device_id").value="";
            }
            function setEffect() {
                document.getElementById("q_isEffect").value="1";
            }
            function setRecordFlag() {
                document.getElementById("q_recordFlag").value="0";
            }
        </script>
    </head>
    
    <body onload="setEffect();setRecordFlag();
            preLoadVal('q_beginDate', 'q_endDate');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable');
            "> 
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    参数 设备参数版本查询结果
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="DevParaVerCur">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" style="width: 70px">设备版本标志:</td>
                    <td class="table_edit_tr_td_input" style="width: 100px">
                        <select id="q_recordFlag" name="q_recordFlag">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_devRecordFlags"/>
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" style="width: 80px">是否生效:</td>
                    <td class="table_edit_tr_td_input" style="width: 100px">
                        <select id="q_isEffect" name="q_isEffect" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_isEffect" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" style="width: 80px">参数类型:</td>
                    <td class="table_edit_tr_td_input" style="width: 100px">
                        <select id="q_parmTypeId" name="q_parmTypeId">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_parmTypeIds" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" style="width: 70px">报告开始日期:</td>
                    <td class="table_edit_tr_td_input" style="width: 100px">
                        <input type="text" name="q_beginDate" id="q_beginDate" size="8" require="true"  
                                dataType="ICCSDate|LimitB"  maxLength="10" msg="报告开始日期格式为[yyyy-mm-dd]"/>
                            <a href="javascript:openCalenderDialogByID('q_beginDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                            </a>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" style="width: 70px">线路:</td>
                    <td class="table_edit_tr_td_input" style="width: 100px" onclick="setDeviceId()">
                        <select id="q_lineID" name="q_lineID" onChange="setSelectValues('queryOp', 'q_lineID', 'q_stationID', 'commonVariable');setLineCardNames('queryOp','q_lineID','q_stationID','commonVariable','','','');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" style="width: 80px">车站:</td>
                    <td class="table_edit_tr_td_input" style="width: 100px" onclick="setDeviceId()">
                        <select id="q_stationID" name="q_stationID" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label" style="width: 80px">设备类型:</td>
                    <td class="table_edit_tr_td_input" style="width: 100px" onclick="setDeviceId()">
                        <select id="q_deviceType" name="q_deviceType" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fildevtype" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" style="width: 70px">报告结束日期:</td>
                    <td class="table_edit_tr_td_input"  style="width: 100px">
                        <input type="text" name="q_endDate" id="q_endDate" size="8" require="true"  
                                dataType="ICCSDate|ThanDate|LimitB"  maxLength="10" to="q_beginDate" msg="报告结束日期格式为[yyyy-mm-dd]且要大于或等于报告开始日期!"/>
                        <a href="javascript:openCalenderDialogByID('q_endDate','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    
                    <td class="table_edit_tr_td_label" style="width: 70px">设备ID:</td>
                    <td class="table_edit_tr_td_input" style="width: 100px">
                        <input type="text" name="device_id" id="device_id" size="3" require="false"  
                                maxlength="3" dataType="LimitB|Number"  min = "3" max="3" msg="设备ID为3位数字"/>
                        <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"
                             onclick="openWindowDialogDevId('${devIdList}','DevIdList?type=null&ModuleID=040402','0305',600,600);"/>
                        
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2" style="width: 80px">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_recordFlag#q_isEffect#q_parmTypeId#q_beginDate#q_endDate#q_lccLineId#q_lineID#q_stationID#q_deviceType#device_id');"/>
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">设备类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 60px">设备ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 160px">参数类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">设备版本标志</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">下发版本号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">返回版本号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">报告日期</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">备注</td>
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
                            id="${rs.code}" >
                            <td  id="lineId" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.line_name}
                            </td>
                            <td  id="stationId" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.station_name}
                            </td>
                            <td  id="devTypeId" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.dev_type_name}
                            </td>
                            <td  id="deviceId" class="table_list_tr_col_data_block" style="width: 60px">
                                ${rs.device_id}
                            </td>
                            <td  id="parmTypeId" class="table_list_tr_col_data_block" style="width: 160px">
                                ${rs.parm_type_name}
                            </td>
                            <td  id="recordFlag" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.record_flag_name}
                            </td>
                            <td  id="versionNew" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.version_new}
                            </td>
                            <td  id="versionNo" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.version_no}
                            </td>
                            <td  id="reportDate" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.report_date}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.remark}
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

        <FORM method="post" name="detailOp" id="detailOp" action="DevParaVerCur">
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="line_name,station_name,dev_type_name,device_id,parm_type_name,record_flag_name,version_new,version_no,report_date,remark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="设备参数版本查询结果.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/DevParaVerCurExportAll" />
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
