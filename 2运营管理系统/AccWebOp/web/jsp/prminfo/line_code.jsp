<%-- 
    Document   : line_code
    Created on : 2017-6-8
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>线路管理</title>

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
                    setPrimaryKeys('detailOp', 'd_line_id');
                    setControlsDefaultValue('queryOp');
                    setListViewDefaultValue('detailOp', 'clearStart');
                    setQueryControlsDefaultValue('queryOp', 'detailOp');
                    setPageControl('detailOp');
		    setTableRowBackgroundBlock('DataTable')">
            </c:when>
            <c:otherwise>
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'add;modify;del;submit1');
                    setPrimaryKeys('detailOp', 'd_line_id');
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
                <td colspan="4">线路管理（
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

        <form method="post" name="queryOp" id="queryOp" action="LineCode">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label" >线路ID:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_lineId" id="q_lineId" value="${q_lineId}" size="10"  maxLength="3" require="false" dataType="LimitB" min="2" max="2" msg="线路ID应为2位"/>
                    </td>
                    <td class="table_edit_tr_td_label" >线路名称:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_lineName" id="q_lineName" value="${q_lineName}" size="10"  maxLength="5" require="false" msg="线路名称最多为5个字"/>
                    </td>
                  
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_lineId#q_lineName');"/>
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
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">线路ID</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="2" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">线路名称</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="3" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">线路维文名称</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="4" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">线路IP地址</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="5" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">线路标识</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="6" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">序号</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="7" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">客流方向</td>
                </tr>
	    </table>
        </div>
	
	<div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                <c:forEach items="${ResultSet}" var="rs">
		
                    <tr class="table_list_tr_data" 
                        onMouseOver="overResultRow('detailOp', this);" 
                        onMouseOut="outResultRow('detailOp', this);" 
                        onclick="clickResultRow('detailOp', this, 'detail');setPageControl('detailOp');
                                 onclickSetIpValue('ip1','ip2','ip3','ip4','${rs.lc_ip}')" 
                        id="${rs.line_id}#${rs.version_no}#${rs.record_flag}">

                        <td id="rectNo1" class="table_list_tr_col_data_block">
                            <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                   value="${rs.line_id}">
                            </input>
                        </td>
                        <td id="line_id" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.line_id}
                        </td>
                        <td id="line_name" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.line_name}
                        </td>
                        <td id="uygur_name" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.uygur_name}
                        </td>
                        <td id="lc_ip" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.lc_ip}
                        </td>
                        <td id="line_flag" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.line_flag_text}
                        </td>
                        <td id="sequence" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.sequence}
                        </td>
                        <td id="direction_flag" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.direction_flag_name}
                        </td>
                    </tr>
                </c:forEach>
            </table>
	   </DIV>
        </div>

        <!-- 表头 通用模板 -->
        <c:set var="pTitleName" scope="request" value="明细"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <FORM method="post" name="detailOp" id="detailOp" action="LineCode" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <!--导出全部参数 -->
            
            
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getLine_id,getLine_name,getUygur_name,getLc_ip,getLine_flag_text,getSequence,getDirection_flag" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="线路.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/LineCodeExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
                <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">线路ID:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_line_id" name="d_line_id" size="10" maxlength="2" require="true" maxLength="2" dataType="Number|LimitB"  min="2" max="2" msg="线路ID应为2位数字字符!"/>
                        </td>
                        <td class="table_edit_tr_td_label">线路名称:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_line_name" name="d_line_name" size="10" maxlength="50" dataType="NotEmpty" msg="线路名称不能为空!"/>
                        </td>
                        <td class="table_edit_tr_td_label">线路IP地址:</td>
                        <td class="table_edit_tr_td_input">
                            <input name="ip1" id ="ip1" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="第1个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip1,ip1,ip2) onkeyup=keyUpEventForIp1(ip1)>.
                            <input name="ip2" id ="ip2" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="第2个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip1,ip2,ip3) onkeyup=keyUpEvent(ip2)>.
                            <input name="ip3" id ="ip3" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="第3个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip2,ip3,ip4) onkeyup=keyUpEvent(ip3)>.
                            <input name="ip4" id ="ip4" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="第4个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip3,ip4,ip4) onkeyup=keyUpEvent(ip4)>
                            <input type="hidden" name="d_lc_ip" id="d_lc_ip" />
                        </td>
                    </tr>

                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">序号:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_sequence" name="d_sequence" maxLength="2" size="10" min="2" max="2" dataType="LimitB|Number" msg="序号应为2位数字字符!"/>
                        </td>
                        <td class="table_edit_tr_td_label">线路维文名称:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" id="d_uygur_name" name="d_uygur_name" size="10" maxlength="50" dataType="NotEmpty" msg="线路维文名称不能为空!"/>
                        </td>
                        <td class="table_edit_tr_td_label">线路标识:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_line_flag" name="d_line_flag" require="true" dataType="NotEmpty" msg="线路标识不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_lineFlag" />
                            </select>
                         
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">客流方向:</td>
                        <td class="table_edit_tr_td_input">
                            <select id="d_direction_flag" name="d_direction_flag" require="true" dataType="NotEmpty" msg="客流方向不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_directionFlag" />
                            </select>
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
            <c:set var="expAll" scope="request" value="1"/>
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="btGoPage" scope="request" value="1"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_line_id');getipvalue('ip1','ip2','ip3','ip4','d_lc_ip');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');getipvalue('ip1','ip2','ip3','ip4','d_lc_ip');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

        
    </body>
</html>
