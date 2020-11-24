<%-- 
    Document   : samStockWarnQuery
    Created on : 2017-8-24, 17:36:28
    Author     : taidb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>卡库存预警</title>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/common_form_extend.js"></script>
        <script language="javascript" type="text/javascript" charset="utf-8" src="js/Validator.js"></script>
        <script language="JavaScript" type="text/javascript" charset="utf-8" src="js/CalendarPop.js"></script>
        <link rel="stylesheet" type="text/css" href="css/frame/simple.css" title="Style"/>
    </head>
    <body onload="
            initDocument('detailOp', 'detail');
            setControlsDefaultValue('queryOp');
            setListViewDefaultValue('detailOp', 'clearStart');
            setQueryControlsDefaultValue('queryOp', 'detailOp');
            setPageControl('detailOp');
            setTableRowBackgroundBlock('DataTable')
          ">
        <table  class="table_title">
            <tr align="center" class="trTitle">
                <td colspan="4">
                    卡库存预警
                </td>
            </tr>
        </table>
        
        

        <!-- 表头 通用模板 -->

        <c:set var="pTitleName" scope="request" value="查询"/>
        <c:set var="pTitleWidth" scope="request" value="50"/>
        <c:import  url="/jsp/common/common_template.jsp?template_name=common_table_title" />

        <form method="post" name="queryOp" id="queryOp" action="samStockWarnAction">
            <!-- 页面用到的变量 通用模板 -->
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <table  class="table_edit">
                <tr class="table_edit_tr">
                    <td class="table_edit_tr_td_label">密钥卡类型:</td>
                    <td class="table_edit_tr_td_input">
                        <select id = "q_samType" name = "q_samType">
                            <c:import url="/jsp/common/common_template.jsp?template_name=option_list_pub_samtype" />
                        </select>
                    </td>    
                    <td class="table_edit_tr_td_label" rowspan="2"/>
                    <td class="table_edit_tr_td_input">
                        <c:import url="/jsp/common/common_template.jsp?template_name=op_button_list_all_init" />
                        <c:set var="query" scope="request" value="1"/>
                        <c:set var="addClickMethod" scope="request" value="setControlNames('queryOp','q_samType');"/>
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
        <div id="clearStartBlock" class="divForTableBlock" style="width: 120%">
            <div id="clearStartHead" class="divForTableBlockHead">
                <table class="table_list_block" id="DataTableHead" >
                    <!--说明：列的序号从0开始 isDigit：false 表示按字符串排序 ：true表示按数值排序 sortedby：asc表示按升序 dec表示按降序 -->
                    <tr  class="table_list_tr_head_block" id="ignore">
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true index="0"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:180px;">SAM卡类型</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true index="1"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:170px;">空白卡库存数量</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false index="2"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:170px;">空白卡预警状态</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=true index="3"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:170px;">成品卡库存数量</td>
                        <td id="orderTd"  class="table_list_tr_col_head_block"  isDigit=false  index="4"  sortedby="asc" onclick="sortForTableBlock('clearStart');" style="width:170px;">成品卡预警状态</td>
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
                            id="${rs.samType}"
                            >
                            <td  id="samType" class="table_list_tr_col_data_block" style="width:180px;">
                                ${rs.samTypeText}
                            </td>
                            <td  id="stockEmptyQty" class="table_list_tr_col_data_block" style="width:170px;">  
                                ${rs.stockEmptyQty}
                            </td>
                            <td  id="etyWarnState" class="table_list_tr_col_data_block" style="width:170px;">
                                <c:choose>    
                                    <c:when  test="${rs.etyWarnState == 1}">
                                        <div style="color: #F00">异常</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div>正常</div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td  id="stockProductQty" class="table_list_tr_col_data_block" style="width:170px;">
                                ${rs.stockProductQty}
                            </td>
                            <td  id="pduWarnState" class="table_list_tr_col_data_block" style="width:170px;">
                                <c:choose>    
                                    <c:when  test="${rs.pduWarnState == 1}">
                                        <div style="color: #F00">异常</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div>正常</div>
                                    </c:otherwise>
                                </c:choose>                                
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <FORM method="post" name="detailOp" id="detailOp" action="samStockWarnAction" >
            <!-- 表头 通用模板 -->       
            <c:set var="divideShow" scope="request" value="1"/>
            <c:import  url="/jsp/common/common_template_web_variable.jsp?template_name=common_web_variable" />
            <input type="hidden" name="expAllFields" id="d_expAllFields"
                   value="getSamTypeText,getStockEmptyQty,getEtyWarnStateText,getStockProductQty,getPduWarnStateText" />
            <input type="hidden" name="expFileName" id="d_expFileName" value="卡库存预警.xlsx" />
            <input type="hidden" name="divId" id="d_divId" value="clearStartHead">
            <input type="hidden" name="methodUrl" id="d_methodUrl" value="/SamStockWarnExportAll"/>
            <div id="detail"  class="divForTableDataDetail" >
                <table  class="table_edit_detail">
                    <tr class="table_edit_tr">
                    </tr>
                </table>
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
