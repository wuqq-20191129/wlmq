<%-- 
    Document   : tvm_stop_sell_def
    Created on : 2017-6-13, 19:10:18
    Author     : zhouyang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>工作日时间表参数</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="javascript">
            function stopsell_time(stopsell_time1,stopsell_time2,stopsell_endtime) {
                var stopsell_time1_hh = stopsell_time1.substring(0,2);
                var stopsell_time1_mm = stopsell_time1.substring(3,5);
                var stopsell_time2_hh = stopsell_time2.substring(0,2);
                var stopsell_time2_mm = stopsell_time2.substring(3,5);
                var stopsell_endtime_hh = stopsell_endtime.substring(0,2);
                var stopsell_endtime_mm = stopsell_endtime.substring(3,5);
                document.getElementById('d_stopsell_time1_hh').value=stopsell_time1_hh;
                document.getElementById('d_stopsell_time1_mm').value=stopsell_time1_mm;
                document.getElementById('d_stopsell_time2_hh').value=stopsell_time2_hh;
                document.getElementById('d_stopsell_time2_mm').value=stopsell_time2_mm;
                document.getElementById('d_stopsell_endtime_hh').value=stopsell_endtime_hh;
                document.getElementById('d_stopsell_endtime_mm').value=stopsell_endtime_mm;
            }
        </script>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_startLine#d_startStation#d_destLine#d_destStation');
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
                    setPrimaryKeys('detailOp', 'd_startLine#d_startStation#d_destLine#d_destStation');
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
                <td colspan="4">${timeTable.timetable_name}（
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

        <form method="post" name="queryOp" id="queryOp" action="TvmStopsellDef">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >出发线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_startLine" name="q_startLine" onChange="setSelectValues('queryOp', 'q_startLine', 'q_startStation', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">出发车站:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_startStation" name="q_startStation" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label" >目的线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_destLine" name="q_destLine" onChange="setSelectValues('queryOp', 'q_destLine', 'q_destStation', 'commonVariable1');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label"><div align="right">目的车站:</div></td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_destStation" name="q_destStation" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_startLine#q_startStation#q_destLine#q_destStation');setLineCardNames('queryOp','q_startLine','q_startStation','commonVariable','q_destLine','q_destStation','commonVariable1');"/>
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
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width: 120px">时间表类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width: 120px">出发线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width: 120px">出发车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width: 120px">目的线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"   sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width: 120px">目的车站</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width: 100px">停售开始时间1</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width: 100px">停售开始时间2</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width: 100px">停售结束时间</td>
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
                            onclick="setSelectValuesByRowPropertyName('detailOp','d_startStation','commonVariable','idStartLine');
								setSelectValuesByRowPropertyName('detailOp','d_destStation','commonVariable1','idDestLine');
								clickResultRow('detailOp',this,'detail');setPageControl('detailOp');
                                                                stopsell_time('${rs.stopsell_time1}','${rs.stopsell_time2}','${rs.stopsell_endtime}');" 
                        id="${rs.start_station}#${rs.dest_station}#${rs.version_no}#${rs.record_flag}#${rs.parm_type_id}"
                        idStartLine="${rs.start_line_id}" idDestLine="${rs.dest_line_id}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.start_station}#${rs.dest_station}">
                                </input>
                            </td>
                            <td  id="timetableID" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.timetable_name}
                            </td>
                            <td  id="startLine" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.start_line_name}
                            </td>
                            <td  id="startStation" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.start_station_name}
                            </td>
                            <td  id="destLine" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.dest_line_name}
                            </td>
                            <td  id="destStation" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.dest_station_name}
                            </td>
                            <td  id="stopsellTime1" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.stopsell_time1}
                            </td>
                            <td  id="stopsellTime2" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.stopsell_time2}
                            </td>
                            <td  id="stopsellEndtime" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.stopsell_endtime}
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

        <FORM method="post" name="detailOp" id="detailOp" action="TvmStopsellDef" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">出发线路: </td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_startLine"  id="d_startLine" onChange="setSelectValues('detailOp', 'd_startLine', 'd_startStation', 'commonVariable');" require="true" dataType="LimitB" min="1" msg="没有选择出发线路">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">出发车站:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_startStation" name="d_startStation" require="true" dataType="LimitB" min="1" msg="没有选择出发车站">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                        <td class="table_edit_tr_td_label">目的线路: </td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_destLine"  id="d_destLine" onChange="setSelectValues('detailOp', 'd_destLine', 'd_destStation', 'commonVariable1');" require="true" dataType="LimitB" min="1" msg="没有选择目的线路">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">目的车站:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_destStation" name="d_destStation" require="true" dataType="LimitB" min="1" msg="没有选择目的车站">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">停售开始时间1: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_stopsell_time1_hh" name="d_stopsell_time1_hh" require="true" dataType="LimitB" min="1" msg="停售开始时间1的“时”不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_hour" />
                            </select>时
                            <select id="d_stopsell_time1_mm" name="d_stopsell_time1_mm" require="true" dataType="LimitB" min="1" msg="停售开始时间1的“分”不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_min" />
                            </select>分
                        </td>
                        <td class="table_edit_tr_td_label">停售开始时间2: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_stopsell_time2_hh" name="d_stopsell_time2_hh" require="true" dataType="LimitB"  min="1" msg="停售开始时间2的“时”不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_hour" />
                            </select>时
                            <select id="d_stopsell_time2_mm" name="d_stopsell_time2_mm" require="true" dataType="LimitB" min="1" msg="停售开始时间2的“分”不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_min" />
                            </select>分
                        </td>
                        <td class="table_edit_tr_td_label">停售结束时间: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_stopsell_endtime_hh" name="d_stopsell_endtime_hh" require="true" dataType="LimitB" min="1" msg="停售结束时间的“时”不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_hour" />
                            </select>时
                            <select id="d_stopsell_endtime_mm" name="d_stopsell_endtime_mm" require="true" dataType="LimitB" min="1" msg="停售结束时间的“分”不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_min" />
                            </select>分
                            <input type="hidden" name="compare_time" dataType="CompareTime" 
                                   hh1="d_stopsell_time1_hh" mm1="d_stopsell_time1_mm"
                                   hh2="d_stopsell_time2_hh" mm2="d_stopsell_time2_mm"
                                   hh="d_stopsell_endtime_hh" mm="d_stopsell_endtime_mm"
                                   msg="不能出现停售结束时间<停售开始时间2<停售开始时间1的情况"/>
                        </td>
                        
                    </tr>
                </table>
            </DIV>

            <input type="hidden" name="expAllFields" id="d_expAllFields" value="timetable_name,start_line_name,start_station_name,dest_line_name,dest_station_name,stopsell_time1,stopsell_time2,stopsell_endtime" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="${timeTable.timetable_name}.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TvmStopsellDefExportAll" />
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

            <c:set var="addQueryMethod" scope="request" value="setLineCardNames('queryOp','d_startLine','d_startStation','commonVariable','d_destLine','d_destStation','commonVariable1');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_startLine#d_startStation#d_destLine#d_destStation#Version');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            
           
            <br/>
        </FORM>

        
    </body>
</html>