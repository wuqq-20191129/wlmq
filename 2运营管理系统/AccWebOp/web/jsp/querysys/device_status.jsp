<%-- 
    Document   : device_status
    Created on : 2017-11-27, 17:37:50
    Author     : zhouyang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>设备查询 设备状态查询</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        
    </head>
    
    <body onload="
            preLoadVal('q_beginDate', 'q_endDate');
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
                    设备查询 设备状态查询
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="deviceStatus">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_lineID" name="q_lineID" dataType="NotEmpty" msg="线路不能为空"
                                onChange="setSelectValues('queryOp', 'q_lineID', 'q_stationID', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">车站:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_stationID" name="q_stationID" dataType="NotEmpty" msg="车站不能为空">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label" width="10%">设备类型:</td>
                    <td class="table_edit_tr_td_input" width="20%">
                        <select id="q_devTypeId" name="q_devTypeId" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_devtype" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >设备ID:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_deviceId" id="q_deviceId" maxlength="3" size="10" require="false"  dataType="Number"   msg="设备ID应为数字!" />
                    </td>
                    <td class="table_edit_tr_td_label" >状态ID:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_statusId" id="q_statusId" maxlength="3" size="10" require="false"  dataType="Number"   msg="状态ID应为数字!" />
                    </td>
                    <td class="table_edit_tr_td_label" >ACC状态值:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_ACCStatusValue" name="q_ACCStatusValue" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_ACCStatusValue" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" width="10%">起始时间:</td>
                    <td class="table_edit_tr_td_input" width="20%">
                        <input type="text" name="q_beginDate" id="q_beginDate" size="8" require="true"  
                                dataType="Date|LimitB"  msg="起始时间格式为[yyyy-mm-dd]" maxLength="10"/>
                            <a href="javascript:openCalenderDialogByID('q_beginDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                            </a>
                    </td>
                    <td class="table_edit_tr_td_label" width="10%">终止时间:</td>
                    <td class="table_edit_tr_td_input" width="20%">
                        <input type="text" name="q_endDate" id="q_endDate" size="8" require="true"  
                            dataType="Date|ThanDate|CompareDateByNum" format="ymd" to="q_beginDate" operator="LessThanEqual" num="7"
                                           msg="终止时间格式为(yyyy-mm-dd)且大于起始时间不超过7天!" />
                        <a href="javascript:openCalenderDialogByID('q_endDate','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_lineID#q_stationID#q_devTypeId#q_deviceId#q_statusId#q_statusValue#q_ACCStatusValue#q_beginDate#q_endDate');setLineCardNames('queryOp','q_lineID','q_stationID','commonVariable','','','');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','');"/>
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">设备类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">设备ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">状态ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 300px">状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 90px">ACC状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">状态时间</td>
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
                            id="${rs.code}">
                            <td  id="line_id" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.lineName}
                            </td>
                            <td  id="station_id" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.stationName}
                            </td>
                            <td  id="dev_type_id" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.dev_type_name}
                            </td>
                            <td  id="device_id" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.device_id}
                            </td>
                            <td  id="status_id" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.status_id}
                            </td>
                            <td  id="status_value" class="table_list_tr_col_data_block" style="width: 300px">
                                ${rs.status_name}
                            </td>
                            <td  id="acc_status_value" class="table_list_tr_col_data_block" style="width: 90px">
                                ${rs.acc_status_name}
                            </td>
                            <td  id="status_datetime" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.status_datetime}
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

        <FORM method="post" name="detailOp" id="detailOp" action="deviceStatus">
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="lineName,stationName,dev_type_name,device_id,status_id,status_name,acc_status_name,status_datetime" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="设备状态查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/DeviceStatusExportAll" />
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
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        
    </body>
</html>