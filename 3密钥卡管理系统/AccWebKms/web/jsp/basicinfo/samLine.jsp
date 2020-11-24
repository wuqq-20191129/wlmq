<%-- 
    Document   : samLineDefin
    Created on : 2017-8-23, 19:25:09
    Author     : ysw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>线路/ES定义</title>

        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>   
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
        
    </head>
 
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setPrimaryKeys('detailOp', 'd_lineCode');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">线路/ES定义</td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="samLineDefin">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">                                      
                    <td class="table_edit_tr_td_label">线路/ES代码:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_lineCode" id="q_lineCode" size="20" require="false" dataType="LimitB|Number" maxLength="20" msg="线路/ES代码应为两位数字" />                     
                    </td>
                    <td class="table_edit_tr_td_label">线路/ES名称:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_lineDesc" id="q_lineDesc" size="20" dataType="LimitB" maxLength="20" />                     
                    </td>

                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_lineCode#q_lineDesc');"/>
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
                        <td   class="table_list_tr_col_head_block">
                            <input type="checkbox" name="rectNoAll" id="rectNoAll" onclick="selectAllRecord('detailOp', 'rectNoAll', 'rectNo', 'clearStart', 0);"/>
                        </td>	
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">线路/ES代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">线路/ES名称</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 200px">类型</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 400px">备注</td> 
                    </tr>
                </table>
            </div>
            <!-- <DIV id="clearStart" align="center" style="position:relative; left:20; width:95%; height:30%;min-height: 200px;max-height: 250px; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1; overflow: scroll;">-->
            <div id="clearStart"  class="divForTableBlockData">
                <table class="table_list_block" id="DataTable" >
                    <c:forEach items="${ResultSet}" var="rs">                 
                        <tr class="table_list_tr_data"  onMouseOver="overResultRow('detailOp', this);" 
                            onMouseOut="outResultRow('detailOp', this);" 
                            onclick="clickResultRow('detailOp', this, 'detail');" 
                            id="${rs.line_es_code}" >

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.line_es_code}">
                                </input>
                            </td>                       
                            <td  id="lineCode" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.line_es_code}
                            </td>
                            <td  id="lineDesc" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.line_es_desc}
                            </td>
                            <td  id="codeText" class="table_list_tr_col_data_block" style="width: 200px">
                                ${rs.code_text}
                            </td> 
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 400px">
                                ${rs.remark}
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

        <FORM method="post" name="detailOp" id="detailOp" action="samLineDefin" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getLine_es_code,getLine_es_desc,getCode_text,getRemark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="线路(ES)定义.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/SamLineExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">线路/ES代码: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_lineCode" id="d_lineCode" size="15" maxLength="2" require="true" dataType="Number|LimitB" min="2" max="2" msg="线路代码应为两位数字"/>	
                        </td>
                        <td class="table_edit_tr_td_label">线路/ES名称:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_lineDesc" id="d_lineDesc" size="20" maxlength="55" require="true"  dataType="LimitB|NotEmpty" min="1" max="50" msg="线路名称最大长度为50个字节，一个中文是3个字节（最大16个中文）"/>
                        </td>
                        <td class="table_edit_tr_td_label">类型:</td>
                        <td class="table_edit_tr_td_input"> 
                            <select id="d_codeText" name="d_codeText" dataType="Require" msg="类型不能为空!">
                                <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_line_es_Type" />
                            </select>
                        </td>                       
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input">                         
                            <input type="text" name="d_remark" id="d_remark" size="30" maxlength="256" require="false" dataType="LimitB" min="1" max="256" msg="备注最大长度为256个字节，一个中文是3个字节（最大85个中文）"/>                   
                        </td>
                    </tr>
                </table>
            </div>

            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="add" scope="request" value="1"/>
            <c:set var="del" scope="request" value="1"/>
            <c:set var="modify" scope="request" value="1"/>
            <c:set var="save" scope="request" value="1"/>
            <c:set var="cancle" scope="request" value="1"/>
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1"/>
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_lineCode');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
 
       
    </body>
</html>
