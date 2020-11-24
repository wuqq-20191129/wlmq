<%-- 
    Document   : white_list_moc
    Created on : 2018-02-08
    Author     : zhouy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>交通部一卡通白名单</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" src="js/CalendarPop.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/BlackList.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="initDocument('queryOp', 'detail');
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable');"> 
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    黑名单管理 交通部一卡通白名单
                </td>
            </tr>
        </table>

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="WhiteListMoc">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">发卡机构代码:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_iss_identify_id" id="q_iss_identify_id" size="12"  maxLength="11" onpaste="removeBlankSpace('queryOp','q_cardLogicalId');" require="false" dataType="integer"  msg="发卡机构代码只能为数字，最大长度为11位"/>
                    </td>
                    <td class="table_edit_tr_td_label">发卡机构识别码:</td>
                    <td class="table_edit_tr_td_input">
                        <input type="text" name="q_iss_identify_num" id="q_iss_identify_num" size="11"  maxLength="10" onpaste="removeBlankSpace('queryOp','q_cardLogicalId');" require="false" dataType="integer"  msg="发卡机构识别码只能为数字，最大长度为10位"/>
                    </td>
                    <td class="table_edit_tr_td_label" rowspan="2">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_iss_identify_id#q_iss_identify_num');"/>
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
                        <td id="orderTd"    style="width: 120px" class="table_list_tr_col_head_block"  isDigit=true index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >发卡机构代码</td>
                        <td id="orderTd"    style="width: 120px" class="table_list_tr_col_head_block"  isDigit=false index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" >发卡机构识别码</td>
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
                            id="${rs.iss_identify_id}">
                            <td  id="iss_identify_id" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.iss_identify_id}
                            </td>
                            <td  id="iss_identify_num" class="table_list_tr_col_data_block" style="width: 120px">
                                ${rs.iss_identify_num}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <FORM method="post" name="detailOp" id="detailOp" action="WhiteListMoc" >
            <!-- 表头 通用模板 -->
            <input type="hidden" name="expAllFields" id="d_expAllFields" value="iss_identify_id,iss_identify_num" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="交通部一卡通白名单.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead" />
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/WhiteListMocExportAll" />
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
            <c:set var="print" scope="request" value="1"/>
            <c:set var="export" scope="request" value="1"/>
            <c:set var="expAll" scope="request" value="1" />
            <c:set var="btNext" scope="request" value="1"/>
            <c:set var="btNextEnd" scope="request" value="1"/>
            <c:set var="btBack" scope="request" value="1"/>
            <c:set var="btBackEnd" scope="request" value="1"/>
            <c:set var="clickMethod" scope="request" value="btnClick('detailOp','clearStart','detail','','clearStartHead');"/>
            <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all" />
            <br/>
        </FORM>
        
    </body>
</html>
