<%-- 
    Document   : distanceOD
    Created on : 2017-9-6, 16:03:58
    Author     : chenzx
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OD路径查询</title>

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>   
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>

    </head>

    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">OD路径查询</td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="distanceODAction">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">                                      
                    <td class="table_edit_tr_td_label">开始站点:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_beginLineId" name="q_beginLineId" require="true" dataType="NotEmpty" msg="请选择开始线路!" 
                                onChange="setSelectValues('queryOp', 'q_beginLineId', 'q_beginStationId', 'commonVariable');">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_beginStationId" name="q_beginStationId" require="true" dataType="NotEmpty" msg="请选择开始站点!">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />                     
                    </td>
                    <td class="table_edit_tr_td_label">结束站点:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_endLineId" name="q_endLineId" require="true" dataType="NotEmpty" msg="请选择结束线路!"
                                onChange="setSelectValues('queryOp', 'q_endLineId', 'q_endStationId', 'commonVariable1');">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_endStationId" name="q_endStationId" require="true" dataType="NotEmpty" msg="请选择结束站点!">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />                    
                    </td>
                    <td class="table_edit_tr_td_label">版本状态:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_recordFlag" name="q_recordFlag" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_recordFlags" />
                        </select>                     
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_beginLineId#q_beginStationId#q_endLineId#q_endStationId#q_recordFlag');
                               setLineCardNames('queryOp','q_beginLineId','q_beginStationId','commonVariable','q_endLineId','q_endStationId','commonVariable1')"/>
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">开始线路</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">开始站点</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">结束线路</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px">结束站点</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 80px">换乘次数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">换乘明细</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="7"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">总里程数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="8"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">最短里程</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="9"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">是否有效</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="10"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">版本状态</td>
                    </tr>
                </table>
            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">                 
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, '');" 
                            id="${rs.id}" >

                            <td  id="ID" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.id}
                            </td>
                            <td  id="beginLineId" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.oLineText}
                            </td>
                            <td  id="beginStationId" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.oStationText}
                            </td> 
                            <td  id="endLineId" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.eLineText}
                            </td>
                            <td  id="endStationId" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.eStationText}
                            </td>
                            <td  id="changeTimes" class="table_list_tr_col_data_block" style="width: 80px">
                                ${rs.change_times}
                            </td>
                            <td  id="detail" class="table_list_tr_col_data_block" style="width: 100px">
                                <c:if test="${rs.change_times>0}">
                                    <a href='#'
                                       onClick="openwindow('distanceDetailAction?q_od_id=${rs.id}&command=query&ModuleID=${ModuleID}','',800,600)">
                                        换乘明细
                                    </a>
                                </c:if>
                                <c:if test="${rs.change_times ==0}">
                                    换乘明细
                                </c:if>
                            </td> 
                            <td  id="distance" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.distance}
                            </td>
                            <td  id="minDistance" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.min_distance}
                            </td>
                            <td  id="isValid" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.isValidText}
                            </td>
                            <td  id="recordFlag" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.versionText}
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>
        </div>

        <FORM method="post" name="detailOp" id="detailOp" action="distanceODAction" >
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getId,getoLineText,getoStationText,geteLineText,geteStationText,getChange_times,getChangDetailText,getDistance,getMin_distance,getIsValidText,getVersionText" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="OD路径查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/DistanceODExportAll"/>

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <div id="detail"  class="divForTableDataDetail" ></div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1"/>
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
