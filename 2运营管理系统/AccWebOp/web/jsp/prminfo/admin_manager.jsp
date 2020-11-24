<%-- 
    Document   : admin_manager
    Created on : 2017-6-16, 14:35:46
    Author     : lind
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>行政收费</title>
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
                    setPrimaryKeys('detailOp', 'd_lineID#d_stationID#d_cardMainID#d_cardSubID#d_adminManagerID');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable');">
            </c:when>
            <c:otherwise>
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del;submit1');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable');">
            </c:otherwise>
        </c:choose>

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">行政收费（${version.record_flag_name}：${version.version_no}）
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

        <form method="post" name="queryOp" id="queryOp" action="AdminManager">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">线路:</td>
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
                    <td class="table_edit_tr_td_label">行政处理原因:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_adminManagerID" name="q_adminManagerID" require="false" dataType="NotEmpty" msg="应选择行政处理原因" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_adminHandleReason" />
                        </select>
                    </td>
                </tr>
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">票卡主类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardMainID" name="q_cardMainID" onchange="setSelectValues('queryOp', 'q_cardMainID', 'q_cardSubID', 'commonVariable1');" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">票卡子类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_cardSubID" name="q_cardSubID" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                    </td>
                    <td class="table_edit_tr_td_label">行政处理罚金(分):</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_adminCharge" id="q_adminCharge" style="width:140px" maxlength="4" require="false" dataType="integer|LimitB" min="1" max="4" msg="行政处理罚金最长为4位长度的数字" />
                    </td>
                    <td class="table_edit_tr_td_label" style="text-algin:left">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_lineID#q_stationID#q_cardMainID#q_cardSubID#q_adminManagerID#q_adminCharge');
                               setLineCardNames('queryOp','q_lineID','q_stationID','commonVariable','q_cardMainID','q_cardSubID','commonVariable1');"/>
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
                <table id="DataTableHead" class="table_list_block">
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td style="width:50px" class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td style="width:150px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');" >行政处理原因</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=true index="2" sortedby="asc" onclick="sortForTableBlock('clearStart');" >行政处理罚金(分)</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="3" sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡主类型</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="4" sortedby="asc" onclick="sortForTableBlock('clearStart');" >票卡子类型</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="5" sortedby="asc" onclick="sortForTableBlock('clearStart');" >线路</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="6" sortedby="asc" onclick="sortForTableBlock('clearStart');" >车站</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="7" sortedby="asc" onclick="sortForTableBlock('clearStart');" >版本号</td>
                    </tr>
                </table>
            </div>

            <div id="clearStart" class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" onMouseOut="outResultRow('detailOp', this);" 
                            onclick="setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');
                                setSelectValuesByRowPropertyName('detailOp', 'd_cardSubID', 'commonVariable1', 'typeIDs');
                                clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');
                                setPrimaryKeys('detailOp', 'd_lineID#d_stationID#d_cardMainID#d_cardSubID#d_adminManagerID');" 
                            id="${rs.lineId}#${rs.stationId}#${rs.cardMainId}#${rs.cardSubId}#${rs.adminManagerId}#${rs.versionNo}#${rs.recordFlag}"
                            typeIDs="${rs.cardMainId}#${rs.cardSubId}">

                            <td style="width:50px" id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.lineId}#${rs.stationId}#${rs.cardMainId}#${rs.cardSubId}#${rs.adminManagerId}#${rs.versionNo}#${rs.recordFlag}">
                                </input>
                            </td>
                            <td style="width:150px" id="adminManagerID" class="table_list_tr_col_data_block">
                                ${rs.adminManagerIdText}
                            </td>
                            <td style="width:100px" id="adminCharge" class="table_list_tr_col_data_block">
                                ${rs.adminCharge}
                            </td>
                            <td style="width:100px" id="cardMainID" class="table_list_tr_col_data_block">
                                ${rs.cardMainIdText}
                            </td>
                            <td style="width:100px" id="cardSubID" class="table_list_tr_col_data_block">
                                ${rs.cardSubIdText}
                            </td>
                            <td style="width:100px" id="lineID" class="table_list_tr_col_data_block">
                                ${rs.lineIdText}
                            </td>
                            <td style="width:100px" id="stationID" class="table_list_tr_col_data_block">
                                ${rs.stationIdText}
                            </td>
                            <td style="width:100px" id="versionNo" class="table_list_tr_col_data_block">
                                ${rs.versionNo}
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

        <FORM method="post" name="detailOp" id="detailOp" action="AdminManager" >
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="adminManagerIdText,adminCharge,cardMainIdText,cardSubIdText,lineIdText,stationIdText,versionNo" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="行政收费.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/AdminManagerExportAll" />

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <DIV id="detail" class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">线路:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_lineID" name="d_lineID" onChange="setSelectValues('detailOp', 'd_lineID', 'd_stationID', 'commonVariable');" 
                                    dataType="NotEmpty" msg="线路不可以为空" style="width:140px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=all_option_list_pub_line" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label"><div align="right">车站:</div></td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_stationID" name="d_stationID" dataType="NotEmpty" msg="车站不可以为空" style="width:140px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=all_option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                        <td class="table_edit_tr_td_label">票卡主类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardMainID" name="d_cardMainID" onchange="setSelectValues('detailOp', 'd_cardMainID', 'd_cardSubID', 'commonVariable1');" 
                                    dataType="NotEmpty" msg="票卡主类型不可以为空" style="width:140px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardMain" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">票卡子类型:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_cardSubID" name="d_cardSubID" dataType="NotEmpty" msg="票卡子类型不可以为空" style="width:140px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_cardSub" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">行政处理原因:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_adminManagerID" name="d_adminManagerID" require="true" dataType="NotEmpty" msg="应选择行政处理原因" style="width:140px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_adminHandleReason" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">行政处理罚金(分):</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_adminCharge" id="d_adminCharge" maxlength="4" require="true" style="width:140px" dataType="integer|LimitB" min="1" max="4" value="" msg="行政处理罚金最长为4位长度的数字"/>
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

            <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','q_lineID','q_stationID','commonVariable','q_cardMainID','q_cardSubID','commonVariable1');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->
        
    </body>
</html>