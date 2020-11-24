<%-- 
    Document   : off_peak_hours
    Created on : 2017-6-18
    Author     : mqf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>票价资料</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <SCRIPT LANGUAGE="JavaScript">
//            function selectedRow(thisObject)
//            {
//            var selectedRow;
//            if(selectedRow != null)
//            {
//            selectedRow.value = "unselected";
//            selectedRow.style.backgroundColor = "";
//            }
//            thisObject.value = "selected";
//            thisObject.style.backgroundColor = "#94CBFF";
//            selectedRow = thisObject;
//            document.getElementById('d_rectNo').value=selectedRow.childNodes.item(0).childNodes.item(0).value;
//            document.getElementById('d_rectNo').value=thisObject.id;
//            }
        </SCRIPT>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="
//                initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
//                    setPrimaryKeys('detailOp', 'd_lineID#d_stationID#d_deviceID#d_deviceType');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
//                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="
//                initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del');
//                    setPrimaryKeys('detailOp', 'd_lineID#d_stationID#d_deviceID#d_deviceType');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
//                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:otherwise>
        </c:choose>
        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">非繁忙时间定义表（
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

        <form method="post" name="queryOp" id="queryOp" action="OffPeakHours">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">星期名称:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_day_of_week" name="q_day_of_week" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_dayOfWeek" />
                        </select>
                    </td>
                    
                    <td class="table_edit_tr_td_label">开始时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_start_time" id="q_start_time" size="10" maxlength="4" require="false" dataType="ICCSDateHHmm|Compare|Range"  operator="GreaterThanEqual" to="0" min="0000" max="2359" msg="开始时间为0000-2359之间的时间格式!"/>
                        <a href="javascript:openHHmiCalenderDialogByIDx('q_start_time','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    
                    <td class="table_edit_tr_td_label">结束时间:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_end_time" id="q_end_time" size="10" maxlength="4" require="false"   dataType="ICCSDateHHmm|Range|thanDate"   min="0000" max="2359" to="d_start_time" msg="结束时间为0000-2359之间的时间格式且大于开始时间!"/>
                        <a href="javascript:openHHmiCalenderDialogByIDx('q_end_time','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    

                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_day_of_week#q_start_time#q_end_time');"/>
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >星期名称</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >开始时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >结束时间</td>
                    </tr>


                </table>

            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <!--selectedRow(this);-->
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');" 
                            id="${rs.water_no}">

                            <td id="rectNo" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.water_no}">

                                </input>
                            </td>
                            <td  id="day_of_week_desc" class="table_list_tr_col_data_block">
                                ${rs.day_of_week_desc}
                            </td>
                            <td  id="start_time" class="table_list_tr_col_data_block">
                                ${rs.start_time}
                            </td>
                            <td  id="end_time" class="table_list_tr_col_data_block">
                                ${rs.end_time}
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

        <FORM method="post" name="detailOp" id="detailOp" action="OffPeakHours" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">

                    <tr class="table_edit_tr">
                        
                        <INPUT TYPE="hidden" id="d_rectNo" NAME="d_water_no"/>
                        
                        <td class="table_edit_tr_td_label">星期名称: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_day_of_week_desc" name="d_day_of_week" dataType="NotNull" msg="星期名称不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_dayOfWeek" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">开始时间:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_start_time" id="d_start_time" size="10" maxlength="4" require="true" dataType="ICCSDateHHmm|Compare|Range"  operator="GreaterThanEqual" to="0" min="0000" max="2359" msg="开始时间为0000-2359之间的时间格式!"/>
                            <a href="javascript:openHHmiCalenderDialogByIDx('d_start_time','false');">
                                <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                            </a>
                        </td>
                        <td class="table_edit_tr_td_label">结束时间:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_end_time" id="d_end_time" size="10" maxlength="4" require="true"   dataType="ICCSDateHHmm|Range|thanDate"   min="0000" max="2359" to="d_start_time" msg="结束时间为0000-2359之间的时间格式且大于开始时间!"/>
                            <a href="javascript:openHHmiCalenderDialogByIDx('d_end_time','false');">
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
            <c:set var="submit" scope="request" value="0"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>

            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_water_no');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->

        <%--<c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />--%>
    </body>
</html>
