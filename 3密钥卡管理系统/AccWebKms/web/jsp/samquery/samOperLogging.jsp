<%-- 
    Document   : samOperLogging
    Created on : 2017-8-26, 20:33:54
    Author     : taidb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>操作日志</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>    
    </head>

    <body onload="preLoadVal('q_beginTime', 'q_endTime', 85);
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')
          ">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">操作日志
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="samOperLoggingAction">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">操作员:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_operatorId" name="q_operatorId" >
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_operator" />
                        </select>
                    </td>          
                    <td class="table_edit_tr_td_label">操作:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_operType" name="q_operType" require="false" dataType="NotEmpty" msg="操作类型不能为空!" >
                            <option value="">全部</option>
                            <c:forEach items="${operTypes}" var="OP"> 
                                <c:choose>                                
                                    <c:when test="${OP.operType == ''}">
                                        <option value="${OP.operType}"></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${OP.operType}">${OP.operType}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">系统名称:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_sysType" name="q_sysType">
                            <option value="">全部</option>
                            <c:forEach items="${sysTypes}" var="ST"> 
                                <c:choose>                                
                                    <c:when test="${ST.sysType == 0}">
                                        <option value="${ST.sysType}">管钥卡管理系统</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${ST.sysType}">管钥卡发行系统</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="table_edit_tr_td_label">操作开始日期: </td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_beginTime" id="q_beginTime" value="" size="12" require="true" dataType="Date|NotEmpty" format="ymd"  
                               msg="操作开始日期不能为空，格式为(YYYY-MM-dd)!" />
                        <a href="javascript:openCalenderDialogByID('q_beginTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>
                    <td class="table_edit_tr_td_label">操作结束日期: </td>
                    <td class="table_edit_tr_td_input" >
                        <input type="text" name="q_endTime" id="q_endTime" value="" size="12" require="ture"  
                               dataType="Date|ThanDate|CompareDateByNum" format="ymd" to="q_beginTime" operator="LessThanEqual" num="90"
                               msg="操作结束日期格式为(YYYY-MM-dd)且大于操作开始日期不超过90天!" />
                        <a href="javascript:openCalenderDialogByID('q_endTime','false');">
                            <img src="./images/calendar.gif" width="15" height="15" border="0" style="block"/>
                        </a>
                    </td>              
                    <td class="table_edit_tr_td_label" rowspan="1">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>                      
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_operatorId#q_operType#q_sysType#q_beginTime#q_endTime');"/>         
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">操作员</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">操作时间</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">操作模块</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">操作</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="5"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 430px">操作明细</td>
                    </tr>
                </table>
            </div>
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, '');
                                    setPageControl('detailOp');" 
                           id="${rs.operatorId}# ${rs.opTime}">
                            <td  id="operatorId" class="table_list_tr_col_data_block" style="width:100px;">
                                ${rs.operatorName}
                            </td>
                            <td  id="opTime" class="table_list_tr_col_data_block" style="width:200px;">
                                ${rs.opTime}
                            </td>
                            <td  id="moduleName" class="table_list_tr_col_data_block" style="width:150px;">
                                ${rs.moduleName}
                            </td>
                            <td  id="operType" class="table_list_tr_col_data_block" style="width:100px;">
                                ${rs.operType}
                            </td>
                            <td  id="description" class="table_list_tr_col_data_block" style="width:430px;">
                                ${rs.description}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <!-- 表头 通用模板 -->
        <FORM method="post" name="detailOp" id="detailOp" action="samOperLoggingAction" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getOperatorName,getOpTime,getModuleName,getOperType,getDescription" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="操作日志.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/SamOperLoggingExportAll"/>
            <div id="detail"  class="divForTableDataDetail" >

            </div>
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
