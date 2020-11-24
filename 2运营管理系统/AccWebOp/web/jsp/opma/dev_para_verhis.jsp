<%-- 
    Document   : dev_para_verhis
    Created on : 2017-6-21
    Author     : liudezeng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>设备参数查询历史</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>

        <script language="JavaScript">
//            function setDeviceId() {
//                document.getElementById("device_id").value="";
//            }
//            function setEffect() {
//                document.getElementById("q_isEffect").value="1";
//            }
            function setRecordFlag() {
                document.getElementById("q_recordFlag").value = "0";
            }
        </script>
    </head>
    <body onload="setRecordFlag();
            preLoadVal('q_b_time', 'q_e_time');
            initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')"> 
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    参数 设备参数查询历史
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="DevParaVerHis">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">参数类型ID:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_parmTypeId" name="q_parmTypeId" require="false">				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_parmTypeIds" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">设备版本标志:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_recordFlag" name="q_recordFlag" require="false">				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_devRecordFlags" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label" width="10%">报告开始日期:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_b_time" id="q_b_time" size="18" require="false"  
                               dataType="Date" format="ymd"  msg="开始时间格式不正确,格式为(yyyy-mm-dd)!"/>
                        <a href="javascript:openCalenderDialog(document.all.q_b_time);">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
                    </td>


                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_lineID" name="q_lineID"require="false" dataType="NotEmpty" msg="线路不能为空" onChange="setSelectValues('queryOp', 'q_lineID', 'q_stationID', 'commonVariable');" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">车站:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_stationID" name="q_stationID" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>

                    <td class="table_edit_tr_td_label" width="10%">报告结束日期:</td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text" name="q_e_time" id="q_e_time" size="18" require="false"  
                               dataType="Date|ThanDate" format="ymd" to="q_b_time" msg="结束时间格式必须正确且大于开始时间!"/>
                        <a href="javascript:openCalenderDialog(document.all.q_e_time);">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block" />
                        </a>
                    </td>

                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">设备类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_deviceType" name="q_deviceType">				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_devtype" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">版本号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_version_no" id="q_version_no" size="15"  maxlength="10"   require="false" dataType="Number|LimitB" msg="版本号应为数字且最大长度为10"/>
                    </td>
                    <td class="table_edit_tr_td_label">设备ID:</td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text" name="device_id"  id="device_id" size="15" require="false" readonly 
                               maxlength="4" dataType="LimitB"  max="4" msg="设备ID长度不超过4个字节"/>
                        <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"
                             onclick="openWindowDialogDevId('${devIdList}', 'DevIdListForVerhis?type=null&ModuleID=040402', '0305', 600, 600);"/>

                    </td>
                    <td class="table_edit_tr_td_label" rowspan="3">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_parmTypeId#q_version_no#q_recordFlag#q_lineID#q_stationID#q_deviceType#device_id#q_b_time#q_e_time');
                               setLineCardNames('queryOp','q_lineID','q_stationID','commonVariable','','','');"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>
            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

        </form>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head" id="ignore">

                        <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="1" sortedby="asc" onclick="sortForTable();" style="width: 80px">线路</td>
                        <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="2" sortedby="asc" onclick="sortForTable();" style="width: 80px">车站</td>
                        <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="3" sortedby="asc" onclick="sortForTable();" style="width: 120px">设备类型</td>
                        <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="4" sortedby="asc" onclick="sortForTable();" style="width: 80px">设备ID</td>
                        <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="5" sortedby="asc" onclick="sortForTable();" style="width: 120px">参数类型ID</td>
                        <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="6" sortedby="asc" onclick="sortForTable();" style="width: 150px">设备版本标志</td>
                        <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="7" sortedby="asc" onclick="sortForTable();" style="width: 120px">返回版本号</td>
                        <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="8" sortedby="asc" onclick="sortForTable();" style="width: 150px">报告日期</td>
                        <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="9" sortedby="asc" onclick="sortForTable();" style="width: 120px">备注</td>
                    </tr>
                </table>

            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data" 
                            onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            >

                            <td id="line_id" style="width: 80px">
                                ${rs.line_name}
                            </td>
                            <td id="station_id" style="width: 80px">
                                ${rs.station_name}
                            </td>
                            <td id="dev_type_id" style="width: 120px">
                                ${rs.dev_type_name}
                            </td>
                            <td id="device_id" style="width: 80px">
                                ${rs.device_id}
                            </td>
                            <td id="parm_type_id" style="width: 120px">
                                ${rs.parm_type_id}
                            </td>
                            <td id="record_flag" style="width: 150px">
                                ${rs.record_flag_name}
                            </td>
                            <td id="version_no" style="width: 120px">
                                ${rs.version_no}
                            </td>
                            <td id="report_date" style="width: 150px">
                                ${rs.report_date}
                            </td>
                            <td id="remark" style="width: 120px">
                                ${rs.remark}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <!-- 表头 通用模板 -->
        <FORM method="post" name="detailOp" id="detailOp" action="DevParaVerHis">

            <input type="hidden" name="expAllFields" id="d_expAllFields" value="line_name,station_name,dev_type_name,device_id,parm_type_id,record_flag_name,version_no,report_date,remark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="设备参数查询历史.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/DevParaVerHisExportAll" />

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
            <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','q_lineID','q_stationID','commonVariable','','','');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        <!-- 状态栏 通用模板 -->

    </body>
</html>
