<%-- 
    Document   : station_code
    Created on : 2017-6-12
    Author     : xiaowu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>车站表</title>

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        <script language="JavaScript">
            //
            function setLcIp(){
                var lineIdSelect=document.getElementById("d_line_id");
                var index=lineIdSelect.selectedIndex;
                var lineIdWithLiIpString = document.getElementById("lineIdWithLiIp").value;
                var arr_lineIdWithLiIp = lineIdWithLiIpString.split(";");
                document.getElementById("d_lc_ip").value = arr_lineIdWithLiIp[index - 1].split(":")[1]; 
                //'ip5','ip6','ip7','ip8'
                document.getElementById("ip5").value = arr_lineIdWithLiIp[index - 1].split(":")[1].split(".")[0];
                document.getElementById("ip6").value = arr_lineIdWithLiIp[index - 1].split(":")[1].split(".")[1];
                document.getElementById("ip7").value = arr_lineIdWithLiIp[index - 1].split(":")[1].split(".")[2];
                document.getElementById("ip8").value = arr_lineIdWithLiIp[index - 1].split(":")[1].split(".")[3];
            }
        </script>
    </head>
    <c:choose>
        <c:when test="${version.version_no == '0000000000'}">
            <body onload="initDocument('queryOp', 'detail');
                    initDocument('detailOp', 'detail');
                    setInvisable('detailOp', 'clone');
                    setPrimaryKeys('detailOp', 'd_line_id#d_station_id');
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
                    setPrimaryKeys('detailOp', 'd_line_id#d_station_id');
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
                <td colspan="4">车站表（
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

        <form method="post" name="queryOp" id="queryOp" action="StationCode">
            <!-- 页面用到的变量 通用模板 -->

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />

            <table  class="table_edit">
                <tr class="table_edit_tr">
                   <td class="table_edit_tr_td_label">线路:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_line_id" name="q_line_id">				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                        </select>
                    </td>
                    <td class="table_edit_tr_td_label">运营商：</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_contc_id" name="q_contc_id">				     
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_contc" />
                        </select>
                    </td>
                  
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_line_id#q_contc_id');"/>
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
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="1" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">线路</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="2" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">车站ID</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="3" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">车站中文名称</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="4" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">车站英文名称</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="5" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">车站维文名称</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="6" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 110px">车站计算机的IP地址</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="7" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">运营商</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="8" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">LC的IP地址</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="9" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">序号</td>
                    <td id="orderTd" class="table_list_tr_col_head" isDigit=false index="10" sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 100px">所属线路</td>
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
                                 onclickSetIpValue('ip1','ip2','ip3','ip4','${rs.sc_ip}');
                                 onclickSetIpValue('ip5','ip6','ip7','ip8','${rs.lc_ip}')" 
                        id="${rs.line_id}#${rs.station_id}#${rs.version_no}#${rs.record_flag}">

                        
			<td id="rectNo1" class="table_list_tr_col_data_block">
                            <input type="checkbox" name="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                   value="${rs.line_id}#${rs.station_id}">
                            </input>
                        </td>
                        <td id="line_id" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.line_name}
                        </td>
                        <td id="station_id" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.station_id}
                        </td>
                        <td id="chinese_name" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.chinese_name}
                        </td>
                        <td id="english_name" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.english_name}
                        </td>
                        <td id="uygur_name" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.uygur_name}
                        </td>
                        <td id="sc_ip" class="table_list_tr_col_data_block" style="width: 110px">
                            ${rs.sc_ip}
                        </td>
                        <td id="contc_id" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.contc_name}
                        </td>
                        <td id="lc_ip" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.lc_ip}
                        </td>
                        <td id="sequence" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.sequence}
                        </td>
                        <td id="belong_line_id" class="table_list_tr_col_data_block" style="width: 100px">
                            ${rs.belong_line_name}
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

        <FORM method="post" name="detailOp" id="detailOp" action="StationCode" >
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            
            <!--导出全部参数 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                value="getLine_id,getStation_id,getChinese_name,getEnglish_name,getUygur_name,getSc_ip,getContc_id,getLc_ip,getSequence,getBelong_line_id" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="车站表.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/stationCodeExportAll"/>
            
            <DIV id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" style="width: 60px">线路:</td>
                        <td class="table_edit_tr_td_input" style="width: 60px">
                            <input type="hidden" id="lineIdWithLiIp" name="lineIdWithLiIp" value="${lineIdWithLcIp}" />
