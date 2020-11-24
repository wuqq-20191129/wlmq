<%-- 
    Document   : dev_para_ver_qry
    Created on : 2017-6-23, 15:07:14
    Author     : mh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>设备参数版本查询请求</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="JavaScript">
            function MM_openBrWindow(theURL,winName,features) {
            window.open(theURL,winName,features);
            }
        </script>
    </head>
    <!--删除 hejj setInvisable('detailOp','clone'); 
    -->
    <body onload="preLoadVal('q_beginDatetime', 'q_endDatetime');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')"> 
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">参数 设备参数版本查询请求</td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="DevParaVerQry">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">设备类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_devTypeId" name = "q_devTypeId">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fildevtype" />
                        </select>
                    </td> 
<!--                    <td class="table_edit_tr_td_label">LCC线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_lccLineId" name = "q_lccLineId">
                            c:import url="/jsp/common/common_template.jsp?template_name=option_list_lcc_line" />
                        </select>
                    </td>-->
                    <td class="table_edit_tr_td_label" >线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_lineId" name="q_lineId" onChange="setSelectValues('queryOp', 'q_lineId', 'q_stationId', 'commonVariable');" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">车站:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_stationId" name="q_stationId" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label">设备ID:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_deviceId" id="q_deviceId" style="width:80px;"  maxLength="3" require="false" dataType="integer|LimitB" min="3" max="3" msg="设备ID应为3位数字"/>
                    </td>
                </tr>

                <tr class="table_edit_tr">
                    
                    <td class="table_edit_tr_td_label">请求状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_statusId" name = "q_statusId">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_status" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">查询开始时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" id="q_beginDatetime" name="q_beginDatetime" style="width:90px;"  size="15"  dataType="ICCSDate"  msg="开始日期格式为[yyyy-mm-dd]"/>
                        <a href="javascript:openCalenderDialogByID('q_beginDatetime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label">查询结束时间:</td>
                    <td class="table_edit_tr_td_input">    
                        <input type="text" id="q_endDatetime" name="q_endDatetime"  style="width:90px;"  size="15"  dataType="ICCSDate|ThanDate" to="q_beginDatetime" msg="结束日期格式为[yyyy-mm-dd]且大于等于开始日期"/>
                        <a href="javascript:openCalenderDialogByID('q_endDatetime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_beginDatetime#q_endDatetime#q_devTypeId#q_lineId#q_stationId#q_deviceId#q_statusId');setLineCardNames('queryOp','q_lineId','q_stationId','commonVariable')"/>
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
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:50px;">ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width:80px;">线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width:75px;">车站</td>
<!--                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:100px;" >LCC线路</td>-->
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true  index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:90px;">请求状态</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width:140px;">查询时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width:140px;">处理时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width:90px;">操作员ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="11"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:125px;">备注</td>
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
                            onclick="setSelectValuesByRow('detailOp', 'd_stationId', 'commonVariable');
                                clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');"
                                    id="${rs.line_id}#${rs.station_id}#${rs.water_no}#${rs.dev_type_id}#${rs.device_id}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.water_no}">
                                </input>
                            </td>
                            <td  id="waterNo" class="table_list_tr_col_data_block" style="width:50px;">
                                ${rs.water_no}
                            </td>
                            <td  id="lineId" class="table_list_tr_col_data_block" style="width:80px;">
                                ${rs.lineText}
                            </td>
                            <td  id="stationId" class="table_list_tr_col_data_block" style="width:75px;">
                                ${rs.stationText}
                            </td>
<!--                            <td  id="lccLineId" class="table_list_tr_col_data_block" style="width:100px;">
                                {rs.lccText}
                            </td>-->
                            <td  id="devTypeId" class="table_list_tr_col_data_block">
                                ${rs.devTypeText}
                            </td>
                            <td  id="deviceId" class="table_list_tr_col_data_block">
                                ${rs.device_id}
                            </td>
                            <td  id="statusId" class="table_list_tr_col_data_block" style="width:90px;">
                                ${rs.statusText}
                            </td>
                            <td  id="queryDate" class="table_list_tr_col_data_block" style="width:140px;">
                                ${rs.query_date}
                            </td>
                            <td  id="sendDate" class="table_list_tr_col_data_block" style="width:140px;">
                                ${rs.send_date}
                            </td>
                            <td  id="operatorId" class="table_list_tr_col_data_block" style="width:90px;">
                                ${rs.operator_id}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width:125px;">
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

        <FORM method="post" name="detailOp" id="detailOp" action="DevParaVerQry">
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">设备类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id = "d_devTypeId" name = "d_devTypeId">
                                <c:import url="/jsp/common/common_template.jsp?template_name=all_option_list_pub_filDevtype" />
                            </select>
                        </td> 
<!--                        <td class="table_edit_tr_td_label">LCC线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id = "d_lccLineId" name = "d_lccLineId">
                                c:import url="/jsp/common/common_template.jsp?template_name=option_list_lcc_line" />
                            </select>
                        </td>-->
                        <td class="table_edit_tr_td_label" >线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_lineId" name="d_lineId" onChange="setSelectValues('detailOp','d_lineId','d_stationId','commonVariable');">				    
                                <c:import url="/jsp/common/common_template.jsp?template_name=all_option_list_pub_line" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">车站:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_stationId" name="d_stationId">
                                <c:import url="/jsp/common/common_template.jsp?template_name=all_option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">设备ID:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_deviceId" id="d_deviceId" size="20" require="false"  style="width:75px;"maxLength="3" dataType="integer|LimitB|NotEmpty" min="3" max="3" msg="设备ID应为3位数字！" value="000"/>
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"
                                 onclick="openWindowDeviceId('${devIdList}','DevIdList?type=null&ModuleID=040402','0305',600,600);"/>
                        </td>
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_remark" id="d_remark" size="15" maxLength="50" require="true" msg="备注最大值不能超过50位(中文字符为两位)！"/>
                        </td>
                    </tr>
                </table>
            </DIV>

            <input type="hidden" name="expAllFields" id="d_expAllFields" value="WATER_NO,LINE_TEXT,STATION_TEXT,DEV_TYPE_TEXT,DEVICE_ID,STATUS_TEXT,QUERY_DATE,SEND_DATE,OPERATOR_ID,REMARK" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="设备参数版本查询请求.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/DevParaVerQryExportAll" />
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
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

