<%-- 
    Document   : deal_assign_contc
    Created on : 2017-6-18, 17:10:18
    Author     : liudezeng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>运营商交易分配比例</title>

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_b_line_id#d_b_station_id#d_e_line_id#d_e_station_id#d_contc_id');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del;submit1;import');
                    setPrimaryKeys('detailOp', 'd_b_line_id#d_b_station_id#d_e_line_id#d_e_station_id#d_contc_id');
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
                <td colspan="4">运营商交易分配比例（
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

        <form method="post" name="queryOp" id="queryOp" action="DealAssignContc">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >起始车站:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_b_line_id" name="q_b_line_id" onChange="setSelectValues('queryOp', 'q_b_line_id', 'q_b_station_id', 'commonVariable');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                        <select id="q_b_station_id" name="q_b_station_id" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label" >出口车站:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_e_line_id" name="q_e_line_id" onChange="setSelectValues('queryOp', 'q_e_line_id', 'q_e_station_id', 'commonVariable1');"  >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                        <select id="q_e_station_id" name="q_e_station_id" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                    </td>
                    <td class="table_edit_tr_td_label">运营商:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_contc_id" name="q_contc_id" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_contc" />
                        </select>
                    </td>


                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_b_line_id#q_b_station_id#q_e_line_id#q_e_station_id#q_contc_id');setLineCardNames('queryOp','q_b_line_id','q_b_station_id','commonVariable','q_e_line_id','q_e_station_id','commonVariable1');"/>
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
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >起始线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >起始站点</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >目的线路</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >目的站点</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >运营商</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="6"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >分账比例</td>
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
                            onclick="setSelectValuesByRowPropertyName('detailOp', 'd_b_station_id', 'commonVariable', 'idStartLine');
                                    setSelectValuesByRowPropertyName('detailOp', 'd_e_station_id', 'commonVariable1', 'idDestLine');
                                    clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            id="${rs.b_line_id}#${rs.b_station_id}#${rs.e_line_id}#${rs.e_station_id}#${rs.contc_id}#${rs.in_percent}#${rs.version_no}#${rs.record_flag}"
                            idStartLine="${rs.b_line_id}" idDestLine="${rs.e_line_id}">

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.b_station_id}#${rs.e_station_id}">
                                </input>
                            </td>

                            <td  id="b_line_id" class="table_list_tr_col_data_block">
                                ${rs.b_line_id_name}
                            </td>
                            <td  id="b_station_id" class="table_list_tr_col_data_block">
                                ${rs.b_station_id_name}
                            </td>
                            <td  id="e_line_id" class="table_list_tr_col_data_block">
                                ${rs.e_line_id_name}
                            </td>
                            <td  id="e_station_id" class="table_list_tr_col_data_block">
                                ${rs.e_station_id_name}
                            </td>
                            <td  id="contc_id" class="table_list_tr_col_data_block">
                                ${rs.contc_id_name}
                            </td>
                            <td  id="in_percent" class="table_list_tr_col_data_block">
                                ${rs.in_percent}
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

        <FORM method="post" name="detailOp" id="detailOp" action="DealAssignContc" >
    <input type="hidden" name="importUrl" id="importUrl" value="DealAssignContc?command=importContc" />
    
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="b_line_id_name,b_station_id_name,e_line_id_name,e_station_id_name,contc_id_name,in_percent" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="运营商交易分配比例.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/DealAssignContcExportAll" />
            
            
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">起始车站: </td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_b_line_id"  id="d_b_line_id" onChange="setSelectValues('detailOp', 'd_b_line_id', 'd_b_station_id', 'commonVariable');" require="true" dataType="LimitB" min="1" msg="线路代码不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>	
                            <select id="d_b_station_id" name="d_b_station_id" require="true" dataType="LimitB" min="1" msg="车站代码不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                        <td class="table_edit_tr_td_label">出口车站: </td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_e_line_id"  id="d_e_line_id" onChange="setSelectValues('detailOp', 'd_e_line_id', 'd_e_station_id', 'commonVariable1');" require="true" dataType="LimitB" min="1" msg="没有选择目的线路">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>	
                            <select id="d_e_station_id" name="d_e_station_id" require="true" dataType="LimitB" min="1" msg="没有选择目的车站">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />

                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                        <td class="table_edit_tr_td_label">运营商:</td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_contc_id"  id="d_contc_id"  require="true" dataType="LimitB" min="1" msg="分账线路不能为空">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_contc" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">分账比例:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_in_percent" id="d_in_percent" size="8" maxLength="8" require="true" dataType="Custom" regexp="(0|0\.\d{1,6}|1(\.0{1,6})|1)$" msg="分帐比率数值应该在0.000000-1.000000之间" />
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
            <c:set var="import_1" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>

            <c:set var="addQueryMethod" scope="request" value="setLineCardNames('queryOp','d_b_line_id','d_b_station_id','commonVariable','d_e_line_id','d_e_station_id','commonVariable1','d_contc_id','commonVariable1');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_b_line_id#d_b_station_id#d_e_line_id#d_e_station_id#d_contc_id#Version');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->

       
    </body>
</html>