<!--                            <input type="text" id="d_line_id" name="d_line_id" size="10" maxLength="2"  require="true" dataType="Integer|Range" operator="GreaterThanEqual" to="0" min="0" max="99" msg="车站ID必须为大于0的整数!"/>-->
                            <select  name="d_line_id"  id="d_line_id" require="true" dataType="LimitB" min="1" msg="没有选择线路" onchange="if(this.value != '') setLcIp()">
                                <!-- 线路 -->
                                <option value="">=请选择=</option>
                                <c:forEach items="${lines}" var="line"> 
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
                        <td class="table_edit_tr_td_label" style="width: 120px">车站ID:</td>
                        <td class="table_edit_tr_td_input" style="width: 160px">
                            <input type="text" id="d_station_id" name="d_station_id" size="10" maxLength="2"  require="true" dataType="Integer|Range" operator="GreaterThanEqual" to="0" min="0" max="99" msg="车站ID必须为大于0的整数!"/>
                        </td>
                        <td class="table_edit_tr_td_label" style="width: 100px">车站中文名称:</td>
                        <td class="table_edit_tr_td_input" style="width: 160px">
                            <input type="text" id="d_chinese_name" name="d_chinese_name" size="10" maxLength="20" dataType="Require" msg="车站中文名称不能为空!"/>
                        </td>
                        <td class="table_edit_tr_td_label" style="width: 100px">车站英文名称:</td>
                        <td class="table_edit_tr_td_input" style="width: 120px">
                            <input type="text" id="d_english_name" name="d_english_name" size="10" maxLength="50" dataType="English|NotEmpty"   msg="车站英文名称必须为英文且不能为空" />
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" style="width: 60px">运营商:</td>
                        <td class="table_edit_tr_td_input" style="width: 60px">
                            <select  name="d_contc_id"  id="d_contc_id" dataType="Require" msg="运营商不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_contc" />
                            </select>	
                        </td>
                        <td class="table_edit_tr_td_label" style="width: 120px">车站计算机的IP地址:</td>
                        <td class="table_edit_tr_td_input" style="width: 160px">
                            <input name="ip1" id ="ip1" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="车站计算机第1个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip1,ip1,ip2) onkeyup=keyUpEventForIp1(ip1)>.
                            <input name="ip2" id ="ip2" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="车站计算机第2个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip1,ip2,ip3) onkeyup=keyUpEvent(ip2)>.
                            <input name="ip3" id ="ip3" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="车站计算机第3个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip2,ip3,ip4) onkeyup=keyUpEvent(ip3)>.
                            <input name="ip4" id ="ip4" class=ip_input style="width:25px" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="车站计算机第4个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip3,ip4,ip4) onkeyup=keyUpEvent(ip4)>
                            <input type="hidden" name="d_sc_ip" id="d_sc_ip" />
                        </td>
                        <td class="table_edit_tr_td_label" style="width: 100px">LC的IP地址:</td>
                        <td class="table_edit_tr_td_input" style="width: 160px">
                            <input name="ip5" id ="ip5" class="ip_input" readonly="readonly" style="width:25px; border: 1px solid #DDD; background-color: #f0f0f0; color:#ACA899;" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="LC的第1个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip5,ip5,ip6) onkeyup=keyUpEventForIp1(ip5)>.
                            <input name="ip6" id ="ip6" class="ip_input" readonly="readonly" style="width:25px; border: 1px solid #DDD; background-color: #f0f0f0; color:#ACA899;" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="LC的第2个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip5,ip6,ip7) onkeyup=keyUpEvent(ip6)>.
                            <input name="ip7" id ="ip7" class="ip_input" readonly="readonly" style="width:25px; border: 1px solid #DDD; background-color: #f0f0f0; color:#ACA899;" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="LC的第3个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip6,ip7,ip8) onkeyup=keyUpEvent(ip7)>.
                            <input name="ip8" id ="ip8" class="ip_input" readonly="readonly" style="width:25px; border: 1px solid #DDD; background-color: #f0f0f0; color:#ACA899;" maxlength=3 require="true" dataType="NumAndEng|NotEmpty" msg="LC的第4个ip地址栏不能为空" onkeydown=keyDownEvent(this,ip7,ip8,ip8) onkeyup=keyUpEvent(ip8)>
                            <input type="hidden" name="d_lc_ip" id="d_lc_ip" />
                        </td>
                        <td class="table_edit_tr_td_label" style="width: 100px">车站维文名称:</td>
                        <td class="table_edit_tr_td_input" style="width: 120px">
                            <input type="text" id="d_uygur_name" name="d_uygur_name" size="10" maxLength="66" dataType="Require" msg="车站维文名称不能为空!"/>
                        </td>
                    </tr>
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label" style="width: 60px">所属线路:</td>
                        <td class="table_edit_tr_td_input" style="width: 60px">
                            <select  name="d_belong_line_id"  id="d_belong_line_id"  dataType="Require" msg="所属线路不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line" />
                            </select>
                        </td>
                        <td class="table_edit_tr_td_label" style="width: 120px">序号:</td>
                        <td class="table_edit_tr_td_input" style="width: 160px">
                            <input type="text" id="d_sequence" name="d_sequence" maxLength="2" min="2" max="2" require="true" dataType="LimitB|Number"  msg="序号应为2位数字" size="10"/>
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
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_line_id#d_station_id');
                                                                 getipvalue('ip1','ip2','ip3','ip4','d_sc_ip');
                                                                 getipvalue('ip5','ip6','ip7','ip8','d_lc_ip')"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');
                                                              getipvalue('ip1','ip2','ip3','ip4','d_sc_ip');
                                                              getipvalue('ip5','ip6','ip7','ip8','d_lc_ip')"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>

    </body>
</html>
