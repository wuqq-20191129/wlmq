<%-- 
    Document   : samTypeDefin
    Created on : 2017-8-23, 19:24:02
    Author     : ysw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>卡类型定义</title>


        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>   
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
 
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setPrimaryKeys('detailOp', 'd_samTypeCode');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setTableRowBackgroundBlock('DataTable')">

        <!--<body >-->
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">卡类型定义</td>
            </tr>
        </table>

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="samTypeDefin">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">                                      
                    <td class="table_edit_tr_td_label">类型代码:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_samTypeCode" id="q_samTypeCode" size="20" require="false" dataType="LimitB|Number" maxLength="2" msg="类型代码应为数字"/>                     
                    </td>
                    <td class="table_edit_tr_td_label">类型名称:</td>
                    <td class="table_edit_tr_td_input">
                        <select id="q_samTypeDesc" name="q_samTypeDesc" >				    
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_samtype_desc" />
                        </select>
                    </td>
                    

                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_samTypeCode#q_samTypeDesc');"/>
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
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">类型代码</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 150px">类型名称</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 180px">成品卡预警阀值</td>
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 180px">空白卡预警阀值</td> 
                        <td id="orderTd"    class="table_list_tr_col_head_block"  isDigit=false index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width: 300px">备注</td>
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
                            id="${rs.sam_type_code}" >

                            <td id="rectNo1" class="table_list_tr_col_data_block">
                                <input type="checkbox" name="rectNo" id="rectNo" onclick="unSelectAllRecord('detailOp', 'rectNoAll', 'rectNo');"
                                       value="${rs.sam_type_code}">
                                </input>
                            </td>                       
                            <td  id="samTypeCode" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.sam_type_code}
                            </td>
                            <td  id="samTypeDesc" class="table_list_tr_col_data_block" style="width: 150px">
                                ${rs.sam_type_desc}
                            </td>
                            <td  id="pduWarn" class="table_list_tr_col_data_block" style="width: 180px">
                                ${rs.pdu_warn_threshhold}
                            </td> 
                            <td  id="etyWarn" class="table_list_tr_col_data_block" style="width: 180px">
                                ${rs.ety_warn_threshhold}
                            </td>
                            <td  id="remark" class="table_list_tr_col_data_block" style="width: 300px">
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

        <FORM method="post" name="detailOp" id="detailOp" action="samTypeDefin" >

            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getSam_type_code,getSam_type_desc,getPdu_warn_threshhold,getEty_warn_threshhold,getRemark" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="卡类型定义.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/SamTypeExportAll"/>

            <!--<DIV id="detail" align="center" class="divForTableData" style="position:relative; left:20; width:95%; height:10; layer-background-color:#99CC99; border:thin outset #ffffff; z-index:1;">-->
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                        <td class="table_edit_tr_td_label">类型代码: </td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_samTypeCode" id="d_samTypeCode" size="10" maxLength="2" require="true" dataType="Number|LimitB" min="1" max="2" msg="类型代码应为数字且不能为空"/>	
                        </td>
                        <td class="table_edit_tr_td_label">类型名称:</td>
                        <td class="table_edit_tr_td_input">
                            <input type="text" name="d_samTypeDesc" id="d_samTypeDesc" size="10" maxlength="35" require="true"  dataType="LimitB|NotEmpty" min="1" max="30" msg="类型名称最大长度为30个字节，一个中文是3个字节（最大10个中文）"/>
                        </td>
                        <td class="table_edit_tr_td_label" style="width:5px">成品卡预警阀值:</td>
                        <td class="table_edit_tr_td_input">                         
                            <input type="text" name="d_pduWarn" id="d_pduWarn" size="10" maxlength="4" require="true" dataType="integer|Positive|Limit" min="1" max="4" msg="成品卡预警阀值应为正整数"/>                   
                        </td>
                        <td class="table_edit_tr_td_label" style="width:5px">空白卡预警阀值:</td>
                        <td class="table_edit_tr_td_input">                         
                            <input type="text" name="d_etyWarn" id="d_etyWarn" size="10" maxlength="4" require="false" dataType="integer|Positive|Limit" min="1" max="4" msg="新购卡预警阀值应为正整数"/>                   
                        </td>
                        <td class="table_edit_tr_td_label">备注:</td>
                        <td class="table_edit_tr_td_input">                         
                            <input type="text" name="d_remark" id="d_remark" size="20" maxlength="260" require="false" dataType="LimitB" min="1" max="256" msg="备注最大长度为256个字节，一个中文是3个字节（最大85个中文）"/>                   
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
            <c:set var="addClickMethod" scope="request" value="setUpdatePrimaryKey('detailOp','d_samTypeCode#d_samTypeDesc');"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
 
       
    </body>
</html>
