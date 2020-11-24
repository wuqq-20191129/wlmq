<%-- 
    Document   : transfer_station_code
    Created on : 2017-6-13
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>换乘车站</title>

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
                    setPrimaryKeys('detailOp', 'd_line_id#d_station_id#d_transfer_line_id#d_transfer_station_id');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;del;submit1');
                    setPrimaryKeys('detailOp', 'd_line_id#d_station_id#d_transfer_line_id#d_transfer_station_id');
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
                <td colspan="4">换乘车站（
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

        <form method="post" name="queryOp" id="queryOp" action="TransferStationCode">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">换乘线路_1:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_line_id" name="q_line_id" onChange="setSelectValues('queryOp', 'q_line_id', 'q_station_id', 'commonVariable');" >				    
                            <!-- 线路 -->
                            <option value="">=请选择=</option>
                            <c:forEach items="${queryLines}" var="line"> 
                                <c:choose>
                                    <c:when test="${line.code_text == ''}">
                                        <option value="${line.code}">${line.code}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${line.code}">${line.code_text}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">换乘车站_1：</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_station_id" name="q_station_id" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable"/>
                        <!-- 车站 -->
                        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${queryLineStaions}" />
                    </td>
                    
                    <td class="table_edit_tr_td_label">换乘线路_2:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_line_id_2" name="q_line_id_2" onChange="setSelectValues('queryOp', 'q_line_id_2', 'q_station_id_2', 'commonVariable1');" >				    
                            <!-- 线路 -->
                            <option value="">=请选择=</option>
                            <c:forEach items="${queryLines}" var="line"> 
                                <c:choose>
                                    <c:when test="${line.code_text == ''}">
                                        <option value="${line.code}">${line.code}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${line.code}">${line.code_text}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">换乘车站_2：</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_station_id_2" name="q_station_id_2" >
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                        </select>
                        <c:set var="pVarName" scope="request" value="commonVariable1"/>
                        <!-- 车站 -->
                        <input type="hidden" id="${pVarName}" name="${pVarName}" value="${queryLineStaions}" />
                    </td>
                  
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_line_id#q_station_id#q_line_id_2#q_station_id_2');setLineCardNames('queryOp','q_line_id','q_station_id','commonVariable','q_line_id_2','q_station_id_2','commonVariable1');"/>
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
            <DIV id="clearStartHead"  class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >

                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td  align="center" class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
			</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">换乘线路_1</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="2" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">换乘车站_1</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="3" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">换乘线路_2</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="4" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">换乘车站_2</td>
                </tr>
	       </table>
            </div>
	    <div id="clearStart"  class="divForTableBlockData">
               <table class="table_list_block" id="DataTable" >
                <c:forEach items="${ResultSet}" var="rs">
                    <tr class="table_list_tr_data" 
                        onMouseOver="overResultRow('detailOp', this);" 
                        onMouseOut="outResultRow('detailOp', this);" 
                        onclick="setSelectValuesByRowPropertyName('detailOp','d_station_id','commonVariable1','typeIDs1');setSelectValuesByRowPropertyName('detailOp','d_transfer_station_id','commonVariable2','typeIDs2');
			clickResultRow('detailOp',this,'detail');setPageControl('detailOp');" 
                        id="${rs.line_id}#${rs.station_id}#${rs.transfer_line_id}#${rs.transfer_station_id}#${rs.version_no}#${rs.record_flag}"
                        typeIDs1="${rs.line_id}" typeIDs2="${rs.transfer_line_id}">

                        <td id="rectNo1" class="table_list_tr_col_data_block">
                            <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                   value="${rs.line_id}#${rs.station_id}#${rs.transfer_line_id}#${rs.transfer_station_id}#${rs.version_no}#${rs.record_flag}">
                            </input>
                        </td>
                        <td id="line_id" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.line_name}
                        </td>
                        <td id="station_id" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.station_name}
                        </td>
                        <td id="transfer_line_id" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.transfer_line_name}
                        </td>
                        <td id="transfer_station_id" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.transfer_station_name}
                        </td>
                    </tr>
                </c:forEach>
            </table>
	   </DIV>
        </DIV>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="TransferStationCode" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getLine_id,getStation_id,getTransfer_line_id,getTransfer_station_id" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="换乘车站.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/TransferStationCodeExportAll"/>
            
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">换乘线路_1:</td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_line_id"  id="d_line_id" onChange="setSelectValues('detailOp','d_line_id','d_station_id','commonVariable1');" require="true" dataType="LimitB" min="1" msg="没有选择线路">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">换乘车站_1:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_station_id" name="d_station_id" require="true" dataType="LimitB" min="1" msg="没有选择车站">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable1"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                        <td class="table_edit_tr_td_label">换乘线路_2:</td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_transfer_line_id"  id="d_transfer_line_id" 
                                     onChange="setSelectValuesTSC('detailOp','d_transfer_line_id','d_transfer_station_id','commonVariable2', 'd_station_id');" 
                                     require="true" dataType="LimitB" min="1" msg="没有选择线路">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">换乘车站_2:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_transfer_station_id" name="d_transfer_station_id" require="true" dataType="LimitB" min="1" msg="没有选择车站">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_none" />
                            </select>
                            <c:set var="pVarName" scope="request" value="commonVariable2"/>
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_station" />
                        </td>
                    </tr>
                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />


            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
           
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
	    <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_line_id#d_station_id#d_transfer_line_id#d_transfer_station_id');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');" />
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
    </body>
</html>
