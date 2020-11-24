<%-- 
    Document   : lineStation
    Created on : 2017-9-7, 15:58:23
    Author     : mh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>站线查询</title>

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
                <td colspan="4">站线查询</td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="lineStationAction" style="margin-bottom: 3px;">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">                                      
                    <td class="table_edit_tr_td_label">线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_lineId" name="q_lineId"  onChange="setSelectValues('queryOp', 'q_lineId', 'q_stationId', 'commonVariable');">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>                    
                    </td>
                    <td class="table_edit_tr_td_label">站点:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_stationId" name="q_stationId" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
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
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_lineId#q_stationId#q_recordFlag');setLineCardNames('queryOp','q_lineId','q_stationId','commonVariable')" />
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">所属线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">站点中文名</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">站点英文名</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">版本状态</td> 
                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">                 
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, '');setPageControl('detailOp');"
                            id="${rs.id_code}" >
                            
                            <td  id="lineId" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.line_name}
                            </td>
                            <td  id="stationId" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.chinese_name}
                            </td>
                            <td  id="englishName" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.english_name}
                            </td> 
                            <td  id="recordFlag" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.versionText}
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>
        </div>

        <FORM method="post" name="detailOp" id="detailOp" action="lineStationAction" >

            <c:set var="divideShow" scope="request" value="1"/>
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getLine_name,getChinese_name,getEnglish_name,getVersionText" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="站线查询.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/LineStationExportAll"/>
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
