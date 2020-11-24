<%-- 
    Document   : station_device
    Created on : 2017-5-16, 20:48:42
    Author     : hejj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>设备配置</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <style type="text/css">
            .readOnly{
                border: 1px solid #DDD; 
                background-color: #F5F5F5; 
                color:#ACA899;
            }
        </style>
        <script language=javascript>
            function setDeviceSerialAndArrayID(){
                var obj=document.getElementById("d_deviceType");
                
                var index=obj.selectedIndex;
                var text = obj.options[index].text; // 选中文本
                var value = obj.options[index].value; // 选中值
                //alert("text:" + text + ";value:" + value);
                
                //给设备序列号赋默认值：000
                var devSerial = document.getElementById("d_deviceSerial").value;
                if(devSerial == null || devSerial == ""){
                    document.getElementById("d_deviceSerial").value = "000";
                }
                
                //设备类型选中 02 TVM 、04 EG 、05 EXG 、06 TWG， 闸机/TVM阵列编码可输入，其他赋默认值：000 并置灰,不可编辑
                var array = new Array("02","04","05","06");
                if(contains(value, array)){
                    var arrayIdTemp = document.getElementById("d_arrayID").value;
                    if(arrayIdTemp == "000"){ 
                        document.getElementById("d_arrayID").value = "";
                    }
                    //去除置灰
                    document.getElementById("d_arrayID").readOnly=false;
                    document.getElementById("d_arrayID").className ="";
                }else{
                    var arrayIdTemp = document.getElementById("d_arrayID").value;
                    //if(arrayIdTemp == "" || arrayIdTemp == null){ 
                        document.getElementById("d_arrayID").value = "000";
                        //置灰
                        document.getElementById("d_arrayID").readOnly=true;
                        document.getElementById("d_arrayID").className ="readOnly";
                    //}
                } 
            }
            
            function contains(obj, arr) {
                var i = arr.length;  
                while (i--) {
                  if (arr[i] === obj) {
                    return true;
                  } 
                }
                return false;
            }
            
            function initAdd(){
                //获取选择的时间
                var current = new  Date();
                var year = current.getFullYear();
  		var month = current.getMonth()+1;
  		var day = current.getDate();
 
  		if(month <10)
  			month = "0"+month;
  		if(day <10 )
  			day = "0"+day;
                
                var etime    =  year+"-"+month+"-"+day ;
                document.getElementById("d_configDate").value = etime ;
            }
        </script>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_lineID#d_stationID#d_deviceID#d_deviceType');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del;submit1');
                    setPrimaryKeys('detailOp', 'd_lineID#d_stationID#d_deviceID#d_deviceType');
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
                <td colspan="4">车站设备配置（${version.record_flag_name}：${version.version_no}）
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

        <form method="post" name="queryOp" id="queryOp" action="StationDevice">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_lineID" name="q_lineID" onChange="setSelectValues('queryOp', 'q_lineID', 'q_stationID', 'commonVariable');" style="width:140px">				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">车站:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_stationID" name="q_stationID" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label">设备类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_deviceType" name="q_deviceType" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_devtype" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">设备ID:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_deviceID" id="q_deviceID" style="width:140px" maxLength="3" require="false" dataType="integer|LimitB" min="3" max="3" msg="设备ID应为3位数字"/>
                    </td>    

                    <td class="table_edit_tr_td_label">设备序列号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_deviceSerial" id="q_deviceSerial" require="false" maxLength="20" dataType="NumAndEng" style="width:140px" msg="请输入字母或数字组合"/>                     
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_lineID#q_stationID#q_deviceID#q_deviceSerial#q_deviceType');setLineCardNames('queryOp','q_lineID','q_stationID','commonVariable','','','');"/>
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
                <table id="DataTableHead" class="table_list_block">

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备CSC读写器数量</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >闸机/TVM阵列编码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >车站内区域编号</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备序列号</td>
                        <td id="orderTd" style="width:100px" class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备IP地址</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10" sortedby="asc"  onclick="sortForTableBlock('clearStart');">商户</td>
                        <td id="orderTd" style="width:100px" class="table_list_tr_col_head_block"  isDigit=false index="11" sortedby="asc"  onclick="sortForTableBlock('clearStart');">配置时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="12" sortedby="asc"  onclick="sortForTableBlock('clearStart');">设备名称</td>
                        <td id="orderTd" style="width:100px" class="table_list_tr_col_head_block"  isDigit=false index="13" sortedby="asc"  onclick="sortForTableBlock('clearStart');">版本号</td>
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
                            onclick="setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');
                                     onclickSetIpValue('ip1','ip2','ip3','ip4','${rs.ip_address}')" 
                            id="${rs.line_id}#${rs.station_id}#${rs.dev_type_id}#${rs.device_id}#${rs.version_no}#${rs.record_flag}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.line_id}#${rs.station_id}#${rs.device_id}#${rs.dev_type_id}">

                                </input>
                            </td>
                            <td  id="lineID" class="table_list_tr_col_data_block">
                                ${rs.line_id_name}
                            </td>
                            <td  id="stationID" class="table_list_tr_col_data_block">
                                ${rs.station_id_name}
                            </td>
                            <td  id="deviceID" class="table_list_tr_col_data_block">
                                ${rs.device_id}
                            </td>
                            <td  id="deviceType" class="table_list_tr_col_data_block">
                                ${rs.dev_type_id_name}
                            </td>
                            <td  id="CSCNumber" class="table_list_tr_col_data_block">
                                ${rs.csc_num}
                            </td>
                            <td  id="arrayID" class="table_list_tr_col_data_block">
                                ${rs.array_id}
                            </td>
                            <td  id="zoneID" class="table_list_tr_col_data_block">
                                ${rs.concourse_id}
                            </td>
                            <td  id="deviceSerial" class="table_list_tr_col_data_block">
                                ${rs.dev_serial}
                            </td>
                            <td style="width:100px" id="IPAddress" class="table_list_tr_col_data_block">
                                ${rs.ip_address}
                            </td>
                            <td  id="venderID" class="table_list_tr_col_data_block">
                                ${rs.store_id_name} 
                            </td>
                            <td style="width:100px" id="configDate" class="table_list_tr_col_data_block">
                                ${rs.config_date}
                            </td>
                            <td  id="devName" class="table_list_tr_col_data_block">
                                ${rs.dev_name}
                            </td>
                            <td style="width:100px" id="versionNo" class="table_list_tr_col_data_block" >
                                ${rs.version_no}
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

        <FORM method="post" name="detailOp" id="detailOp" action="StationDevice" >
            <input type="hidden" name="importUrl" id="importUrl" value="StationDevice?command=importStationDevice&Type=${version.parm_type_id}" />
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getLine_id_name,getStation_id_name,getDevice_id,getDev_type_id_name,getCsc_num,getArray_id,getConcourse_id,getDev_serial,getIp_address,getStore_id_name,getConfig_date,getDev_name,getVersion_no" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="车站设备配置.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/StationDeviceExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">线路: </td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_lineID"  id="d_lineID" onChange="setSelectValues('detailOp', 'd_lineID', 'd_stationID', 'commonVariable');" 
                                     require="true" dataType="LimitB" min="1" msg="没有选择线路" style="width:140px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">车站:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_stationID" name="d_stationID" require="true" dataType="LimitB" min="1" msg="没有选择车站" style="width:140px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                        <td class="table_edit_tr_td_label">设备ID:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_deviceID" id="d_deviceID" style="width:140px" maxLength="3" dataType="integer|LimitB|NotEmpty" min="3" max="3" msg="设备ID应为3位数字！"/>
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">设备类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_deviceType" name="d_deviceType" require="true" dataType="LimitB" min="1" msg="没有选择设备类型" style="width:140px"
                                    onchange="if(this.value != '') setDeviceSerialAndArrayID()">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_devtype" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">设备名称:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_devName" id="d_devName" style="width:140px" require="true"  maxLength="30"  dataType="NotEmpty|LimitContainChinese" min="1" max="30" value=""  msg="设备名称长度为1-30个字符，不能为空" />
                        </td>
                        <td class="table_edit_tr_td_label">设备IP地址:</td>
                        <td class="table_edit_tr_td_input">
                            <input name="ip1" id ="ip1" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="第1个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip1,ip1,ip2) onkeyup=keyUpEventForIp1(ip1)>.
                            <input name="ip2" id ="ip2" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="第2个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip1,ip2,ip3) onkeyup=keyUpEvent(ip2)>.
                            <input name="ip3" id ="ip3" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="第3个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip2,ip3,ip4) onkeyup=keyUpEvent(ip3)>.
                            <input name="ip4" id ="ip4" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="第4个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip3,ip4,ip4) onkeyup=keyUpEvent(ip4)>
                            <input type="hidden" name="d_IPAddress" id="d_IPAddress" />
                        </td>
                        
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">设备序列号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_deviceSerial" id="d_deviceSerial" style="width:140px" maxLength="20" require="true" dataType="NumAndEng|NotEmpty"  msg="设备序列号应为数字、字母组合，不能为空" />
                        </td>
                        <td class="table_edit_tr_td_label">闸机/TVM阵列编码:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_arrayID" id="d_arrayID" style="width:140px" require="true" maxLength="3" dataType="Number|LimitB" min="3" max="3" msg="闸机/TVM阵列编码应为3位数字"/>
                        </td>
                        <td class="table_edit_tr_td_label">设备CSC读写器数量:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_CSCNumber"  id="d_CSCNumber" style="width:140px" require="false" maxlength="3" dataType="integer|Positive" msg="设备CSC读写器数量应为正整数"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">车站内区域编号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_zoneID" id="d_zoneID" style="width:140px" maxLength="3" require="true" dataType="Number|LimitB" min="3" max="3" msg="车站内区域编号应为3位数字"/>
                        </td>
                        <td class="table_edit_tr_td_label">商户ID:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_venderID" name="d_venderID" style="width:140px">
                                < <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_merchant" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">配置时间: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_configDate" id="d_configDate" style="width:140px"  dataType="ICCSDate"  msg="配置日期格式为[****-**-**]"/>
                            <a href="javascript:openCalenderDialogByID('d_configDate','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                        </td>
                        
                    </tr>
                    
                </table>
            </DIV>

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
            <c:set var="import_1" scope="request" value="1"/>
            <%--<c:set var="import_2" scope="request" value="1"/>--%>

            <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','q_lineID','q_stationID','commonVariable','','','');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineID#d_stationID#d_deviceType#d_deviceID#Version');getipvalue('ip1','ip2','ip3','ip4','d_IPAddress');"/>
            <c:set var="addAfterMethod" scope="request" value="initAdd();"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');getipvalue('ip1','ip2','ip3','ip4','d_IPAddress');"/>
            <%--<c:set var="importUpload" scope="request" value="openUpload('queryOp');"/>--%>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

    </body>
    <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
    <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
    <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
    <!--<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/upload.js"></script>-->
</html>
