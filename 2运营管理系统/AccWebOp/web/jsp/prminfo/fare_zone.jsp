<%-- 
    Document   : fare_zone
    Created on : 2017-6-12
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>收费区段</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <script language="javascript">
//            function setSelectedIdForRow(selectedRowOb,formName){
//            var frm = document.forms[formName];
////            var keyOb = frm.all['selectedFlowId'];
//            var keyOb =document.getElementById('selectedFlowId');
//            keyOb.value =selectedRowOb.id;
//            //  alert(keyOb.value);
//            }
//            function setSelectedIdForCheckBox(selectedCheckBoxOb,formName){
//            var frm = document.forms[formName];
////            var keyOb = frm.all['selectedFlowId'];
//            var keyOb =document.getElementById('selectedFlowId');
//            keyOb.value =selectedCheckBoxOb.value;
//            //  alert(keyOb.value);
//            }
        </script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_entry_line_text#d_entry_station_text#d_exit_line_text#d_exit_station_text'); 
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del');
                    setPrimaryKeys('detailOp','d_entry_line_text#d_entry_station_text#d_exit_line_text#d_exit_station_text');
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
                <td colspan="4">收费区段（
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

        <form method="post" name="queryOp" id="queryOp" action="FareZone">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >入口车站:</td>
                    <td class="table_edit_tr_td_input" style="width:15%">
                        <select id="q_b_line" name="q_b_line" onChange="setSelectValues('queryOp','q_b_line','q_b_station','commonVariable');" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                        
                        <select id="q_b_station" name="q_b_station" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    
                    <td class="table_edit_tr_td_label" >出口车站:</td>
                    <td class="table_edit_tr_td_input" style="width:15%">
                        <select id="q_e_line" name="q_e_line" onChange="setSelectValues('queryOp','q_e_line','q_e_station','commonVariable1');" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                        
                        <select id="q_e_station" name="q_e_station" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    
                    <td class="table_edit_tr_td_label">收费区段:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_fare_name" name="q_fare_name" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fareName" />
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_b_line#q_b_station#q_e_line#q_e_station#q_fare_name');setLineCardNames('queryOp','q_b_line','q_b_station','commonVariable','q_e_line','q_e_station','commonVariable1');"/>
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
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll"  onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >入口线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >入口车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出口线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >出口车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >收费区段</td>
                        <td id="orderTd"    style="width: 120px" class="table_list_tr_col_head_block"  isDigit=true index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >乘车时间限制</td>
                        <td id="orderTd"    style="width: 120px" class="table_list_tr_col_head_block"  isDigit=true index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >超时罚金(分)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >乘距</td>
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
                            onClick="setSelectValuesByRowPropertyName('detailOp','d_entry_station_text','commonVariable','typeIDs');
                                     setSelectValuesByRowPropertyName('detailOp','d_exit_station_text','commonVariable1','typeIDs1');
                                     clickResultRow('detailOp',this,'detail');setPageControl('detailOp');"
                            id="${rs.water_no}"
                            typeIDs="${rs.b_line_id}#${rs.b_station_id}"
                            typeIDs1="${rs.e_line_id}#${rs.e_station_id}" >
                        

                            <td id="rectNo" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onClick="unSelectAllRecord('detailOp','rectNoAll','rectNo');"
                                       value="${rs.water_no}">

                                </input>
                            </td>
                            <td  id="entry_line_text" class="table_list_tr_col_data_block">
                                ${rs.entry_line_text}
                            </td>
                            <td  id="entry_station_text" class="table_list_tr_col_data_block">
                                ${rs.entry_station_text}
                            </td>
                            <td  id="exit_line_text" class="table_list_tr_col_data_block">
                                ${rs.exit_line_text}
                            </td>
                            <td  id="exit_station_text" class="table_list_tr_col_data_block">
                                ${rs.exit_station_text}
                            </td>
                            <td  id="fare_zone" class="table_list_tr_col_data_block">
                                ${rs.fare_zone_text}
                            </td>
                            <td  id="max_travel_time" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.max_travel_time}
                            </td>
                            <td  id="over_time_charge" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.over_time_charge}
                            </td>
                            <td  id="distince" class="table_list_tr_col_data_block">
                                ${rs.distince}
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

        <FORM method="post" name="detailOp" id="detailOp" action="FareZone" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="getEntry_line_text,getEntry_station_text,getExit_line_text,getExit_station_text,getFare_zone_text,getMax_travel_time,getOver_time_charge,getDistince" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="收费区段.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/FareZoneExportAll" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        
                        <INPUT TYPE="hidden" id="d_rectNo" NAME="d_water_no"/>
                        
                        <td class="table_edit_tr_td_label">入口车站ID: </td>
                        <td class="table_edit_tr_td_input"  style="width:15%">
                            <select  id="d_entry_line_text" name="d_entry_line_id" onChange="setSelectValues('detailOp','d_entry_line_text','d_entry_station_text','commonVariable');" dataType="Require" msg="线路代码不能为空!" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>	
                            
                            <select id="d_entry_station_text" name="d_entry_station_id" dataType="Require" msg="车站代码不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                                <c:set var="pVarName" scope="request" value="commonVariable"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>    
                        
                        <td class="table_edit_tr_td_label">出口车站ID: </td>
                        <td class="table_edit_tr_td_input"  style="width:15%">
                            <select  id="d_exit_line_text" name="d_exit_line_id" onChange="setSelectValues('detailOp','d_exit_line_text','d_exit_station_text','commonVariable1');" dataType="Require" msg="线路代码不能为空!" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>	
                            
                            <select id="d_exit_station_text" name="d_exit_station_id" dataType="Require" msg="车站代码不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                                <c:set var="pVarName" scope="request" value="commonVariable1"/>
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>    
                        
                        
                        <td class="table_edit_tr_td_label">收费区段: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_fare_zone" name="d_fare_zone" require="true" dataType="Require" msg="收费区段不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_fareName" />
                            </select>	
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">乘车时间限制(分钟):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_max_travel_time" id="d_max_travel_time" size="10" maxlength="5"  dataType="integer|Range"  min="0" max="99999"  msg="乘车时间限制必须为0-99999的整数!"/>
                        </td>
                        <td class="table_edit_tr_td_label">超时罚金(分):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_over_time_charge" id="d_over_time_charge" size="10" maxlength="4"   dataType="integer|Range"  min="0" max="9999" msg="超时罚金必须为0-9999的整数!"/>
                        </td>
                        
                        <td class="table_edit_tr_td_label">乘距(公里):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_distince" id="d_distince" size="10" maxlength="9"   dataType="double|Range|limitDecimal4" min="0" max="9999.9999"  msg="乘距保留小数点后四位，在0-9999.9999之间！"/>
                        </td>
                    </tr>



                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />


            <!--<input type="hidden" name="selectedFlowId" id="selectedFlowId" values=""/>-->

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="clone" scope="request" value="1"/>
            <c:set var="submit" scope="request" value="0"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>

            <c:set var="addQueryMethod" scope="request" value="setControlNames('queryOp','q_b_line#q_b_station#q_e_line#q_e_station#q_fare_name');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_water_no');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->

        <%--<c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />--%>
    </body>
</html>
