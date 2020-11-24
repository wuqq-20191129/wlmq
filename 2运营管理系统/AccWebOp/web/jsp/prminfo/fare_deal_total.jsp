<%-- 
    Document   : fare_deal_total
    Created on : 2018-5-28, 10:03:27
    Author     : liudz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>累计缴费额定义表</title>

<script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="
                    initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_deal_id');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">

            </c:when>
            <c:otherwise>
            <body onload="
                   initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del');
                    setPrimaryKeys('detailOp', 'd_deal_id');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:otherwise>

        </c:choose>
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">累计消费额定义表（
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
        <form method="post" name="queryOp" id="queryOp" action="FareDealTotal">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >累计消费额ID:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_deal_id" id="q_deal_id" size="10"  require="false" dataType="integer" maxLength="2"  msg="累计消费额ID只能为数字，最大长度为2位"/>
                    </td>
                    <td class="table_edit_tr_td_label">最小消费额(分):</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_deal_min" id="q_deal_min" size="10" require="false" dataType="integer"  maxLength="6" msg="最小消费额(分)只能为数字，最大长度为6位"/>
                    </td>
                    <td class="table_edit_tr_td_label">最大消费额(分):</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_deal_max" id="q_deal_max" size="10" require="false" dataType="integer"  maxLength="6" msg="最大消费额(分)只能为数字，最大长度为6位"/>
                    </td>
                    
                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_deal_id#q_deal_min#q_deal_max');"/>
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
        <DIV id="clearStartBlock"  class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll"  onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 120px" >累计消费额ID</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width: 120px">最小消费额(分)</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');"  style="width: 120px">最大消费额(分)</td>
                    </tr>
                </table>
            </div>

            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <!--class="listTableData" -->
                        <tr class="table_list_tr_data" optype=""   modified="" onyes="detail" bgColor="#fdfdfd" onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');
                                    setPageControl('detailOp');" 
                            style="display:" id="${rs.water_no}#${rs.deal_id}#${rs.deal_min}#${rs.deal_max}#${rs.version_no}#${rs.record_flag}">

                            <td id="rectNo" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.water_no}">

                                </input>
                            </td>
                            <td align="center" id="deal_id" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.deal_id}
                            </td>
                            <td align="center"  id="deal_min" class="table_list_tr_col_data_block " style="width: 120px">
                                ${rs.deal_min}
                            </td>
                            <td align="center"  id="deal_max" class="table_list_tr_col_data_block " style="width: 120px">
                                ${rs.deal_max}
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

        <FORM method="post" name="detailOp" id="detailOp" action="FareDealTotal" >
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="deal_id,deal_min,deal_max" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="累计消费额定义表.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/FareDealTotalExportAll" />

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <DIV id="detail"  class="divForTableDataDetail" >
                <table align="center" class="table_edit_detail">
                    <tr align="center"class="table_edit_tr">
                        
                        <input type="hidden" id="d_rectNo" name="d_water_no"/>
                        
                        <td class="table_edit_tr_td_label">累计消费额ID: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_deal_id" id="d_deal_id" size="10" require="true" maxLength="2" dataType="Number|LimitB"  min="2" max="2" msg="累计消费额IDID应为2位数字字符"/>
                    
                        </td>

                        <td class="table_edit_tr_td_label">最小消费额(分):</td>
                        <td class="table_edit_tr_td_input">

                            <input type="text" name="d_deal_min" id="d_deal_min" size="20" maxLength="6" require="true" dataType="integer"  msg="最小消费额应为不小于0的整数"/>

                        </td>
                        
                        <td class="table_edit_tr_td_label">最大消费额(分):</td>
                        <td class="table_edit_tr_td_input">

                            <input type="text" name="d_deal_max" id="d_deal_max" size="20" maxLength="6" require="true" dataType="integer"  msg="最大消费额应为不小于0的整数"/>

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
            
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>

            <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','d_deal_id','','','','','');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_water_no');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->


    </body>
</html>

