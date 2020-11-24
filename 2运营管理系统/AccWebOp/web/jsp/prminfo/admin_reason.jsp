<%-- 
    Document   : admin_reason
    Created on : 2017-6-16, 14:36:01
    Author     : lind
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>行政处理原因</title>
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
                    setPrimaryKeys('detailOp', 'd_adminManagerID');
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
                    setPrimaryKeys('detailOp', 'd_adminManagerID');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
                    setTableRowBackgroundBlock('DataTable');">
            </c:otherwise>
        </c:choose>

        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">行政处理原因（${version.record_flag_name}：${version.version_no}）
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

        <form method="post" name="queryOp" id="queryOp" action="AdminReason">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">行政处理原因ID:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_adminManagerID" id="q_adminManagerID" size="20" require="false" maxLength="2" dataType="Number|LimitB" min="2" max="2" msg="行政处理原因ID应为2位数字字符"/>
                    </td>
                    <td class="table_edit_tr_td_label">行政处理原因名称:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_adminManagerName" id="q_adminManagerName" size="20" maxLength="90" require="false"/>
                    </td>
                    
                    <td class="table_edit_tr_td_label" rowspan="2">
                        
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_adminManagerID#q_adminManagerName');"/>
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
                        <td style="width:100px" id="orderTd" class="table_list_tr_col_head_block"  isDigit=true index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');" >行政处理原因ID</td>
                        <td style="width:300px" id="orderTd" class="table_list_tr_col_head_block" isDigit=false index="2" sortedby="asc" onclick="sortForTableBlock('clearStart');" >行政处理原因名称</td>
                    </tr>
                </table>
            </div>

            <div id="clearStart" class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >

                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');" 
                            id="${rs.adminManagerId}#${rs.versionNo}#${rs.recordFlag}">

                            <td style="width:50px" id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.adminManagerId}#${rs.versionNo}#${rs.recordFlag}">
                                </input>
                            </td>
                            <td style="width:100px" id="adminManagerID" class="table_list_tr_col_data_block">
                                ${rs.adminManagerId}
                            </td>
                            <td style="width:300px" id="adminManagerName" class="table_list_tr_col_data_block">
                                ${rs.adminManagerName}
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

        <FORM method="post" name="detailOp" id="detailOp" action="AdminReason" >
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="adminManagerId,adminManagerName" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="行政处理原因.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/AdminReasonExportAll" />

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <DIV id="detail" class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">行政处理原因ID</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_adminManagerID" id="d_adminManagerID" size="20" require="true" maxLength="2" dataType="Number|LimitB"  min="2" max="2" msg="行政处理原因ID应为2位数字字符"/>
                        </td>
                        <td class="table_edit_tr_td_label">行政处理原因名称</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_adminManagerName" id="d_adminManagerName" size="20" require="true"  maxLength="90"  dataType="NotEmpty|LimitContainChinese" min="1" max="90"  value=""  msg="行政处理原因名称不能为空，且小于90个字符"/>
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

            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_adminManagerID');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        <!-- 状态栏 通用模板 -->
        
    </body>
</html>