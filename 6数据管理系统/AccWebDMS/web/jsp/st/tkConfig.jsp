<%-- 
    Document   : tkConfig
    Created on : 2017-9-20, 18:01:35
    Author     : limj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>票务管理系统 配置信息</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="style"/>
    </head>
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPrimaryKeys('detailOp', 'd_origin_table_name');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')">
        <table class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">票务管理系统 配置信息</td>
            </tr>
        </table>
        <!-- 表头 通用模板-->
        <c:set var="pTitleName" scope="request" value="查询" />
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />
        <form method="post" name="queryOp" id="queryOp" action="tkConfig">
            <!-- 页面用到的变量 通用模板-->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable"/>
            <table class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">源表表名：</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_origin_table_name" id="q_origin_table_name" size="35"
                                require="false" maxLength="40" dataType="NotEmpty" msg="源表名"/>
  
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="1">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init"/>
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_origin_table_name')"/>
                        <c:set var="clickMethod" scope="request" value="btnClick('queryOp','clearStart','detail');"/>
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
                    </td>
                </tr>
            </table>
        </form>
        
        <!-- 状态栏 通用模板 -->
        <c:import url="/jsp/common/common_template.jsp?template_name=common_status_table" />
        <!--表头 通用模板-->
        <c:set var="pTitleName" scope="request" value="列表"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title"/>
        <div id="clearStartBlock" class="divForTableBlock">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead">
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序-->
                    <tr class="table_list_tr_head_block" id="ignore">
                        <td class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll"
                                   onclick="selectAll('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);setPageControl('detailOp');"/>
                        </td>
                        <td id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="1" sortedby="asc"
                            onclick="sortForTableBlock('clearStart');" style="width: 230px">源表表名</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="2"  sortedby="asc" 
                            onclick="sortForTableBlock('clearStart');" style="width: 100px" >保存天数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="3"  sortedby="asc" 
                            onclick="sortForTableBlock('clearStart');" style="width: 100px">最大记录数</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="3"  sortedby="asc" 
                            onclick="sortForTableBlock('clearStart');" style="width: 100px">清理标记</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=true index="3"  sortedby="asc" 
                            onclick="sortForTableBlock('clearStart');" style="width: 150px">分表名简写</td>
                    </tr>
                </table>
            </div>
            <div id="clearStart" class="divForTableBlockData">
                <table class="table_list_block" id="DataTable">
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data" onmouseover="overResultRow('detailOp', this);"
                            nMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickOneResultRow('detailOp', this, 'detail','rectNo');
                                    setPageControl('detailOp');" 
                            id="${rs.originTableName}">
                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" 
                                       value="${rs.originTableName}" />
                            </td>
                            <td id="origin_table_name" class="table_list_tr_col_data_block" style="width: 230px">
                                ${rs.originTableName}
                            </td>
                            <td id="keep_days" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.keepDays}
                            </td>
                            <td id="divide_recd_count" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.divideRecdCount}
                            </td>
                            <td id="clear_flag" class="table_list_tr_col_data_block" style="width: 100px">
                                ${rs.clearFlag}                                                     
                            </td>
                            <td id="db_name" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.abName}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <!--表头 通用模板-->
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title"/>
        <form method="post" name="detailOp" id="detailOp" action="tkConfig">
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable"/>
            <input type="hidden" name="expAllFields" id="d_expAllFields"  value="getOriginTableName,getKeepDays,getDivideRecdCount,getClearFlag,getAbName" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="票务管理系统配置信息.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl"  value="/tkConfigExportAll"/>
            <div id="detail" class="divForTableDataDetail">
                <table class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">源表表名：</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_origin_table_name" id="d_origin_table_name" 
                                   maxlength="50" dataType="NotEmpty" msg="源表名不能为空" disabled="disabled"/>
                        </td>
                        <td class="table_edit_tr_td_label">保存天数：</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_keep_days" id="d_keep_days" require="true"
                                   maxlength="3" dataType="Range|integer" min="1" max="210"
                                   msg="保存天数为数字，并在1-210之间！"/>
                        </td>
                        <td class="table_edit_tr_td_label">最大记录数：</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_divide_recd_count" id="d_divide_recd_count"
                                   require="true" maxlength="8" dataType="Range|integer" min="1"
                                   max="20000000" msg="最大记录数为数字，并在1-20000000之间！"/>
                        </td>
                    </tr>
                </table>
            </div>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>

            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />           
            <c:set var="addAfterClickModifyMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_origin_table_name');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </form>
            
        
    </body>
</html>
