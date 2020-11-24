<%-- 
    Document   : device_type
    Created on : 2017-6-13, 9:55:36
    Author     : lind
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>设备类型</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="initDocument('queryOp', 'detail');
                        initDocument('detailOp','detail');
                        setInvisable('detailOp','clone');
                        setPrimaryKeys('detailOp','d_devTypeId');
                        setControlsDefaultValue('queryOp');
                        setListViewDefaultValue('detailOp','clearStart');
                        setQueryControlsDefaultValue('queryOp', 'detailOp');
                        setTableRowBackgroundBlock('DataTable');">
        </c:when>
        <c:otherwise>
            <body onload="initDocument('queryOp', 'detail');
                        initDocument('detailOp','detail');
                        setInvisable('detailOp','add;modify;del;submit1');
                        setPrimaryKeys('detailOp','d_devTypeId');
                        setControlsDefaultValue('queryOp');
                        setListViewDefaultValue('detailOp','clearStart');
                        setQueryControlsDefaultValue('queryOp', 'detailOp');
                        setTableRowBackgroundBlock('DataTable');">
        </c:otherwise>
    </c:choose>
                
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">设备类型（${version.record_flag_name}：${version.version_no}）
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

        <form method="post" name="queryOp" id="queryOp" action="DeviceType">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">安装位置:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_installLocation" name="q_installLocation" style="width:140px">
                            <option value="">=请选择=</option>
                            <option value="1">车站</option>
                            <option value="2">LC</option>
                            <option value="3">制票室</option>
                            <option value="4">ACC</option>
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label">设备类型ID:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_devTypeId" id="q_devTypeId" style="width:140px"  maxLength="2" require="false" dataType="integer|LimitB" min="2" max="2" msg="设备类型ID应为2位数字"/>
                    </td> 
                    
                    <td class="table_edit_tr_td_label">设备类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_deviceTypeName" name="q_deviceTypeName" style="width:140px">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_devtype" />
                        </select>
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2">

                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_installLocation#q_devTypeId#q_deviceTypeName');"/>
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
                    <tr class="table_list_tr_head_block" id="ignore">
                        <td style="width:50px" class="table_list_tr_col_head_block">
                            <input type="checkbox" id="rectNoAll" name="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=true index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');">设备类型ID</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="2" sortedby="asc" onclick="sortForTableBlock('clearStart');">设备类型</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="3" sortedby="asc" onclick="sortForTableBlock('clearStart');">安装位置</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="4" sortedby="asc" onclick="sortForTableBlock('clearStart');">是否售票</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="5" sortedby="asc" onclick="sortForTableBlock('clearStart');">是否充值</td>
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="6" sortedby="asc" onclick="sortForTableBlock('clearStart');">版本号</td>
                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');" 
                            id="${rs.devTypeId}#${rs.versionNo}#${rs.recordFlag}">

                            <td style="width:50px" id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.devTypeId}#${rs.versionNo}#${rs.recordFlag}">
                                </input>
                            </td>
                            <td style="width:100px" id="devTypeId" class="table_list_tr_col_data_block">
                                ${rs.devTypeId}
                            </td>
                            <td style="width:100px" id="devTypeName" class="table_list_tr_col_data_block">
                                ${rs.devTypeName}
                            </td>
                            <td style="width:100px" id="installLocation" class="table_list_tr_col_data_block">
                                ${rs.installLocationTxt}
                            </td>
                            <td style="width:100px" id="isSale" class="table_list_tr_col_data_block">
                                ${rs.isSaleTxt}
                            </td>
                            <td style="width:100px" id="isCharge" class="table_list_tr_col_data_block">
                                ${rs.isChargeTxt}
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

        <FORM method="post" id="detailOp" name="detailOp" action="DeviceType" onSubmit="return Validator.Validate(this,2);">
            
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getDevTypeId,getDevTypeName,getInstallLocationTxt,getIsSaleTxt,getIsChargeTxt,getVersionNo" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="设备类型.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/DeviceTypeExportAll"/>
            
            <DIV id="detail" class="divForTableDataDetail">
                <table class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">设备类型ID:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_devTypeId" name="d_devTypeId" style="width:140px" maxlength="2" dataType="integer" msg="设备类型ID不能为空,且为数字"/>
                        </td>
                        <td class="table_edit_tr_td_label">类型名称:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_devTypeName" name="d_devTypeName" style="width:140px"  maxlength="40" dataType="NotEmpty|LimitContainChinese" min="1" max="40" msg="类型名称长度为1-40个字符，不能为空"/>
                        </td>
                        <td class="table_edit_tr_td_label">安装位置:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_installLocation" name="d_installLocation" require="true" dataType="LimitB" min="1" msg="没有选择安装位置" style="width:140px">
                                <option value="">=请选择=</option>
                                <option value="1">车站</option>
                                <option value="2">LC</option>
                                <option value="3">制票室</option>
                                <option value="4">ACC</option>
                            </select>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">是否售票:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_isSale" name="d_isSale" require="true" dataType="LimitB" min="1" msg="没有选择是否售票" style="width:140px">
                                <option value="0">不可发售</option>
                                <option value="1">可发售</option>
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label">是否充值:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_isCharge" name="d_isCharge" require="true" dataType="LimitB" min="1" msg="没有选择是否充值" style="width:140px">
                                <option value="0">不可充值</option>
                                <option value="1">可充值</option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                </table>
            </DIV>

            <c:import url="/jsp/common/common_template.jsp?template_name=version_eval" />

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="clone" scope="request" value="1"/>
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="submit" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />

            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_devTypeId');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
    </body>
</html>
