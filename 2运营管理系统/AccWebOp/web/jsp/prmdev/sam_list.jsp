<%-- 
    Document   : sam_list
    Created on : 2017-6-14, 20:20:25
    Author     : lind
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SAM卡对照表</title>
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
                    setPrimaryKeys('detailOp', 'd_samLogicalID');
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
                    setPrimaryKeys('detailOp', 'd_samLogicalID');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable');">
            </c:otherwise>
        </c:choose>

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">SAM卡对照表（${version.record_flag_name}：${version.version_no}）
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

        <form method="post" name="queryOp" id="queryOp" action="SamList">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">SAM卡逻辑卡号:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_samLogicalID" id="q_samLogicalID" dataType="NumAndEng" style="width:140px" maxlength="20" require="false" msg="逻辑卡号应英文数字字符"/>
                    </td>

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
                </tr>    
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">设备类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_devTypeId" name="q_devTypeId" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_devtype" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">SAM卡类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_samTypeId" name="q_samTypeId" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_samTypes" />
                        </select>
                    </td>
                    
                    <td class="table_edit_tr_td_label" rowspan="2">
                        
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_samLogicalID#q_lineID#q_stationID#q_devTypeId#q_samTypeId');setLineCardNames('queryOp','q_lineID','q_stationID','commonVariable','','','');"/>
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
                        <td style="width:50px" class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td style="width:120px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');" >SAM卡逻辑卡号</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="2" sortedby="asc" onclick="sortForTableBlock('clearStart');" >SAM类型代码</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=true index="3" sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备ID</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="4" sortedby="asc" onclick="sortForTableBlock('clearStart');" >设备类型</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="5" sortedby="asc" onclick="sortForTableBlock('clearStart');" >线路</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="6" sortedby="asc" onclick="sortForTableBlock('clearStart');" >车站</td>
                    </tr>
                </table>
            </div>

            <div id="clearStart" class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" onMouseOut="outResultRow('detailOp', this);" 
                            onclick="setSelectValuesByRow('detailOp', 'd_stationID', 'commonVariable');clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');" 
                            id="${rs.lineId}#${rs.stationId}#${rs.samLogicalId}#${rs.versionNo}#${rs.recordFlag}">

                            <td style="width:50px" id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.samLogicalId}#${rs.versionNo}#${rs.recordFlag}">
                                </input>
                            </td>
                            <td style="width:120px" id="samLogicalID" class="table_list_tr_col_data_block">
                                ${rs.samLogicalId}
                            </td>
                            <td style="width:100px" id="samTypeCode" class="table_list_tr_col_data_block">
                                ${rs.samTypeCodeText}
                            </td>
                            <td style="width:100px" id="deviceID" class="table_list_tr_col_data_block">
                                ${rs.deviceId}
                            </td>
                            <td style="width:100px" id="deviceTypeID" class="table_list_tr_col_data_block">
                                ${rs.deviceTypeIdText}
                            </td>
                            <td style="width:100px" id="lineID" class="table_list_tr_col_data_block">
                                ${rs.lineIdText}
                            </td>
                            <td style="width:100px" id="stationID" class="table_list_tr_col_data_block">
                                ${rs.stationIdText}
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

        <FORM method="post" name="detailOp" id="detailOp" action="SamList" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getSamLogicalId,getSamTypeCodeText,getDeviceId,getDeviceTypeIdText,getLineIdText,getStationIdText" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="SAM卡对照表.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/SamListExportAll"/>
            
            <DIV id="detail" class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">线路: </td>
                        <td class="table_edit_tr_td_input">
                            <select  name="d_lineID" id="d_lineID" onChange="setSelectValues('detailOp', 'd_lineID', 'd_stationID', 'commonVariable');" 
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
                        <td class="table_edit_tr_td_label">设备类型: </td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_deviceTypeID" name="d_deviceTypeID" require="true" dataType="LimitB" min="1" msg="没有选择设备类型" style="width:140px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_devtype" />
                            </select>	
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">设备ID:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_deviceID" id="d_deviceID" style="width:140px" maxLength="3" dataType="integer|LimitB|NotEmpty" min="3" max="3" msg="设备ID应为3位数字！"/>
                        </td>
                        <td class="table_edit_tr_td_label">SAM类型代码:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_samTypeCode" name="d_samTypeCode" require="true" dataType="LimitB" min="1" msg="没有选择SAM类型类型" style="width:140px">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_samTypes" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label">SAM卡逻辑卡号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_samLogicalID" id="d_samLogicalID" require="true" style="width:140px" maxlength="16" dataType="NumAndEng|LimitB" min="16" max="16" msg="逻辑卡号应为16位英文数字字符。"/>
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
            <c:set var="addQueryMethod" scope="request" value="setLineCardNames('detailOp','q_lineID','q_stationID','commonVariable','','','');"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_samLogicalID');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
    </body>
</html>